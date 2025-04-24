package iuh.fit.qlksfxapp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class PasswordHasher {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(int length) {
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }
    // Phương thức để sinh salt ngẫu nhiên
    public static  byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 byte salt
        random.nextBytes(salt);
        return salt;
    }
    // Phương thức để băm mật khẩu với SHA-256 và salt
    public static String hashPassword(String password, byte[] salt) {
         if (password == null) {
        throw new IllegalArgumentException("Password cannot be null.");
    }
        try {
            // Khởi tạo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Cập nhật salt vào digest
            digest.update(salt);

            // Băm mật khẩu
            byte[] hashedPassword = digest.digest(password.getBytes());

            // Kết hợp salt và hashedPassword thành một chuỗi để lưu trữ
            byte[] saltAndHash = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
            System.arraycopy(hashedPassword, 0, saltAndHash, salt.length, hashedPassword.length);

            // Mã hóa chuỗi salt và băm bằng Base64 để lưu trữ dưới dạng chuỗi
            return Base64.getEncoder().encodeToString(saltAndHash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    public static String getSalt(String password){
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Encoded password cannot be null or empty.");
        }
        byte[] hashPassword = Base64.getDecoder().decode(password);

        // Kết hợp salt và hashedPassword thành một chuỗi để lưu trữ
        byte[] salt = new byte[16];
        System.arraycopy(hashPassword, 0, salt, 0, 16);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Xác thực mật khẩu bằng cách so sánh với mật khẩu đã được băm
     * @param plainPassword Mật khẩu người dùng nhập vào
     * @param storedPassword Mật khẩu đã được băm lưu trong cơ sở dữ liệu
     * @return true nếu mật khẩu khớp, false nếu không khớp
     */
    public static boolean verifyPassword(String plainPassword, String storedPassword) {
        try {
            // Giải mã chuỗi Base64 để lấy salt và hash
            byte[] saltAndHash = Base64.getDecoder().decode(storedPassword);

            // Tách salt (16 byte đầu tiên)
            byte[] salt = new byte[16];
            System.arraycopy(saltAndHash, 0, salt, 0, 16);

            // Tạo hash mới từ mật khẩu người dùng nhập vào và salt đã lưu
            String newHashedPassword = hashPassword(plainPassword, salt);

            // So sánh hash mới với hash đã lưu
            return newHashedPassword.equals(storedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Phương thức tiện ích để kiểm tra mật khẩu đã băm
     * @param plainPassword Mật khẩu gốc
     * @param hashedPassword Mật khẩu đã băm
     */
    public static void checkPassword(String plainPassword, String hashedPassword) {
        try {
            System.out.println("Checking password: " + plainPassword);
            System.out.println("Against hash: " + hashedPassword);

            // Giải mã chuỗi Base64 để lấy salt và hash
            byte[] saltAndHash = Base64.getDecoder().decode(hashedPassword);

            // Tách salt (16 byte đầu tiên)
            byte[] salt = new byte[16];
            System.arraycopy(saltAndHash, 0, salt, 0, 16);

            System.out.println("Extracted salt: " + Base64.getEncoder().encodeToString(salt));

            // Tạo hash mới từ mật khẩu và salt đã lưu
            String newHashedPassword = hashPassword(plainPassword, salt);

            System.out.println("New hash: " + newHashedPassword);
            System.out.println("Match: " + newHashedPassword.equals(hashedPassword));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        String password1= "Nv00001@";
//        String password2= "Nv00002@";
//        String password3= "Nv00003@";
//        String password4= "Nv00004@";
//
//        // Kiểm tra mật khẩu mẫu
//        String hashedPassword = "ocCkrfgDMu0Mogjy6D7mLTPBLNOneoN1TUQAjV2S0/FxV1GDLlr6OLVgXHcGk/UF";
//        checkPassword("RAT-250001", hashedPassword);
//    }
}
