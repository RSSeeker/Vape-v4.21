cmake_minimum_required(VERSION 3.21)
project(Vape421Native VERSION 1.0 LANGUAGES C RC)

if(NOT WIN32)
    message(FATAL_ERROR "Vape421Native is a Windows-only test bridge")
endif()
if(NOT MSVC)
    message(FATAL_ERROR "The audited test bridge requires the MSVC x64 toolchain")
endif()
if(NOT CMAKE_SIZEOF_VOID_P EQUAL 8)
    message(FATAL_ERROR "Only x64 Minecraft/JVM targets are supported")
endif()

set(VAPE421_JAVA_HOME "$ENV{JAVA_HOME}" CACHE PATH "JDK containing JNI/JVMTI headers")
if(NOT VAPE421_JAVA_HOME)
    foreach(candidate
            "C:/Program Files/Java/jdk1.8.0_301"
            "C:/Program Files/Java/jdk-17")
        if(EXISTS "${candidate}/include/jni.h")
            set(VAPE421_JAVA_HOME "${candidate}")
            break()
        endif()
    endforeach()
endif()

find_path(VAPE421_JNI_INCLUDE jni.h
        HINTS "${VAPE421_JAVA_HOME}/include" REQUIRED)
find_path(VAPE421_JNI_MD_INCLUDE jni_md.h
        HINTS "${VAPE421_JAVA_HOME}/include/win32" REQUIRED)
find_path(VAPE421_JVMTI_INCLUDE jvmti.h
        HINTS "${VAPE421_JAVA_HOME}/include" REQUIRED)

set(VAPE421_PRODUCT_JAR "" CACHE FILEPATH
        "Recovered product JAR embedded into Vape421Native.dll")
if(NOT VAPE421_PRODUCT_JAR OR NOT EXISTS "${VAPE421_PRODUCT_JAR}")
    message(FATAL_ERROR
            "VAPE421_PRODUCT_JAR must name an existing recovered product JAR")
endif()
get_filename_component(VAPE421_PRODUCT_JAR
        "${VAPE421_PRODUCT_JAR}" ABSOLUTE)
file(TO_CMAKE_PATH "${VAPE421_PRODUCT_JAR}" VAPE421_PRODUCT_JAR_RC_PATH)
configure_file(payload.rc.in generated/payload.rc @ONLY)
set_property(SOURCE "${CMAKE_CURRENT_BINARY_DIR}/generated/payload.rc"
        APPEND PROPERTY OBJECT_DEPENDS "${VAPE421_PRODUCT_JAR}")

add_library(Vape421Native SHARED
        dllmain.c
        loader_bootstrap.c
        native_bridge.c
        "${CMAKE_CURRENT_BINARY_DIR}/generated/payload.rc")
target_include_directories(Vape421Native PRIVATE
        "${CMAKE_CURRENT_SOURCE_DIR}"
        "${VAPE421_JNI_INCLUDE}"
        "${VAPE421_JNI_MD_INCLUDE}"
        "${VAPE421_JVMTI_INCLUDE}")
target_compile_definitions(Vape421Native PRIVATE
        WIN32_LEAN_AND_MEAN
        _CRT_SECURE_NO_WARNINGS)
target_compile_options(Vape421Native PRIVATE /W4 /utf-8)
target_link_libraries(Vape421Native PRIVATE user32 ws2_32)

set(VAPE421_INJECTOR_RC
        "${CMAKE_CURRENT_BINARY_DIR}/generated/injector_payload.rc")
add_custom_command(
        OUTPUT "${VAPE421_INJECTOR_RC}"
        COMMAND "${CMAKE_COMMAND}"
                "-DDLL_PATH=$<TARGET_FILE:Vape421Native>"
                "-DRC_OUT=${VAPE421_INJECTOR_RC}"
                -P "${CMAKE_CURRENT_SOURCE_DIR}/embed_dll.cmake"
        DEPENDS Vape421Native
        COMMENT "Embedding Vape421Native.dll into the standalone injector")
add_executable(Vape421Injector injector.c "${VAPE421_INJECTOR_RC}")
target_compile_definitions(Vape421Injector PRIVATE
        WIN32_LEAN_AND_MEAN
        _CRT_SECURE_NO_WARNINGS)
target_compile_options(Vape421Injector PRIVATE /W4 /utf-8)

include(CTest)
if(BUILD_TESTING)
    add_executable(Vape421BootstrapTest
            tests/loader_bootstrap_test.c
            loader_bootstrap.c)
    target_include_directories(Vape421BootstrapTest PRIVATE
            "${CMAKE_CURRENT_SOURCE_DIR}")
    target_compile_definitions(Vape421BootstrapTest PRIVATE
            WIN32_LEAN_AND_MEAN
            _CRT_SECURE_NO_WARNINGS)
    target_compile_options(Vape421BootstrapTest PRIVATE /W4 /utf-8)
    target_link_libraries(Vape421BootstrapTest PRIVATE ws2_32)
    add_test(NAME Vape421BootstrapStandalone
            COMMAND Vape421BootstrapTest standalone)
    add_test(NAME Vape421BootstrapHandoff COMMAND Vape421BootstrapTest)
    add_test(NAME Vape421BootstrapRejectsInvalid
            COMMAND Vape421BootstrapTest invalid)
endif()

set_property(TARGET Vape421Native Vape421Injector PROPERTY
        MSVC_RUNTIME_LIBRARY "MultiThreaded$<$<CONFIG:Debug>:Debug>DLL")

set_target_properties(Vape421Native Vape421Injector PROPERTIES
        RUNTIME_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/dist")
foreach(configuration Debug Release RelWithDebInfo MinSizeRel)
    string(TOUPPER "${configuration}" configuration_upper)
    set_target_properties(Vape421Native Vape421Injector PROPERTIES
            RUNTIME_OUTPUT_DIRECTORY_${configuration_upper}
            "${CMAKE_BINARY_DIR}/dist")
endforeach()
