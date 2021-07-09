package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.entities.User;
import ru.geekbrains.service.ProductDao;
import ru.geekbrains.service.UserDao;

import javax.persistence.NoResultException;

public class AppRun {
    private static final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    public static void main(String[] args) {
        UserDao userDao = context.getBean(UserDao.class);
        ProductDao productDao = context.getBean(ProductDao.class);
        System.out.println(userDao.findAll());
        userDao.save(new User(4L, "user4"));
        System.out.println(productDao.findAll());
        try {
            userDao.getUserProducts(2L).forEach(System.out::println);
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
        try {
            productDao.getBuyers(1L).forEach(System.out::println);
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
