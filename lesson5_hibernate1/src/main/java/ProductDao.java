import entities.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductDao {
    private final EntityManager entityManager;

    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Product productFindById(Long id) {
        return entityManager.find(Product.class, id);
    }

    public List<Product> findAll() {
        return entityManager.createQuery("select p from Product p ", Product.class).getResultList();
    }

    public void deleteById(Long id) {
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(productFindById(id));
            entityManager.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            entityManager.getTransaction().rollback();
        }
    }

    public void saveOrUpdate(Product product) {
        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();
    }

    public void close() {
        entityManager.close();
    }
}
