package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.entities.User;
import ru.geekbrains.service.ProductDao;
import ru.geekbrains.service.UserDao;

public class AppRun {
    private static final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    public static void main(String[] args) {
        UserDao userDao = context.getBean(UserDao.class);
        ProductDao productDao = context.getBean(ProductDao.class);
        System.out.println(userDao.findAll());
        userDao.save(new User(4L, "user4"));
        System.out.println(productDao.findAll());
        userDao.getUserProducts(1L).forEach(System.out::println);
        productDao.getBuyers(3L).forEach(System.out::println);
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
