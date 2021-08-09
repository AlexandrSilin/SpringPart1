package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.ProductListParams;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecification;
import ru.geekbrains.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    /*
        if (userListParams.getUsernameFilter() != null && !userListParams.getUsernameFilter().isBlank()) {
            spec = spec.and(UserSpecifications.usernamePrefix(userListParams.getUsernameFilter()));
        }
        if (userListParams.getMinAge() != null) {
            spec = spec.and(UserSpecifications.minAge(userListParams.getMinAge()));
        }
        if (userListParams.getMaxAge() != null) {
            spec = spec.and(UserSpecifications.maxAge(userListParams.getMaxAge()));
        }

        return userRepository.findAll(spec,
                        PageRequest.of(
                                Optional.ofNullable(userListParams.getPage()).orElse(1) - 1,
                                Optional.ofNullable(userListParams.getSize()).orElse(3),
                                Sort.by(Optional.ofNullable(userListParams.getSortField())
                                        .filter(c -> !c.isBlank())
                                        .orElse("id"))))
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getAge()));
     */
    @Override
    public Page<Product> findWithFilter(ProductListParams params) {
        Specification<Product> specification = Specification.where(null);
        String directionParam = params.getDirection();
        Sort.Direction direction;
        if ("ASC".equals(directionParam)) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        return productRepository.findAll(specification,
                PageRequest.of(Optional.ofNullable(params.getPage()).orElse(1) - 1,
                        Optional.ofNullable(params.getSize()).orElse(3),
                        Sort.by(direction, Optional.ofNullable(params.getSortBy())
                                .filter(col -> !col.isBlank())
                                .orElse("id"))));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }
}
