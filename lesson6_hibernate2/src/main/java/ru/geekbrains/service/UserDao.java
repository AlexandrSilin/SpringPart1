package ru.geekbrains.service;

import ru.geekbrains.AppRun;
import ru.geekbrains.entities.LineItem;
import ru.geekbrains.entities.Product;
import ru.geekbrains.entities.User;

import javax.annotation.PostConstruct;
import java.util.List;

public class UserDao {

    private Service service;

    public UserDao() {
    }

    @PostConstruct
    public void init() {
        this.service = AppRun.getContext().getBean(Service.class);
    }

    public User findById(Long id) {
        return service.executeForEntityManager(manager -> manager.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", id).getSingleResult());
    }

    public List<User> findAll() {
        return service.executeForEntityManager(manager -> manager.createQuery("select u from User u", User.class).getResultList());
    }

    private void insert(User user) {
        service.executeInTransaction(manager -> manager.persist(user));
    }

    private void update(User user) {
        service.executeInTransaction(manager -> manager.merge(user));
    }

    public void save(User user) {
        if (user.getId() == null) {
            insert(user);
        } else {
            update(user);
        }
    }

    public void delete(Long id) {
        service.executeInTransaction(manager -> manager.createQuery("delete from User u where u.id = :id")
                .setParameter("id", id).executeUpdate());
    }

    public List<Product> getUserProducts(Long user_id) {
        return service.executeForEntityManager(manager -> manager.createQuery("select p from Product p join fetch p.users where p.id = :id", Product.class)
                .setParameter("id", user_id).getResultList());
    }
}
