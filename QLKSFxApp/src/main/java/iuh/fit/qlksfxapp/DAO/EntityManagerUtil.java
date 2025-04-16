package iuh.fit.qlksfxapp.DAO;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.reflections.Reflections;

import java.util.Set;

public class EntityManagerUtil {
    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory(){
       if(emf == null) {
           emf = createEntityManagerFactory();
       }
        return emf;
    }
    public static void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
    }
    public static EntityManagerFactory createEntityManagerFactory() {
//        Reflections reflections = new Reflections("Entity");
//
//        // Lấy danh sách các class được đánh dấu với @Entity
//        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(jakarta.persistence.Entity.class);
//        entityClasses.forEach(entity -> System.out.println("Found Entity: " + entity.getName()));
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
//        return emf;
        // Create StandardServiceRegistry
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "org.mariadb.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mariadb://localhost:3306/quanlykhachsan")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "root")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")
                .build();
//         Add package for entity scanning
        MetadataSources metadataSources = new MetadataSources(registry);
        Reflections reflections = new Reflections("iuh.fit.qlksfxapp.Entity");
        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(jakarta.persistence.Entity.class);
        for (Class<?> entityClass : entityClasses) {
            metadataSources.addAnnotatedClass(entityClass);
        }
        // Build the EntityManagerFactory
        return metadataSources.buildMetadata().buildSessionFactory();
    }
}

