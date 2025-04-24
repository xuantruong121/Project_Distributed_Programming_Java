package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.CaLamViec;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for CaLamViec DAO operations
 */
public interface CaLamViecDAO extends GeneralDAO {

    /**
     * Get all work shifts
     * @return A list of all work shifts
     */
    List<CaLamViec> getAllCaLamViec() throws RemoteException;

    /**
     * Find work shift by ID
     * @param maCaLam The ID of the work shift
     * @return The found work shift or null if not found
     */
    CaLamViec findByMaCaLam(String maCaLam) throws RemoteException;
}
