package ru.geekbrains.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "line_item")
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private BigDecimal price;

    public LineItem(Long id, User user, Product product, Integer qty, BigDecimal price) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.qty = qty;
        this.price = price;
    }

    public LineItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "user=" + user.getUsername() +
                ", product=" + product.getTitle() +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
