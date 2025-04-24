package iuh.fit.qlksfxapp.util;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Utility class to test RMI connection
 */
public class RMIConnectionTester {

    /**
     * Main method to run the test
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            System.out.println("=== RMI Connection Test ===");
            
            // Set security policy
            System.setProperty("java.security.policy", "rmi.policy");
            
            // Get the registry
            System.out.println("Connecting to RMI registry on localhost:9090...");
            Registry registry = LocateRegistry.getRegistry("localhost", 9090);
            
            // List all registered objects
            System.out.println("Listing registered objects in the registry:");
            String[] names = registry.list();
            
            if (names.length == 0) {
                System.out.println("No objects found in the registry.");
            } else {
                for (String name : names) {
                    System.out.println("  - " + name);
                }
            }
            
            System.out.println("\nRMI connection test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("RMI connection test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
