package ru.geekbrains.service;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class Service {
    private EntityManagerFactory factory;

    public Service() {
    }

    @PostConstruct
    public void init() {
        this.factory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager manager = factory.createEntityManager();
        try {
            return function.apply(manager);
        } finally {
            manager.close();
        }
    }

    public void executeInTransaction(Consumer<EntityManager> managerConsumer) {
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
