package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.exeptions.ProductNotFound;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecification;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(Product.class);
    private final ProductRepository productRepository;
    private EntityManager em;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("minCost") Optional<Long> minCost,
                           @RequestParam("maxCost") Optional<Long> maxCost,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortBy") Optional<String> col) {
        logger.info("Product list page requested");
//        List<Product> products = productRepository.filterProducts(minCost.orElse(null), maxCost.orElse(null),
//                page.orElse(1) - 1, size.orElse(10));
        Specification<Product> specification = Specification.where(null);
        if (minCost.isPresent()) {
            specification = specification.and(ProductSpecification.minCost(minCost.get()));
        }
        if (maxCost.isPresent()) {
            specification = specification.and(ProductSpecification.maxCost(maxCost.get()));
        }
        model.addAttribute("products", productRepository.findAll(specification,
                PageRequest.of(page.orElse(1) - 1, size.orElse(10),
                        Sort.by(Sort.Direction.ASC, col.orElse("id")))));
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
