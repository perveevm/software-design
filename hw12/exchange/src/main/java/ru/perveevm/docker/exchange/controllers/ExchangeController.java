package ru.perveevm.docker.exchange.controllers;

import org.springframework.web.bind.annotation.*;
import ru.perveevm.docker.exchange.entities.Company;
import ru.perveevm.docker.exchange.services.ExchangeService;

import java.util.List;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeController(final ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping("/add-company")
    public Company addCompany(@RequestParam final String name,
                              @RequestParam final Long stocks,
                              @RequestParam final Double stockPrice) {
        return exchangeService.addCompany(new Company(name, stocks, stockPrice));
    }

    @GetMapping("/get-company/{name}")
    public Company getCompany(@PathVariable final String name) {
        return exchangeService.getCompany(name);
    }

    @PostMapping("/add-stocks")
    public Company addStocks(@RequestParam final String name,
                             @RequestParam final Long stocksAmount) {
        return exchangeService.addStocks(name, stocksAmount);
    }

    @PostMapping("/buy-stocks")
    public Company buyStocks(@RequestParam final String name,
                             @RequestParam final Long stocksAmount) {
        return exchangeService.buyStocks(name, stocksAmount);
    }

    @PostMapping("/change-stocks-price")
    public Company changeStocksPrice(@RequestParam final String name,
                                     @RequestParam final Double newPrice) {
        return exchangeService.changeStocksPrice(name, newPrice);
    }

    @GetMapping("/get-all-companies")
    public List<Company> getAllCompanies() {
        return exchangeService.getAllCompanies();
    }
}
