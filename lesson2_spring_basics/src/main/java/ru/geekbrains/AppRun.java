package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppRun {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Cart cart = context.getBean("cart", Cart.class);
        ProductRepository productRepository = context.getBean("productRepository", ProductRepository.class);
        for (int i = 0; i < 5; i++) {
            Product product = context.getBean("product", Product.class);
            product.setId((short) (1 + Math.random() * Short.MAX_VALUE));
            product.setTitle("Product_" + i);
            product.setCost((long) (1 + Math.random() * Short.MAX_VALUE));
            productRepository.save(product);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        productRepository.findAll().forEach(System.out::println);
        System.out.println("Commands: add [id], remove [id], show, exit\n");
        while (true) {
            String[] command = reader.readLine().trim().split(" ");
            switch (command[0]) {
                case "add":
                    try {
                        if (command.length < 2) {
                            System.out.println("Bad command");
                            break;
                        }
                        Product product = productRepository.getProductById(Long.parseLong(command[1]));
                        if (product != null) {
                            cart.add(product);
                        }
                        cart.show();
                    } catch (NumberFormatException exception) {
                        System.out.println("Bad id");
                    }
                    break;
                case "remove":
                    if (command.length < 2) {
                        System.out.println("Bad command");
                        break;
                    }
                    long id;
                    try {
                        id = Long.parseLong(command[1]);
                    } catch (NumberFormatException e) {
                        id = -1;
                    }
                    cart.remove(id);
                    cart.show();
                    break;
                case "show":
                    cart.show();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("No such command");
            }
        }
    }
}
