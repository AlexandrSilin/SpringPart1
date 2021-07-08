package ru.geekbrains;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.geekbrains.service.ProductDao;
import ru.geekbrains.service.Service;
import ru.geekbrains.service.UserDao;

import javax.persistence.EntityManagerFactory;

@Configuration
public class AppConfig {
    private EntityManagerFactory factory = new org.hibernate.cfg.Configuration()
            .configure("hibernate.cfg.xml").buildSessionFactory();

    @Bean
    @Scope("prototype")
    public ProductDao productDao() {
        return new ProductDao();
    }

    @Bean
    @Scope("prototype")
    public UserDao userDao() {
        return new UserDao();
    }

    @Bean
    public Service service() {
        return new Service();
    }
}
