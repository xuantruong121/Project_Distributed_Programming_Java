package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.reflections.Reflections;

import java.util.Set;

public class EntityManagerUtil {
    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = createEntityManagerFactory();
        }
        return emf;
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public static EntityManagerFactory createEntityManagerFactory() {
        try {
            // Configure Hibernate settings
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.driver_class", "org.mariadb.jdbc.Driver")
                    .applySetting("hibernate.connection.url", "jdbc:mariadb://localhost:3306/quanlykhachsan")
                    .applySetting("hibernate.connection.username", "root")
                    .applySetting("hibernate.connection.password", "root")
                    .applySetting("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
                    .applySetting("jakarta.persistence.validation.mode", "none")
                    .applySetting("hibernate.hbm2ddl.auto", "update")
                    .applySetting("hibernate.show_sql", "true")
                    .applySetting("hibernate.format_sql", "true")
                    .applySetting("hibernate.log_jboss", "true")
                    .applySetting("hibernate.bytecode.use_reflection_optimizer", "true")
                    .applySetting("hibernate.bytecode.provider", "bytebuddy")
                    .build();

            // Scan for entity classes
            MetadataSources metadataSources = new MetadataSources(registry);
            Reflections reflections = new Reflections("iuh.fit.qlksfxapp.Entity");
            Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(jakarta.persistence.Entity.class);

            if (entityClasses.isEmpty()) {
                System.err.println("No entity classes found in package iuh.fit.qlksfxapp.Entity");
            } else {
                for (Class<?> entityClass : entityClasses) {
                    System.out.println("Found entity: " + entityClass.getName());
                    metadataSources.addAnnotatedClass(entityClass);
                }
            }

            // Build the EntityManagerFactory
            return metadataSources.buildMetadata().getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e;
            while (cause.getCause() != null) {
                cause = cause.getCause();
                System.err.println("Caused by: " + cause.getMessage());
            }
            throw new RuntimeException("Failed to create EntityManagerFactory: " + e.getMessage() + ". Root cause: "
                    + cause.getMessage(), e);
        }
    }
}
