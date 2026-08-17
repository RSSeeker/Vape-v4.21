#pragma once

#include <atomic>
#include <cstdint>
#include <mutex>
#include <string>
#include <thread>
#include <unordered_map>

class LocalControllerService {
public:
    LocalControllerService() = default;
    ~LocalControllerService();
    LocalControllerService(const LocalControllerService&) = delete;
    LocalControllerService& operator=(const LocalControllerService&) = delete;

    bool start(std::string accessToken, bool cacheEnabled, bool firstRun);
    void stop();
    std::uint16_t port() const;
    void setValue(std::string key, std::string value);
    int stage() const;
    bool completed() const;
    bool failed() const;
    std::string error() const;

private:
    void acceptLoop();
    void serve(std::uintptr_t socketValue);
    static bool receiveLine(std::uintptr_t socketValue, std::string& value);
    static bool receiveFramedString(std::uintptr_t socketValue, std::string& value);
    static bool sendLine(std::uintptr_t socketValue, const std::string& value);

    std::atomic<bool> running_{false};
    std::uintptr_t listenSocket_{~std::uintptr_t{0}};
    std::thread thread_;
    mutable std::mutex mutex_;
    std::uint16_t port_{};
    std::string accessToken_;
    bool cacheEnabled_{};
    bool firstRun_{true};
    std::atomic<int> stage_{0};
    std::atomic<bool> completed_{false};
    std::atomic<bool> failed_{false};
    std::unordered_map<std::string, std::string> values_;
};
