package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecification {
    public static Specification<Product> minCost(Long min) {
        return (root, query, builder) -> builder.ge(root.get("cost"), min);
    }

    public static Specification<Product> maxCost(Long max) {
        return (root, query, builder) -> builder.le(root.get("cost"), max);
    }
}
