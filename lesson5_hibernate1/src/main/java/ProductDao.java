import entities.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProductDao {
    private final EntityManagerFactory factory;

    public ProductDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Product productFindById(Long id) {
        try {
            return executeForEntityManager(manager ->
                    (manager.createQuery("select p from Product p where p.id = :id", Product.class)
                            .setParameter("id", id)).getSingleResult());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Product> findAll() {
        return executeForEntityManager(manager -> manager.createQuery("select p from Product p", Product.class).getResultList());
    }

    public void deleteById(Long id) {
        executeInTransaction(manager -> manager.createQuery("delete from Product p where p.id = :id")
                .setParameter("id", id).executeUpdate());
    }

    private void update(Product product) {
        executeInTransaction(manager -> manager.merge(product));
    }

    private void insert(Product product) {
        executeInTransaction(manager -> manager.persist(product));
    }

    public void save(Product product) {
        if (product.getId() == null) {
            insert(product);
        } else {
            update(product);
        }
    }

    private <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager manager = factory.createEntityManager();
        try {
            return function.apply(manager);
        } finally {
            manager.close();
        }
    }

    private void executeInTransaction(Consumer<EntityManager> managerConsumer) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            managerConsumer.accept(manager);
            manager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            manager.getTransaction().rollback();
        } finally {
            manager.close();
        }
    }
}
