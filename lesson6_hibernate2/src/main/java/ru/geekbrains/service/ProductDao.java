package ru.geekbrains.service;

import org.springframework.stereotype.Component;
import ru.geekbrains.AppRun;
import ru.geekbrains.entities.Product;
import ru.geekbrains.entities.User;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ProductDao {
    private Service service;

    public ProductDao() {
    }

    @PostConstruct
    public void init() {
        this.service = AppRun.getContext().getBean(Service.class);
    }

    public Product findById(Long id) {
        return service.executeForEntityManager(manager -> manager.createQuery("select  p from Product p where p.id = :id", Product.class)
                .setParameter("id", id).getSingleResult());
    }

    public List<Product> findAll() {
        return service.executeForEntityManager(manager -> manager.createQuery("select p  from Product  p", Product.class)
                .getResultList());
    }

    public void insert(Product product) {
        service.executeInTransaction(manager -> manager.persist(product));
    }

    private void update(Product product) {
        service.executeInTransaction(manager -> manager.merge(product));
    }

    public void save(Product product) {
        if (product.getId() == null) {
            insert(product);
        } else {
            update(product);
        }
    }

    public void delete(Long id) {
        service.executeInTransaction(manager -> manager.createQuery("delete from Product p where p.id = :id")
                .setParameter("id", id).executeUpdate());
    }

    public List<User> getBuyers(Long id) {
        return service.executeForEntityManager(manager -> manager.createQuery("select u from User u join fetch u.products where u.id = :id", User.class)
                .setParameter("id", id).getResultList());
    }
}
