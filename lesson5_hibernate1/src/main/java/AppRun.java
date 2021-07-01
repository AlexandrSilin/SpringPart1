import entities.Product;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AppRun {
    public static void main(String[] args) {
        EntityManagerFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        EntityManager manager = factory.createEntityManager();
        ProductService productService = new ProductService(manager);
//        manager.getTransaction().begin();
//        for (int i = 0; i < 5; i++) {
//            manager.persist(new Product(null, "product" + i, (int)(1 + Math.random() * Short.MAX_VALUE)));
//        }
//        manager.getTransaction().commit();
        System.out.println(productService.productFindById(1L));
        productService.saveOrUpdate(new Product(1L, "product013", 11098));
        System.out.println(productService.productFindById(1L));
        productService.deleteById(1L);
        System.out.println(productService.productFindById(1L));
        System.out.println(productService.findAll());
        productService.close();
        manager.close();
    }
}
