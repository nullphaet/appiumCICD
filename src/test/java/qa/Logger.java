package qa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;

public class Logger {

    public static org.apache.logging.log4j.Logger log() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    protected static void logFileSetup(String platformName, String deviceName) throws Exception {
        String strFile = "Test Logs" + File.separator + platformName + File.separator + deviceName;
        File logFile = new File(strFile);
        if (!(logFile.exists())) {
            boolean logDirCreated = logFile.mkdirs();
            if (!logDirCreated) {
                throw new Exception("Failed to create log file directory structure.");
            }
        }
        ThreadContext.put("ROUTINGKEY", strFile);
    }

    /*    public static void log(String txt) {
        Base base = new Base();
        String msg = Thread.currentThread().getId() + " : " + platform.get() + " : " +
                Thread.currentThread().getStackTrace()[2].getClassName() + " : " + txt;

        System.out.println(msg);

        String strFile = "test-output" + File.separator + "Logs" + File.separator + platform.get() + File.separator
                + getCurrentTime();

        File logFile = new File(strFile);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }
        FileWriter writer;
        try {
            writer = new FileWriter(logFile + File.separator + "log.txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(msg);
        printWriter.close();
    }*/
}
