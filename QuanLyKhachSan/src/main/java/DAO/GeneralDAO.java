package DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GeneralDAO {
    private static EntityManagerFactory emf;
    private EntityManager em;
    public GeneralDAO(){
        emf = EntityManagerUtil.getEntityManagerFactory();
        em = emf.createEntityManager();
    }
    public <T> boolean addOb(T ob){
        //Thêm một đối tượng vào database
        //Kiểm tra xem đối tượng đã được thêm vào chưa{
        try {
            em.getTransaction().begin();
            em.persist(ob);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
//            e.printStackTrace();
            throw  e;
        }
//        return false;
    }

    public <T>  boolean updateOb(T ob){
        //Cập nhật thông tin của một đối tượng trong database
        //Kiểm tra xem thông tin đã được cập nhật chưa
        try {
            em.getTransaction().begin();
            em.merge(ob);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
//            e.printStackTrace();
            throw e;
        }
//        return false;
    }
    public<T> boolean deleteOb(T ob){
        //Xóa một đối tượng trong database
        //Kiểm tra xem đối tượng đã được xóa chưa
        try {
            em.getTransaction().begin();
            em.remove(ob);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
//            e.printStackTrace();
            throw e;
        }
//        return false;
    }
    public <T> void findOb(Class<T> entityClass, Object ob){
        //Tìm một đối tượng trong database
        //Kiểm tra xem đối tượng đã được tìm thấy chưa
        T results= em.find(entityClass, ob);
        if(results!=null){
            System.out.println(results.toString());
        }else{
            System.out.println("Không tìm thấy đối tượng");
        }
    }
    public <T> List<T> findAll(Class<T> entityClass){
        //Tìm tất cả các đối tượng trong database
        //Kiểm tra xem danh sách đối tượng đã được tìm thấy chưa
        List<T> results= em.createQuery("from "+entityClass.getName()).getResultList();
        if(!results.isEmpty()){
            for (T result : results) {
                System.out.println(result.toString());
            }
        }else{
            System.out.println("Không tìm thấy đối tượng");
        }
        return results;
    }
}
