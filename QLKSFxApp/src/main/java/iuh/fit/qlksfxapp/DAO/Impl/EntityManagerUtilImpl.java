package iuh.fit.qlksfxapp.DAO.Impl;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.reflections.Reflections;

import java.util.Set;

public class EntityManagerUtilImpl {
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
        // Kiểm tra xem đây có phải là server dựa trên property đã được thiết lập trong RMIServer
        boolean isServer = Boolean.parseBoolean(System.getProperty("app.isServer", "false"));

        // Sử dụng hostname đã được cấu hình trong RMI
        String serverHostname = System.getProperty("java.rmi.server.hostname");

        // Nếu là server hoặc không tìm thấy cấu hình RMI server hostname thì dùng localhost
        // Nếu là client thì dùng địa chỉ IP mà client đã cấu hình để kết nối đến server
        String dbHost;
        if (isServer) {
            dbHost = "localhost"; // Server luôn kết nối đến localhost
        } else {
            // Lấy địa chỉ server từ cấu hình RMI client nếu có, nếu không sử dụng giá trị mặc định
            String rmiServer = System.getProperty("rmi.server.host", "192.168.99.223");
            dbHost = rmiServer;
        }

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "org.mariadb.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mariadb://" + dbHost + ":3306/quanlykhachsan")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "root")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")
                .build();

        // Add package for entity scanning
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
