package ru.perveevm.docker.users.entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @ElementCollection
    @CollectionTable(name = "user_stocks", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    })
    @MapKeyColumn(name = "company_name")
    private Map<String, Long> stocks = new HashMap<>();

    public User() {
    }

    public User(final Double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }

    public Map<String, Long> getStocks() {
        return stocks;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setBalance(final Double balance) {
        this.balance = balance;
    }

    public void setStocks(final Map<String, Long> stocks) {
        this.stocks = stocks;
    }
}
