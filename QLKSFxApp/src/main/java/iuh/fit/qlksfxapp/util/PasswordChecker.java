package iuh.fit.qlksfxapp.util;

/**
 * Lớp tiện ích để kiểm tra mật khẩu
 */
public class PasswordChecker {
    
    public static void main(String[] args) {
        // Mật khẩu đã băm từ database
        String hashedPassword = "ocCkrfgDMu0Mogjy6D7mLTPBLNOneoN1TUQAjV2S0/FxV1GDLlr6OLVgXHcGk/UF";
        
        // Mật khẩu người dùng nhập vào
        String plainPassword = "Nv00004@";
//        String password1= "Nv00001@";
//        String password2= "Nv00002@";
//        String password3= "Nv00003@";
//        String password4= "Nv00004@";
        
        // Kiểm tra mật khẩu
        PasswordHasher.checkPassword(plainPassword, hashedPassword);
        
        // Kiểm tra xác thực
        boolean isValid = PasswordHasher.verifyPassword(plainPassword, hashedPassword);
        System.out.println("Verification result: " + isValid);
    }
}
