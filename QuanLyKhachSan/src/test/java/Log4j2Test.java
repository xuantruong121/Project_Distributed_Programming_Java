import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4j2Test {
    private static final Logger logger = LoggerFactory.getLogger(Log4j2Test.class);

    public static void main(String[] args) {
        logger.info("Application started...");

        try {
            int result = 10 / 0; // This will cause an exception
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage(), e);
        }

        logger.info("Application finished...");
    }
}
