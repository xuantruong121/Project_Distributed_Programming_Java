package iuh.fit.qlksfxapp.RMI;

import iuh.fit.qlksfxapp.DAO.PhongDAO;
import iuh.fit.qlksfxapp.Entity.Phong;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class RMIClient {

    public static void main(String[] args) {
        try {
            // Set system properties for RMI
            System.setProperty("app.isServer", "false");
            System.setProperty("rmi.server.host", "192.168.99.105");
            System.setProperty("java.security.policy", "rmi.policy");

            // Get the registry
            Registry registry = LocateRegistry.getRegistry("192.168.99.105", 9090);

            // Look up the remote object
            PhongDAO phongDAO = (PhongDAO) registry.lookup("phongDAO");

            // Call methods on the remote object
            List<Phong> phongs = phongDAO.findAll(Phong.class);

            System.out.println("Retrieved " + phongs.size() + " Phong records:");
            for (Phong phong : phongs) {
                System.out.println(phong);
            }

            // Example of finding a Phong by ID
            String maPhong = "STA-101"; // Example ID
            Phong phong = phongDAO.findByMaPhong(maPhong);
            if (phong != null) {
                System.out.println("Found Phong: " + phong);
            } else {
                System.out.println("Phong not found with ID: " + maPhong);
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
