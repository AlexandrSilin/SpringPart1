package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class ProductSpecification {
    public static Specification<Product> minCost(BigDecimal min) {
        return (root, query, builder) -> builder.ge(root.get("cost"), min);
    }

    public static Specification<Product> maxCost(BigDecimal max) {
        return (root, query, builder) -> builder.le(root.get("cost"), max);
    }
}
