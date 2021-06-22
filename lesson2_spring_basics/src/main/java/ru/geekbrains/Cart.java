package ru.geekbrains;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Cart {
    List<Product> products = new ArrayList<>();

    public void add(Product product) {
        products.add(product);
    }

    public void remove(long id) {
        products.removeIf(product -> product.getId() == id);
    }

    public void show() {
        System.out.print("\nYour cart is:");
        if (products.size() == 0) {
            System.out.print(" empty\n");
        } else {
            products.forEach(System.out::println);
        }
    }
}
