package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.TaiKhoan;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for TaiKhoan DAO operations
 */
public interface TaiKhoanDAO extends GeneralDAO {

    /**
     * Get all accounts
     * @return A list of all accounts
     */
    List<TaiKhoan> getAllTaiKhoan() throws RemoteException;

    /**
     * Find account by ID
     * @param maTaiKhoan The ID of the account
     * @return The found account or null if not found
     */
    TaiKhoan findByMaTaiKhoan(String maTaiKhoan) throws RemoteException;

    /**
     * Find account by username
     * @param username The username to search for
     * @return The found account or null if not found
     */
    TaiKhoan findByUsername(String username) throws RemoteException;

    /**
     * Authenticate a user
     * @param username The username
     * @param password The password
     * @return The authenticated account or null if authentication fails
     */
    TaiKhoan authenticate(String username, String password) throws RemoteException;
}
