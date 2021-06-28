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
        model.addAttribute("product", productRepository.getProductById(id)
                .orElseThrow(() -> new ProductNotFound("Product (id: " + id + ") not found")));
        logger.info("Request product id: " + id);
        return "product_info";
    }

    @GetMapping("/remove/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        productRepository.remove(id);
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
