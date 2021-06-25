package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listPage(Model model) {
        logger.info("Product list page requested");
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        logger.info("New product page requested");
        model.addAttribute("product", new Product());
        return "product_form";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productRepository.getProductById(id));
        return "product_info";
    }

    @GetMapping("/remove/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        productRepository.remove(id);
//        model.addAttribute("product", productRepository.getProductById(id));
        return "redirect:/product";
    }

    @PostMapping
    public String update(Product product) {
        logger.info("Saving product");
        productRepository.save(product);
        return "redirect:/product";
    }
}
