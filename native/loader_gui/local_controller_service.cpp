#include "local_controller_service.h"

#include <winsock2.h>
#include <ws2tcpip.h>

#include <cstdlib>
#include <charconv>

namespace {
constexpr std::uintptr_t InvalidSocket = static_cast<std::uintptr_t>(INVALID_SOCKET);
}

LocalControllerService::~LocalControllerService() { stop(); }

bool LocalControllerService::start(std::string accessToken, bool cacheEnabled,
                                   bool firstRun) {
    if (running_) return true;
    WSADATA data{};
    if (WSAStartup(MAKEWORD(2, 2), &data) != 0) return false;
    SOCKET listener = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (listener == INVALID_SOCKET) {
        WSACleanup();
        return false;
    }
    sockaddr_in address{};
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = htonl(INADDR_LOOPBACK);
    address.sin_port = 0;
    if (bind(listener, reinterpret_cast<sockaddr*>(&address), sizeof(address)) == SOCKET_ERROR ||
        listen(listener, 1) == SOCKET_ERROR) {
        closesocket(listener);
        WSACleanup();
        return false;
    }
    int length = sizeof(address);
    getsockname(listener, reinterpret_cast<sockaddr*>(&address), &length);
    {
        std::lock_guard lock(mutex_);
        values_.clear();
        accessToken_ = std::move(accessToken);
        cacheEnabled_ = cacheEnabled;
        firstRun_ = firstRun;
        port_ = ntohs(address.sin_port);
    }
    stage_ = 0;
    completed_ = false;
    failed_ = false;
    listenSocket_ = static_cast<std::uintptr_t>(listener);
    running_ = true;
    thread_ = std::thread(&LocalControllerService::acceptLoop, this);
    return true;
}

void LocalControllerService::stop() {
    if (!running_.exchange(false)) return;
    const SOCKET listener = static_cast<SOCKET>(listenSocket_);
    if (listener != INVALID_SOCKET) {
        shutdown(listener, SD_BOTH);
        closesocket(listener);
        listenSocket_ = InvalidSocket;
    }
    if (thread_.joinable()) thread_.join();
    {
        std::lock_guard lock(mutex_);
        if (!accessToken_.empty()) {
            SecureZeroMemory(accessToken_.data(), accessToken_.size());
            accessToken_.clear();
        }
    }
    WSACleanup();
}

std::uint16_t LocalControllerService::port() const {
    std::lock_guard lock(mutex_);
    return port_;
}

void LocalControllerService::setValue(std::string key, std::string value) {
    std::lock_guard lock(mutex_);
    values_[std::move(key)] = std::move(value);
}

int LocalControllerService::stage() const { return stage_.load(); }

bool LocalControllerService::completed() const { return completed_.load(); }

bool LocalControllerService::failed() const { return failed_.load(); }

std::string LocalControllerService::error() const {
    std::lock_guard lock(mutex_);
    const auto found = values_.find("client-error");
    return found == values_.end() ? std::string{} : found->second;
}

bool LocalControllerService::receiveLine(std::uintptr_t socketValue, std::string& value) {
    value.clear();
    const SOCKET client = static_cast<SOCKET>(socketValue);
    for (;;) {
        char character = 0;
        const int received = recv(client, &character, 1, 0);
        if (received != 1) return false;
        if (character == '\n') {
            if (!value.empty() && value.back() == '\r') value.pop_back();
            return true;
        }
        if (value.size() >= 16 * 1024 * 1024) return false;
        value.push_back(character);
    }
}

bool LocalControllerService::sendLine(std::uintptr_t socketValue, const std::string& value) {
    std::string line = value;
    line.push_back('\n');
    const SOCKET client = static_cast<SOCKET>(socketValue);
    std::size_t offset = 0;
    while (offset < line.size()) {
        const int sent = send(client, line.data() + offset,
            static_cast<int>(line.size() - offset), 0);
        if (sent <= 0) return false;
        offset += static_cast<std::size_t>(sent);
    }
    return true;
}

bool LocalControllerService::receiveFramedString(std::uintptr_t socketValue,
                                                 std::string& value) {
    std::string header;
    if (!receiveLine(socketValue, header)) return false;
    char* end = nullptr;
    const unsigned long length = std::strtoul(header.c_str(), &end, 10);
    if (!header.empty() && end && *end == '\0' && length <= 16 * 1024 * 1024) {
        value.assign(length, '\0');
        std::size_t offset = 0;
        const SOCKET client = static_cast<SOCKET>(socketValue);
        while (offset < value.size()) {
            const int received = recv(client, value.data() + offset,
                static_cast<int>(value.size() - offset), 0);
            if (received <= 0) return false;
            offset += static_cast<std::size_t>(received);
        }
        return true;
    }
    value = std::move(header);
    return true;
}

void LocalControllerService::acceptLoop() {
    while (running_) {
        SOCKET client = accept(static_cast<SOCKET>(listenSocket_), nullptr, nullptr);
        if (client == INVALID_SOCKET) break;
        serve(static_cast<std::uintptr_t>(client));
        shutdown(client, SD_BOTH);
        closesocket(client);
        if (running_ && !completed_) failed_ = true;
        if (completed_ || failed_) break;
    }
}

void LocalControllerService::serve(std::uintptr_t socketValue) {
    std::string line;
    while (running_ && receiveLine(socketValue, line)) {
        const int command = std::atoi(line.c_str());
        std::string payload;
        std::string terminator;
        switch (command) {
        case 0x25c:
            if (!receiveLine(socketValue, payload) || !receiveLine(socketValue, terminator)) return;
            setValue("status", payload);
            {
                int parsed = 0;
                const auto result = std::from_chars(payload.data(), payload.data() + payload.size(), parsed);
                if (result.ec == std::errc{} && result.ptr == payload.data() + payload.size()) {
                    stage_ = parsed;
                }
            }
            break;
        case 0x25e:
            receiveLine(socketValue, payload);
            completed_ = true;
            return;
        case 0x266: {
            if (!receiveLine(socketValue, payload) || !receiveLine(socketValue, terminator)) return;
            std::lock_guard lock(mutex_);
            const auto found = values_.find(payload);
            if (!sendLine(socketValue, found == values_.end() ? std::string{} : found->second)) return;
            break;
        }
        case 0x267:
            if (!receiveLine(socketValue, payload) || !receiveLine(socketValue, terminator) ||
                !sendLine(socketValue, "0")) return;
            break;
        case 0x268:
            if (!receiveFramedString(socketValue, payload) || !receiveLine(socketValue, terminator) ||
                !sendLine(socketValue, "0")) return;
            break;
        case 0x269: {
            if (!receiveLine(socketValue, terminator)) return;
            std::lock_guard lock(mutex_);
            if (!sendLine(socketValue, accessToken_)) return;
            if (!accessToken_.empty()) {
                SecureZeroMemory(accessToken_.data(), accessToken_.size());
                accessToken_.clear();
            }
            break;
        }
        case 0x26a:
            if (!receiveFramedString(socketValue, payload)) return;
            setValue("client-error", payload);
            break;
        case 0x15:
            if (!receiveLine(socketValue, payload) || !receiveLine(socketValue, terminator)) return;
            setValue("username", payload);
            break;
        case 0x2d: {
            if (!receiveLine(socketValue, terminator)) return;
            std::lock_guard lock(mutex_);
            if (!sendLine(socketValue, cacheEnabled_ ? "1" : "0") ||
                !sendLine(socketValue, firstRun_ ? "1" : "0")) return;
            break;
        }
        default:
            return;
        }
    }
}
