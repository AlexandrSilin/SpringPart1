import entities.Product;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AppRun {
    public static void main(String[] args) {
        EntityManagerFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        EntityManager manager = factory.createEntityManager();
        ProductDao productDao = new ProductDao(manager);
//        manager.getTransaction().begin();
//        for (int i = 0; i < 5; i++) {
//            manager.persist(new Product(null, "product" + i, (int)(1 + Math.random() * Short.MAX_VALUE)));
//        }
//        manager.getTransaction().commit();
        System.out.println(productDao.productFindById(1L));
        productDao.saveOrUpdate(new Product(1L, "product013", 11098));
        System.out.println(productDao.productFindById(1L));
        productDao.deleteById(1L);
        System.out.println(productDao.productFindById(1L));
        System.out.println(productDao.findAll());
        productDao.close();
        manager.close();
    }
}
