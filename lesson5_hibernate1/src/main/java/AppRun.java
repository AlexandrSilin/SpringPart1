import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;

public class AppRun {
    public static void main(String[] args) {
        EntityManagerFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        ProductDao productDao = new ProductDao(factory);
//        manager.getTransaction().begin();
//        for (int i = 0; i < 5; i++) {
//            manager.persist(new Product(null, "product" + i, (int)(1 + Math.random() * Short.MAX_VALUE)));
//        }
//        manager.getTransaction().commit();
//        System.out.println(productDao.productFindById(5L));
//        productDao.save(new Product(55L, "product0135", 161098));
//        productDao.save(new Product(5L, "product013", 11098));
//        System.out.println(productDao.productFindById(5L));
        productDao.deleteById(2L);
        System.out.println(productDao.productFindById(2L));
//        System.out.println(productDao.findAll());
    }
}
