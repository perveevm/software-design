package ru.perveevm.docker.users.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class ExchangeService {
    private final String HOST = "http://localhost:8080/exchange";
    private final RestTemplate restTemplate;

    public ExchangeService(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void buyStocks(final String name, final Long stocksAmount) {
        restTemplate.postForObject(HOST + "/buy-stocks?name={name}&stocksAmount={stocksAmount}",
                null, String.class, name, stocksAmount);
    }

    public void sellStocks(final String name, final Long stocksAmount) {
        restTemplate.postForObject(HOST + "/add-stocks?name={name}&stocksAmount={stocksAmount}",
                null, String.class, name, stocksAmount);
    }

    @SuppressWarnings("unchecked")
    public double getStocksPrice(final String name) {
        HashMap<String, Object> response = restTemplate.getForObject(HOST + "/get-company/{name}", HashMap.class, name);
        if (response == null) {
            throw new RuntimeException("Cannot find company with given name");
        }
        return (double) response.get("stockPrice");
    }
}
