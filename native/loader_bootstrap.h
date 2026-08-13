#ifndef VAPE421_LOADER_BOOTSTRAP_H
#define VAPE421_LOADER_BOOTSTRAP_H

#include <stdint.h>

#define VAPE421_BOOTSTRAP_MAGIC 0x54423456u
#define VAPE421_BOOTSTRAP_VERSION 2u
#define VAPE421_BOOTSTRAP_MODE_ONLINE 1u
#define VAPE421_BOOTSTRAP_STATUS_CREATED 1u
#define VAPE421_BOOTSTRAP_STATUS_CONSUMED 2u
#define VAPE421_BOOTSTRAP_STATUS_FAILED 3u

#pragma pack(push, 1)
typedef struct Vape421BootstrapV2 {
    uint32_t magic;
    uint16_t version;
    uint16_t structure_size;
    uint32_t target_pid;
    uint32_t mode;
    uint16_t controller_port;
    uint16_t reserved0;
    char service_http_base[256];
    char service_zeus_host[128];
    uint16_t service_zeus_port;
    uint8_t reserved[14];
    uint32_t status;
} Vape421BootstrapV2;
#pragma pack(pop)

typedef char Vape421BootstrapV2_size_must_be_424[
        sizeof(Vape421BootstrapV2) == 424 ? 1 : -1];

int vape_loader_bootstrap_initialize(void);
const char *vape_loader_access_token(void);
int vape_loader_bootstrap_failed(void);
void vape_loader_report_progress(int step);
void vape_loader_report_completed(void);
void vape_loader_report_failure(const char *message);
void vape_loader_bootstrap_clear(void);

#endif
