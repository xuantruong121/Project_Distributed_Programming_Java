package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    public static Logger getLogger() {
        String className = new Throwable().getStackTrace()[1].getClassName();
        return LogManager.getLogger(className);
    }
}