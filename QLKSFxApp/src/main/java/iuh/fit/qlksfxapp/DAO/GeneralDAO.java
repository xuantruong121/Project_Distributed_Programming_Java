package iuh.fit.qlksfxapp.DAO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Base interface for all DAO operations
 */
public interface GeneralDAO extends Remote {
    /**
     * Add a new object to the database
     * @param ob The object to add
     * @param <T> The type of the object
     * @return true if successful, false otherwise
     */
    <T> boolean addOb(T ob) throws RemoteException;

    /**
     * Update an existing object in the database
     * @param ob The object to update
     * @param <T> The type of the object
     * @return true if successful, false otherwise
     */
    <T> boolean updateOb(T ob) throws RemoteException;

    /**
     * Delete an object from the database
     * @param ob The object to delete
     * @param <T> The type of the object
     * @return true if successful, false otherwise
     */
    <T> boolean deleteOb(T ob) throws RemoteException;

    /**
     * Find an object by its ID
     * @param entityClass The class of the entity
     * @param id The ID of the entity
     * @param <T> The type of the entity
     * @return The found entity or null if not found
     */
    <T> T findOb(Class<T> entityClass, Object id) throws RemoteException;

    /**
     * Find all objects of a specific type
     * @param entityClass The class of the entities to find
     * @param <T> The type of the entities
     * @return A list of all entities of the specified type
     */
    <T> List<T> findAll(Class<T> entityClass) throws RemoteException;
}
