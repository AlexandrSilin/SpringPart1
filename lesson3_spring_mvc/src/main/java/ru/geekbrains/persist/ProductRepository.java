package ru.geekbrains.persist;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    @PostConstruct
    public void init() {
        this.save(new Product(1, "Product 1", 4231));
        this.save(new Product(2, "Product 2", 2512));
        this.save(new Product(3, "Product 3", 1234));
        id.set(3);
    }

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

    public Optional<Product> getProductById(long id) {
        return Optional.ofNullable(products.get(id));
    }
}
