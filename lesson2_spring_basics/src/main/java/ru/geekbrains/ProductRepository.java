package ru.geekbrains;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    public void save(Product product) {
        if (product.getId() == 0) {
            product.setId(this.id.incrementAndGet());
        }
        products.put(product.getId(), product);
    }

    public void remove(long id) {
        products.remove(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Product getProductById(long id) {
        return products.get(id);
    }
}
