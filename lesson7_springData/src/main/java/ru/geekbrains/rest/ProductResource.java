package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.exeptions.BadRequestException;
import ru.geekbrains.exeptions.NotFoundException;
import ru.geekbrains.persist.Product;
import ru.geekbrains.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource {
    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Product findById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @PostMapping(produces = "application/json")
    public Product create(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new BadRequestException("Product id should be empty");
        }
        productService.save(product);
        return product;
    }

    @PutMapping(produces = "application/json")
    public void update(@RequestBody Product product) {
        if (product.getId() == null) {
            throw new BadRequestException("Product id shouldn't be empty");
        }
        productService.save(product);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
