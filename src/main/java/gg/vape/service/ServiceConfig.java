package gg.vape.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public final class ServiceConfig {
    private final String bindAddress;
    private final int httpPort;
    private final int zeusPort;
    private final Path dataFile;

    public ServiceConfig(String bindAddress, int httpPort, int zeusPort, Path dataFile) {
        this.bindAddress = bindAddress;
        this.httpPort = httpPort;
        this.zeusPort = zeusPort;
        this.dataFile = dataFile;
    }

    public String bindAddress() {
        return bindAddress;
    }

    public int httpPort() {
        return httpPort;
    }

    public int zeusPort() {
        return zeusPort;
    }

    public Path dataFile() {
        return dataFile;
    }

    public static ServiceConfig fromEnvironment() {
        return fromArguments(new String[0]);
    }

    public static ServiceConfig fromArguments(String[] args) {
        return fromArguments(args, System.getenv());
    }

    static ServiceConfig fromArguments(String[] args, Map<String, String> environment) {
        Map<String, String> options = parseOptions(args);
        String bindAddress = option(options, "bind-address", environment, "VAPE_BIND_ADDRESS", "127.0.0.1");
        int httpPort = port(option(options, "http-port", environment, "VAPE_HTTP_PORT", "8080"), "http-port");
        int zeusPort = port(option(options, "zeus-port", environment, "VAPE_ZEUS_PORT", "8091"), "zeus-port");
        Path dataFile = Paths.get(option(options, "data-file", environment, "VAPE_DATA_FILE", "data/vape-service.json"));
        return new ServiceConfig(bindAddress, httpPort, zeusPort, dataFile);
    }

    private static Map<String, String> parseOptions(String[] args) {
        Map<String, String> options = new HashMap<>();
        for (int index = 0; index < args.length; index++) {
            String argument = args[index];
            if (!argument.startsWith("--")) {
                throw new IllegalArgumentException("Unexpected argument: " + argument);
            }

            String option = argument.substring(2);
            String value;
            int separator = option.indexOf('=');
            if (separator >= 0) {
                value = option.substring(separator + 1);
                option = option.substring(0, separator);
            } else {
                if (index + 1 >= args.length || args[index + 1].startsWith("--")) {
                    throw new IllegalArgumentException("Missing value for --" + option);
                }
                value = args[++index];
            }

            if (!isSupported(option)) {
                throw new IllegalArgumentException("Unknown option: --" + option);
            }
            if (value.trim().isEmpty()) {
                throw new IllegalArgumentException("Empty value for --" + option);
            }
            if (options.put(option, value) != null) {
                throw new IllegalArgumentException("Duplicate option: --" + option);
            }
        }
        return options;
    }

    private static boolean isSupported(String option) {
        switch (option) {
            case "bind-address":
            case "http-port":
            case "zeus-port":
            case "data-file":
                return true;
            default:
                return false;
        }
    }

    private static String option(Map<String, String> options, String option, Map<String, String> environment,
                                 String environmentName, String fallback) {
        String optionValue = options.get(option);
        if (optionValue != null) {
            return optionValue;
        }
        String value = environment.get(environmentName);
        return value == null || value.trim().isEmpty() ? fallback : value;
    }

    private static int port(String value, String option) {
        final int port;
        try {
            port = Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid value for --" + option + ": " + value);
        }
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Value for --" + option + " must be between 1 and 65535");
        }
        return port;
    }
}
