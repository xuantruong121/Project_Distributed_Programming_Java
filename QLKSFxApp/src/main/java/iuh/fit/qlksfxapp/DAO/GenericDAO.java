package iuh.fit.qlksfxapp.DAO;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Generic DAO interface for basic CRUD operations
 * @param <T> The entity type
 * @param <ID> The ID type
 */
public interface GenericDAO<T, ID> {
    /**
     * Save a new entity
     * @param t The entity to save
     * @return true if successful, false otherwise
     */
    boolean save(T t) throws RemoteException;
    
    /**
     * Update an existing entity
     * @param t The entity to update
     * @return true if successful, false otherwise
     */
    boolean update(T t) throws RemoteException;
    
    /**
     * Find an entity by its ID
     * @param id The ID of the entity
     * @return The found entity or null if not found
     */
    T findById(ID id) throws RemoteException;
    
    /**
     * Get all entities
     * @return A list of all entities
     */
    List<T> getAll() throws RemoteException;
}

