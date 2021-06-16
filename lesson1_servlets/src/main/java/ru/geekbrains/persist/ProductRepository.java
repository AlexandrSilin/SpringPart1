package ru.geekbrains.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public void save(Product product) {
        if (product.getId() == 0) {
            long id = this.id.incrementAndGet();
            product.setId(id);
        }
        products.put(product.getId(), product);
    }

    public void delete(long id) {
        products.remove(id);
    }
}
