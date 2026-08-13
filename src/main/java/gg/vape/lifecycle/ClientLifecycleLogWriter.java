package gg.vape.lifecycle;

import gg.vape.Vape;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientLifecycleLogWriter
implements ClientLifecycleCallback {
    private File logFile;
    private final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private PrintWriter logWriter;

    private void closeFromShutdownHook() {
        this.close();
    }

    public ClientLifecycleLogWriter() {
        try {
            String baseDirectoryPath = System.getenv("APPDATA");
            if (baseDirectoryPath == null) {
                baseDirectoryPath = System.getProperty("user.home");
            }
            String clientDirectoryPath = baseDirectoryPath + File.separator + ".vapeclient";
            File clientDirectory = new File(clientDirectoryPath);
            if (!clientDirectory.exists()) {
                clientDirectory.mkdirs();
            }
            String logFilePath = clientDirectoryPath + File.separator + "log-"
                    + this.timestampFormat.format(new Date()).replace(":", "-") + ".txt";
            Vape.debugLog("Creating log file at: " + logFilePath);
            this.logFile = new File(logFilePath);
            FileWriter fileWriter = new FileWriter(this.logFile, false);
            this.logWriter = new PrintWriter(fileWriter);
            Runtime.getRuntime().addShutdownHook(new Thread(this::closeFromShutdownHook));
        }
        catch (IOException iOException) {
            Vape.logThrowable(iOException);
        }
    }

    private static IOException propagateIOException(IOException exception) {
        return exception;
    }

    @Override
    public void log(String message) {
        try {
            String timestamp = this.timestampFormat.format(new Date());
            this.logWriter.printf("%s: %s%n", timestamp, message);
            this.logWriter.flush();
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    @Override
    public void close() {
        try {
            if (this.logWriter != null) {
                this.logWriter.close();
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }
}
