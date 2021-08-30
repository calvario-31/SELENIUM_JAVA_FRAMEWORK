package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    public static Logger Log = LogManager.getLogger("[LOG]");

    public static void startTest(String testName) {
        System.out.println();
        Log.info("------------------------------------------------------------------------------------------");
        Log.info("Test: " + testName);
        Log.info("------------------------------------------------------------------------------------------");
    }

    public static void endTest(String status, String testName) {
        Log.info("------------------------------------------------------------------------------------------");
        Log.info(status + " " + testName);
        Log.info("------------------------------------------------------------------------------------------");
        System.out.println();
    }

    public static void info(String message) {
        Log.info(message);
    }

    public static void warn(String message) {
        Log.warn(message);
    }

    public static void error(String message) {
        Log.error(message);
    }

    public static void fatal(String message) {
        Log.fatal(message);
    }

    public static void debug(String message) {
        Log.debug(message);
    }
}
