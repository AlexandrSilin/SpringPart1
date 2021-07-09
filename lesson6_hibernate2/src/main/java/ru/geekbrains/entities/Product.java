package ru.geekbrains.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products", schema = "public")
public class Product {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal cost;

    @OneToMany(mappedBy = "product")
    private List<LineItem> users;

    public Product(Long id, String title, BigDecimal cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public List<LineItem> getUsers() {
        return users;
    }

    public void setUsers(List<LineItem> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                '}';
    }
}
