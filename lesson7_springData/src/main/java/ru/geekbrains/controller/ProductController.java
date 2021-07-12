package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.exeptions.ProductNotFound;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(Product.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("Min") Optional<Long> min,
                           @RequestParam("Max") Optional<Long> max,
                           @RequestParam("MinCheck") Optional<String> minCheck,
                           @RequestParam("MaxCheck") Optional<String> maxCheck) {
        logger.info("Product list page requested");
        List<Product> products = productRepository.findAll();
        if (min.isPresent() && max.isPresent()) {
            if (!(minCheck.isEmpty() || maxCheck.isEmpty())) {
                products = products.stream().filter(product -> (product.getCost() >= min.get() &&
                        product.getCost() <= max.get())).collect(Collectors.toList());
            }

        } else if (minCheck.isEmpty() && maxCheck.isPresent()) {
            if (max.isPresent()) {
                products = products.stream().filter(product -> product.getCost() <= max.get())
                        .collect(Collectors.toList());
            }
        } else if (maxCheck.isEmpty() && minCheck.isPresent()) {
            if (min.isPresent()) {
                products = products.stream().filter(product -> product.getCost() >= min.get())
                        .collect(Collectors.toList());
            }
        }
        model.addAttribute("products", products);
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
        model.addAttribute("product", productRepository.getById(id));
        logger.info("Request product id: " + id);
        return "product_info";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        logger.info("Product (id: " + id + ") removed");
        productRepository.deleteById(id);
        return "redirect:/product";
    }

    @PostMapping
    public String update(@Valid Product product, BindingResult result) {
        logger.info("Saving product");
        if (result.hasErrors()) {
            logger.info("Bad input");
            return "product_info";
        }
        productRepository.save(product);
        return "redirect:/product";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView productNotFoundExceptionHandler(ProductNotFound ex) {
        ModelAndView modelAndView = new ModelAndView("product_not_found_form");
        modelAndView.addObject("message", ex.getMessage());
        logger.warn("Exception: " + ex.getMessage());
        return modelAndView;
    }
}
