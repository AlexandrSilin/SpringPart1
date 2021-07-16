package ru.geekbrains.controller;

import org.springframework.data.domain.Sort;

import java.util.Optional;

public class ProductListParams {
    private Long minCost;
    private Long maxCost;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;

    public Long getMinCost() {
        return minCost;
    }

    public void setMinCost(Long minCost) {
        this.minCost = minCost;
    }

    public Long getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(Long maxCost) {
        this.maxCost = maxCost;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
