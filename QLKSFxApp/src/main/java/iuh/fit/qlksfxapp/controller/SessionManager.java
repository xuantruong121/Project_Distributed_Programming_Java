package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.Entity.TaiKhoan;

/**
 * Quản lý thông tin phiên làm việc của người dùng
 */
public class SessionManager {
    private static SessionManager instance;
    private TaiKhoan currentUser;
    
    private SessionManager() {
        // Private constructor để ngăn việc tạo instance từ bên ngoài
    }
    
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public TaiKhoan getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(TaiKhoan currentUser) {
        this.currentUser = currentUser;
    }
    
    public void clearSession() {
        currentUser = null;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
