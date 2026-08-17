#pragma once

#include <cstdint>

constexpr std::uint32_t VAPE421_BOOTSTRAP_MAGIC = 0x54423456u;
constexpr std::uint16_t VAPE421_BOOTSTRAP_VERSION = 2;
constexpr std::uint32_t VAPE421_BOOTSTRAP_MODE_ONLINE = 1;
constexpr std::uint32_t VAPE421_BOOTSTRAP_STATUS_CREATED = 1;
constexpr std::uint32_t VAPE421_BOOTSTRAP_STATUS_CONSUMED = 2;
constexpr std::uint32_t VAPE421_BOOTSTRAP_STATUS_FAILED = 3;

#pragma pack(push, 1)
struct Vape421BootstrapV2 {
    std::uint32_t magic;
    std::uint16_t version;
    std::uint16_t structureSize;
    std::uint32_t targetPid;
    std::uint32_t mode;
    std::uint16_t controllerPort;
    std::uint16_t reserved0;
    char serviceHttpBase[256];
    char serviceZeusHost[128];
    std::uint16_t serviceZeusPort;
    std::uint8_t reserved[14];
    std::uint32_t status;
};
#pragma pack(pop)

static_assert(sizeof(Vape421BootstrapV2) == 424,
    "Loader and Product bootstrap ABI must remain byte-identical");
