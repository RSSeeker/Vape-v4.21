#include "controller_model.h"
#include "injection_coordinator.h"

#include <windows.h>
#include <tlhelp32.h>
#include <winhttp.h>
#include <shellapi.h>

#include <algorithm>
#include <chrono>
#include <fstream>
#include <random>
#include <sstream>
#include <thread>
#include <unordered_map>

namespace {
constexpr UINT WM_CONTROLLER_STATE = WM_APP + 41;

std::wstring onlineBaseUrl() {
    wchar_t value[2048]{};
    const DWORD length = GetEnvironmentVariableW(L"VAPE_ONLINE_BASE_URL", value,
        static_cast<DWORD>(std::size(value)));
    std::wstring result = length > 0 && length < std::size(value)
        ? std::wstring(value, length) : L"http://127.0.0.1:8080";
    while (!result.empty() && result.back() == L'/') result.pop_back();
    return result;
}

std::string utf8(const std::wstring& value) {
    if (value.empty()) return {};
    const int size = WideCharToMultiByte(CP_UTF8, WC_ERR_INVALID_CHARS, value.data(),
        static_cast<int>(value.size()), nullptr, 0, nullptr, nullptr);
    if (size <= 0) return {};
    std::string result(static_cast<std::size_t>(size), '\0');
    WideCharToMultiByte(CP_UTF8, WC_ERR_INVALID_CHARS, value.data(),
        static_cast<int>(value.size()), result.data(), size, nullptr, nullptr);
    return result;
}

std::string jsonEscape(const std::string& value) {
    std::string result;
    result.reserve(value.size());
    for (const unsigned char character : value) {
        switch (character) {
        case '\\': result += "\\\\"; break;
        case '"': result += "\\\""; break;
        case '\b': result += "\\b"; break;
        case '\f': result += "\\f"; break;
        case '\n': result += "\\n"; break;
        case '\r': result += "\\r"; break;
        case '\t': result += "\\t"; break;
        default:
            if (character >= 0x20) result.push_back(static_cast<char>(character));
            break;
        }
    }
    return result;
}

void captureWindowTitles(std::vector<MinecraftProcess>& candidates) {
    EnumWindows([](HWND window, LPARAM value) -> BOOL {
        auto* candidates = reinterpret_cast<std::vector<MinecraftProcess>*>(value);
        if (!IsWindowVisible(window) || GetWindowTextLengthW(window) == 0) {
            return TRUE;
        }

        DWORD processId = 0;
        GetWindowThreadProcessId(window, &processId);
        wchar_t title[256]{};
        if (processId == 0 ||
                GetWindowTextW(window, title, static_cast<int>(std::size(title))) == 0) {
            return TRUE;
        }

        const auto candidate = std::find_if(candidates->begin(), candidates->end(),
            [processId](const MinecraftProcess& process) {
                return process.pid == processId && process.title.empty();
            });
        if (candidate != candidates->end()) candidate->title = title;
        return TRUE;
    }, reinterpret_cast<LPARAM>(&candidates));
}

std::wstring executableDirectory() {
    wchar_t path[MAX_PATH]{};
    GetModuleFileNameW(nullptr, path, MAX_PATH);
    std::wstring result(path);
    const auto separator = result.find_last_of(L"\\/");
    return separator == std::wstring::npos ? L"." : result.substr(0, separator);
}
}

// Extracts the embedded product DLL (resource 422, RCDATA) to the temporary
// directory so it can be injected via LoadLibraryW. Mirrors the standalone
// injector's materialize_embedded_dll() behaviour for single-file mode.
bool ControllerModel::materializeEmbeddedDll(std::uint32_t processId,
        std::wstring& output) {
    const HRSRC resource = FindResourceW(nullptr,
        MAKEINTRESOURCEW(422), MAKEINTRESOURCEW(10));
    if (resource == nullptr) return false;
    const DWORD size = SizeofResource(nullptr, resource);
    const HGLOBAL loaded = LoadResource(nullptr, resource);
    const auto* bytes = loaded == nullptr ? nullptr
        : static_cast<const unsigned char*>(LockResource(loaded));
    if (bytes == nullptr || size < 4) return false;

    wchar_t tempRoot[MAX_PATH]{};
    if (GetTempPathW(static_cast<DWORD>(std::size(tempRoot)), tempRoot) == 0) {
        return false;
    }
    wchar_t directory[MAX_PATH]{};
    _snwprintf_s(directory, std::size(directory), _TRUNCATE,
        L"%lsVape421Recovery", tempRoot);
    if (!CreateDirectoryW(directory, nullptr)
            && GetLastError() != ERROR_ALREADY_EXISTS) {
        return false;
    }
    wchar_t target[MAX_PATH]{};
    _snwprintf_s(target, std::size(target), _TRUNCATE,
        L"%ls\\Vape-v4.21Native-%lu.dll", directory,
        static_cast<unsigned long>(processId));

    HANDLE file = CreateFileW(target, GENERIC_WRITE,
        FILE_SHARE_READ | FILE_SHARE_DELETE, nullptr, CREATE_ALWAYS,
        FILE_ATTRIBUTE_TEMPORARY, nullptr);
    if (file == INVALID_HANDLE_VALUE) return false;

    DWORD offset = 0;
    bool ok = true;
    while (offset < size) {
        DWORD written = 0;
        const DWORD remaining = size - offset;
        if (!WriteFile(file, bytes + offset, remaining, &written, nullptr)
                || written == 0) {
            ok = false;
            break;
        }
        offset += written;
    }
    CloseHandle(file);
    if (!ok) {
        DeleteFileW(target);
        return false;
    }
    output = target;
    return true;
}

ControllerModel::ControllerModel() {
    serviceHttpBase_ = onlineBaseUrl();
    const std::wstring setting = cacheDirectory() + L"cache.preference";
    std::wifstream input(setting);
    int enabled = 0;
    if (input >> enabled) {
        cachePreference_ = enabled != 0;
    }
    // Skip the login page: authenticate against the local loopback Service
    // with an anonymous username and go straight to Minecraft selection.
    autoLoginToService();
}

ControllerModel::~ControllerModel() {
    cancelAuth_ = true;
    if (authThread_.joinable()) authThread_.join();
}

ControllerPage ControllerModel::page() const {
    std::lock_guard lock(mutex_);
    return page_;
}

void ControllerModel::setPage(ControllerPage value) {
    std::lock_guard lock(mutex_);
    page_ = value;
}

std::wstring ControllerModel::status() const {
    std::lock_guard lock(mutex_);
    return status_;
}

void ControllerModel::setStatus(std::wstring value) {
    std::lock_guard lock(mutex_);
    status_ = std::move(value);
}

std::wstring& ControllerModel::username() { return username_; }
std::wstring& ControllerModel::password() { return password_; }

std::vector<MinecraftProcess> ControllerModel::minecraftProcesses() const {
    std::lock_guard lock(mutex_);
    return minecraftProcesses_;
}

void ControllerModel::refreshMinecraftProcesses() {
    std::vector<MinecraftProcess> found;
    std::vector<std::uint32_t> injected;
    {
        std::lock_guard lock(mutex_);
        injected = injectedProcesses_;
    }
    HANDLE snapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
    if (snapshot != INVALID_HANDLE_VALUE) {
        PROCESSENTRY32W entry{sizeof(entry)};
        if (Process32FirstW(snapshot, &entry)) {
            do {
                if (_wcsicmp(entry.szExeFile, L"java.exe") != 0 &&
                        _wcsicmp(entry.szExeFile, L"javaw.exe") != 0) {
                    continue;
                }
                const bool wasInjected = std::find(injected.begin(), injected.end(),
                    entry.th32ProcessID) != injected.end();
                found.push_back({entry.th32ProcessID, {}, wasInjected});
            } while (Process32NextW(snapshot, &entry));
        }
        CloseHandle(snapshot);
    }

    captureWindowTitles(found);
    found.erase(std::remove_if(found.begin(), found.end(),
        [](const MinecraftProcess& process) { return process.title.empty(); }), found.end());
    std::sort(found.begin(), found.end(),
        [](const MinecraftProcess& left, const MinecraftProcess& right) {
            return left.pid < right.pid;
        });

    std::lock_guard lock(mutex_);
    minecraftProcesses_ = std::move(found);
    lastMinecraftRefresh_ = std::chrono::steady_clock::now();
}

bool ControllerModel::injectMinecraft(std::uint32_t processId) {
    std::string token;
    std::string serviceHttpBase;
    {
        std::lock_guard lock(mutex_);
        token = accessToken_;
        serviceHttpBase = utf8(serviceHttpBase_);
    }
    // The local Service may have been unavailable when the Loader started
    // (Minecraft not running yet). Re-authenticate lazily so the token handed
    // to the injected DLL is always valid.
    if (token.empty() && !loginToService()) {
        setStatus(L"无法登录本地服务（请先启动 Minecraft）");
        setPage(ControllerPage::Error);
        return false;
    }
    {
        std::lock_guard lock(mutex_);
        token = accessToken_;
        if (token.empty()) {
            setStatus(L"无法登录本地服务（请先启动 Minecraft）");
            setPage(ControllerPage::Error);
            return false;
        }
    }
    if (!service_.start(token, cachePreference_, true)) {
        setStatus(L"无法创建加载器控制套接字");
        setPage(ControllerPage::Error);
        return false;
    }
    std::wstring error;
    std::wstring dllPath = executableDirectory()
            + L"\\Vape-v4.21Native.dll";
    const std::wstring legacyDllPath = executableDirectory()
            + L"\\Vape421Native.dll";
    if (GetFileAttributesW(dllPath.c_str()) == INVALID_FILE_ATTRIBUTES
            && GetFileAttributesW(legacyDllPath.c_str()) != INVALID_FILE_ATTRIBUTES) {
        dllPath = legacyDllPath;
    }
    if (GetFileAttributesW(dllPath.c_str()) == INVALID_FILE_ATTRIBUTES) {
        // Single-file mode: extract the embedded DLL resource (ID 422, RCDATA)
        // next to the Loader so the product DLL can be injected from it.
        std::wstring extracted;
        if (materializeEmbeddedDll(processId, extracted)) {
            dllPath = extracted;
        } else {
            setStatus(L"加载器旁未找到 Vape-v4.21Native.dll");
            setPage(ControllerPage::Error);
            return false;
        }
    }
    if (!InjectionCoordinator::injectProductDll(processId, dllPath, service_.port(),
            serviceHttpBase, error)) {
        service_.stop();
        setStatus(error.empty() ? L"注入 Vape421Native.dll 失败" : error);
        setPage(ControllerPage::Error);
        return false;
    }
    {
        std::lock_guard lock(mutex_);
        if (std::find(injectedProcesses_.begin(), injectedProcesses_.end(), processId) ==
            injectedProcesses_.end()) {
            injectedProcesses_.push_back(processId);
        }
        if (!accessToken_.empty()) {
            SecureZeroMemory(accessToken_.data(), accessToken_.size());
            accessToken_.clear();
        }
        loadingStage_ = service_.stage();
        loadingStarted_ = std::chrono::steady_clock::now();
        stageStarted_ = loadingStarted_;
        page_ = ControllerPage::Loading;
        status_.clear();
    }
    if (!token.empty()) SecureZeroMemory(token.data(), token.size());
    return true;
}

std::wstring ControllerModel::makeHwid() {
    DWORD serial = 0;
    GetVolumeInformationW(L"C:\\", nullptr, 0, &serial, nullptr, nullptr, nullptr, 0);
    wchar_t value[32]{};
    swprintf_s(value, L"%08X", serial);
    return value;
}

std::string ControllerModel::httpPost(const wchar_t* host, const wchar_t* path,
                                      const std::string& body) {
    HINTERNET session = WinHttpOpen(L"Vape4/Launcher",
        WINHTTP_ACCESS_TYPE_AUTOMATIC_PROXY, WINHTTP_NO_PROXY_NAME,
        WINHTTP_NO_PROXY_BYPASS, 0);
    if (!session) return {};
    WinHttpSetTimeouts(session, 5000, 5000, 5000, 5000);
    HINTERNET connection = WinHttpConnect(session, host, INTERNET_DEFAULT_HTTPS_PORT, 0);
    HINTERNET request = connection ? WinHttpOpenRequest(connection, L"POST", path,
        nullptr, WINHTTP_NO_REFERER, WINHTTP_DEFAULT_ACCEPT_TYPES, WINHTTP_FLAG_SECURE) : nullptr;
    std::string response;
    const wchar_t* headers = L"Content-Type: application/x-www-form-urlencoded";
    if (request && WinHttpSendRequest(request, headers, static_cast<DWORD>(-1L),
            const_cast<char*>(body.data()), static_cast<DWORD>(body.size()),
            static_cast<DWORD>(body.size()), 0) && WinHttpReceiveResponse(request, nullptr)) {
        for (;;) {
            DWORD available = 0;
            if (!WinHttpQueryDataAvailable(request, &available) || available == 0) break;
            const auto offset = response.size();
            response.resize(offset + available);
            DWORD read = 0;
            if (!WinHttpReadData(request, response.data() + offset, available, &read)) break;
            response.resize(offset + read);
        }
    }
    if (request) WinHttpCloseHandle(request);
    if (connection) WinHttpCloseHandle(connection);
    WinHttpCloseHandle(session);
    return response;
}

std::string ControllerModel::httpPostJson(const std::wstring& baseUrl,
                                          const wchar_t* path,
                                          const std::string& body) {
    URL_COMPONENTS components{};
    components.dwStructSize = sizeof(components);
    components.dwSchemeLength = static_cast<DWORD>(-1);
    components.dwHostNameLength = static_cast<DWORD>(-1);
    components.dwUrlPathLength = static_cast<DWORD>(-1);
    if (!WinHttpCrackUrl(baseUrl.c_str(), 0, 0, &components) ||
            components.dwHostNameLength == 0) {
        return {};
    }
    const std::wstring host(components.lpszHostName, components.dwHostNameLength);
    HINTERNET session = WinHttpOpen(L"Vape4/Loader",
        WINHTTP_ACCESS_TYPE_NO_PROXY, WINHTTP_NO_PROXY_NAME,
        WINHTTP_NO_PROXY_BYPASS, 0);
    if (!session) return {};
    WinHttpSetTimeouts(session, 5000, 5000, 5000, 5000);
    HINTERNET connection = WinHttpConnect(session, host.c_str(), components.nPort, 0);
    const DWORD flags = components.nScheme == INTERNET_SCHEME_HTTPS
        ? WINHTTP_FLAG_SECURE : 0;
    HINTERNET request = connection ? WinHttpOpenRequest(connection, L"POST", path,
        nullptr, WINHTTP_NO_REFERER, WINHTTP_DEFAULT_ACCEPT_TYPES, flags) : nullptr;
    std::string response;
    const wchar_t* headers = L"Content-Type: application/json; charset=utf-8";
    if (request && WinHttpSendRequest(request, headers, static_cast<DWORD>(-1L),
            const_cast<char*>(body.data()), static_cast<DWORD>(body.size()),
            static_cast<DWORD>(body.size()), 0) && WinHttpReceiveResponse(request, nullptr)) {
        DWORD statusCode = 0;
        DWORD statusSize = sizeof(statusCode);
        if (WinHttpQueryHeaders(request, WINHTTP_QUERY_STATUS_CODE |
                WINHTTP_QUERY_FLAG_NUMBER, WINHTTP_HEADER_NAME_BY_INDEX,
                &statusCode, &statusSize, WINHTTP_NO_HEADER_INDEX) && statusCode == 200) {
            for (;;) {
                DWORD available = 0;
                if (!WinHttpQueryDataAvailable(request, &available) || available == 0) break;
                const auto offset = response.size();
                response.resize(offset + available);
                DWORD read = 0;
                if (!WinHttpReadData(request, response.data() + offset, available, &read)) break;
                response.resize(offset + read);
            }
        }
    }
    if (request) WinHttpCloseHandle(request);
    if (connection) WinHttpCloseHandle(connection);
    WinHttpCloseHandle(session);
    return response;
}

std::string ControllerModel::jsonString(const std::string& json, const char* key) {
    const std::string needle = std::string("\"") + key + "\"";
    auto position = json.find(needle);
    if (position == std::string::npos) return {};
    position = json.find(':', position + needle.size());
    position = json.find('"', position == std::string::npos ? position : position + 1);
    if (position == std::string::npos) return {};
    const auto end = json.find('"', position + 1);
    return end == std::string::npos ? std::string{} : json.substr(position + 1, end - position - 1);
}

void ControllerModel::beginBrowserAuthentication(void* windowHandle) {
    cancelAuth_ = true;
    if (authThread_.joinable()) authThread_.join();
    cancelAuth_ = false;
    setPage(ControllerPage::BrowserAuth);
    setStatus(L"");
    const auto window = static_cast<HWND>(windowHandle);
    authThread_ = std::thread([this, window] {
        const auto hwid = makeHwid();
        std::string narrowHwid;
        narrowHwid.reserve(hwid.size());
        for (const wchar_t character : hwid) {
            narrowHwid.push_back(static_cast<char>(character));
        }
        const auto challenge = httpPost(L"www.vape.gg", L"/api/v1/app-auth/generate",
            "edition=v4&hwid=" + narrowHwid);
        if (challenge.size() != 40 || cancelAuth_) {
            if (!cancelAuth_) {
                setStatus(L"无法启动浏览器登录");
                setPage(ControllerPage::Login);
            }
            PostMessageW(window, WM_CONTROLLER_STATE, 0, 0);
            return;
        }
        std::wstring wideChallenge(challenge.begin(), challenge.end());
        const std::wstring url = L"https://www.vape.gg/app-auth/proceed/" + wideChallenge;
        {
            std::lock_guard lock(mutex_);
            browserUrl_ = url;
        }
        ShellExecuteW(nullptr, L"open", url.c_str(), nullptr, nullptr, SW_SHOWNORMAL);
        while (!cancelAuth_) {
            std::this_thread::sleep_for(std::chrono::seconds(2));
            const auto response = httpPost(L"www.vape.gg", L"/api/v1/app-auth/status",
                "token=" + challenge);
            const auto status = jsonString(response, "status");
            if (status == "success") {
                {
                    std::lock_guard lock(mutex_);
                    accessToken_ = jsonString(response, "token");
                }
                refreshMinecraftProcesses();
                setPage(ControllerPage::MinecraftSelection);
                break;
            }
            if (status == "timed out") {
                setStatus(L"浏览器登录超时");
                setPage(ControllerPage::Login);
                break;
            }
        }
        PostMessageW(window, WM_CONTROLLER_STATE, 0, 0);
    });
}

void ControllerModel::reopenBrowserAuthentication() {
    std::wstring url;
    {
        std::lock_guard lock(mutex_);
        url = browserUrl_;
    }
    if (!url.empty()) ShellExecuteW(nullptr, L"open", url.c_str(), nullptr, nullptr, SW_SHOWNORMAL);
}

void ControllerModel::cancelBrowserAuthentication() {
    cancelAuth_ = true;
    setPage(ControllerPage::Login);
}

std::wstring ControllerModel::cacheDirectory() const {
    wchar_t profile[MAX_PATH]{};
    DWORD size = GetEnvironmentVariableW(L"USERPROFILE", profile, MAX_PATH);
    std::wstring directory = size ? std::wstring(profile, size) : executableDirectory();
    directory += L"\\.vapeclient\\";
    return directory;
}

void ControllerModel::persistCachePreference(bool enabled) {
    cachePreference_ = enabled;
    const auto directory = cacheDirectory();
    CreateDirectoryW(directory.c_str(), nullptr);
    std::wofstream output(directory + L"cache.preference", std::ios::trunc);
    output << (enabled ? 1 : 0);
}

bool ControllerModel::cachePreference() const { return cachePreference_; }

void ControllerModel::tick() {
    const auto now = std::chrono::steady_clock::now();
    const auto currentPage = page();
    if (currentPage == ControllerPage::MinecraftSelection) {
        bool refresh = false;
        {
            std::lock_guard lock(mutex_);
            refresh = lastMinecraftRefresh_.time_since_epoch().count() == 0 ||
                now - lastMinecraftRefresh_ >= std::chrono::seconds(2);
        }
        if (refresh) refreshMinecraftProcesses();
        return;
    }
    if (currentPage != ControllerPage::Loading) return;

    const int serviceStage = service_.stage();
    {
        std::lock_guard lock(mutex_);
        if (serviceStage != loadingStage_) {
            loadingStage_ = serviceStage;
            stageStarted_ = now;
        }
    }
    if (service_.completed()) {
        setPage(ControllerPage::LoadingComplete);
    } else if (service_.failed()) {
        const std::string detail = service_.error();
        setStatus(detail.empty() ? L"Native loading connection closed unexpectedly"
                                 : std::wstring(detail.begin(), detail.end()));
        setPage(ControllerPage::Error);
    } else if (loadingElapsedSeconds() >= 90.0) {
        setStatus(L"原生加载超时");
        setPage(ControllerPage::Error);
    }
}

int ControllerModel::loadingStage() const {
    std::lock_guard lock(mutex_);
    return loadingStage_;
}

double ControllerModel::loadingElapsedSeconds() const {
    std::lock_guard lock(mutex_);
    if (loadingStarted_.time_since_epoch().count() == 0) return 0.0;
    return std::chrono::duration<double>(std::chrono::steady_clock::now() - loadingStarted_).count();
}

double ControllerModel::stageElapsedSeconds() const {
    std::lock_guard lock(mutex_);
    if (stageStarted_.time_since_epoch().count() == 0) return 0.0;
    return std::chrono::duration<double>(std::chrono::steady_clock::now() - stageStarted_).count();
}

void ControllerModel::submitCredentialAuthentication() {
    const std::wstring usernameValue = username_;
    const std::string usernameUtf8 = utf8(usernameValue);
    if (usernameUtf8.empty()) {
        setStatus(L"请输入用户名");
        return;
    }
    const std::string response = httpPostJson(serviceHttpBase_, L"/loader/login",
        "{\"username\":\"" + jsonEscape(usernameUtf8) + "\"}");
    const std::string token = jsonString(response, "token");
    if (token.empty()) {
        setStatus(L"无法登录本地服务");
        return;
    }
    {
        std::lock_guard lock(mutex_);
        accessToken_ = token;
        status_.clear();
    }
    refreshMinecraftProcesses();
    setPage(ControllerPage::MinecraftSelection);
}

bool ControllerModel::loginToService() {
    // Generate a local access token directly. The in-game Service used to be
    // the token source, but it only exists after injection (which needs the
    // token first), so depending on it created a chicken-and-egg deadlock.
    // The bootstrap only requires a non-empty token; online features inside
    // the game register their own account with the loopback Service.
    std::string token;
    token.reserve(48);
    std::mt19937 generator(static_cast<unsigned int>(
        std::chrono::steady_clock::now().time_since_epoch().count())
        ^ static_cast<unsigned int>(GetCurrentProcessId()));
    const char alphabet[] = "0123456789abcdef";
    for (int i = 0; i < 48; ++i) {
        token.push_back(alphabet[generator() % 16]);
    }
    {
        std::lock_guard lock(mutex_);
        accessToken_ = token;
        status_.clear();
    }
    return true;
}

void ControllerModel::autoLoginToService() {
    if (loginToService()) {
        refreshMinecraftProcesses();
    }
    setPage(ControllerPage::MinecraftSelection);
}
