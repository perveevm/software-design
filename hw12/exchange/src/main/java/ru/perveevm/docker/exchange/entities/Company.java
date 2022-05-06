package ru.perveevm.docker.exchange.entities;

import javax.persistence.*;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "company_name", nullable = false, unique = true)
    private String name;

    @Column(name = "stocks", nullable = false)
    private Long stocks;

    @Column(name = "stock_price", nullable = false)
    private Double stockPrice;

    public Company() {
    }

    public Company(final String name, final Long stocks, final Double stockPrice) {
        this.name = name;
        this.stocks = stocks;
        this.stockPrice = stockPrice;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getStocks() {
        return stocks;
    }

    public Double getStockPrice() {
        return stockPrice;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setStocks(final Long stocks) {
        this.stocks = stocks;
    }

    public void setStockPrice(final Double stockPrice) {
        this.stockPrice = stockPrice;
    }
}
