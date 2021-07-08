package ru.geekbrains.entities;

import javax.persistence.*;

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
    private Long cost;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    public Product(Long id, String title, Long cost) {
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

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
