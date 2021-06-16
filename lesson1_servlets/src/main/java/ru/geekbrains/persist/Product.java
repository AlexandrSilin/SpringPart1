package ru.geekbrains.persist;

public class Product {
    private long id;
    private long cost;
    private String title;

    public Product(long id, long cost, String title) {
        this.id = id;
        this.cost = cost;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
