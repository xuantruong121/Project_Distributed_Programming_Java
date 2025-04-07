package nhap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class testLogger {

        private static final Logger logger = LogManager.getLogger(testLogger.class);

        public static void main(String[] args) {
            try {
                // Giả sử đây là đoạn code có thể gây ra ngoại lệ
                int result = 10 / 0;
            } catch (ArithmeticException e) {
                // Ghi log lỗi thay vì ném ngoại lệ
                logger.error("Đã xảy ra lỗi: ", e);
            }
        }

}
