package iuh.fit.qlksfxapp.util;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Utility class to check if all entity classes implement Serializable
 */
public class SerializableChecker {

    /**
     * Main method to run the check
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Get all classes in the Entity package
            List<Class<?>> entityClasses = getClasses("iuh.fit.qlksfxapp.Entity");
            
            // Check if each class implements Serializable
            List<String> nonSerializableClasses = new ArrayList<>();
            for (Class<?> clazz : entityClasses) {
                if (!Serializable.class.isAssignableFrom(clazz)) {
                    nonSerializableClasses.add(clazz.getName());
                }
            }
            
            // Print results
            System.out.println("=== Serializable Check Results ===");
            System.out.println("Total entity classes: " + entityClasses.size());
            System.out.println("Non-serializable classes: " + nonSerializableClasses.size());
            
            if (nonSerializableClasses.isEmpty()) {
                System.out.println("All entity classes implement Serializable!");
            } else {
                System.out.println("The following classes do NOT implement Serializable:");
                for (String className : nonSerializableClasses) {
                    System.out.println("  - " + className);
                }
                System.out.println("\nPlease add 'implements Serializable' to these classes.");
                System.out.println("Also add 'private static final long serialVersionUID = 1L;' to each class.");
            }
            
        } catch (Exception e) {
            System.err.println("Error checking serializable classes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get all classes in a package
     * @param packageName The package name
     * @return List of classes in the package
     * @throws Exception If an error occurs
     */
    private static List<Class<?>> getClasses(String packageName) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        
        return classes;
    }
    
    /**
     * Find classes in a directory
     * @param directory The directory to search
     * @param packageName The package name
     * @return List of classes in the directory
     * @throws ClassNotFoundException If a class cannot be found
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        
        if (!directory.exists()) {
            return classes;
        }
        
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClasses(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    classes.add(Class.forName(className));
                }
            }
        }
        
        return classes;
    }
}
