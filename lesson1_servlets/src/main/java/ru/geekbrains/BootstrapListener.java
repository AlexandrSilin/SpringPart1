package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootstrapListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ProductRepository repository = new ProductRepository();
        for (int i = 0; i < 10; i++) {
            repository.save(new Product((long) (1 + Math.random() * Long.MAX_VALUE),
                    (long) (1 + Math.random() * Short.MAX_VALUE), "Product_" + (i + 1)));
        }
        servletContext.setAttribute("productRepository", repository);
    }
}
