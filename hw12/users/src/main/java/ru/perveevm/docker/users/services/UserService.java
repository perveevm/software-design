package ru.perveevm.docker.users.services;

import org.springframework.stereotype.Service;
import ru.perveevm.docker.users.entities.User;
import ru.perveevm.docker.users.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ExchangeService exchangeService;

    public UserService(final UserRepository userRepository, final ExchangeService exchangeService) {
        this.userRepository = userRepository;
        this.exchangeService = exchangeService;
    }

    public User addUser(final User user) {
        return userRepository.save(user);
    }

    public User getUser(final Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("There is no user with such id"));
    }

    public User changeBalance(final Long userId, final Double newBalance) {
        User user = getUser(userId);
        user.setBalance(newBalance);
        return userRepository.save(user);
    }

    public User buyStocks(final Long userId, final String name, final Long stocksAmount) {
        User user = getUser(userId);
        double stocksPrice = exchangeService.getStocksPrice(name);
        if (user.getBalance() < stocksPrice * stocksAmount) {
            throw new RuntimeException("Not enough balance");
        }

        exchangeService.buyStocks(name, stocksAmount);
        user.setBalance(user.getBalance() - stocksPrice * stocksAmount);
        user.getStocks().put(name, user.getStocks().getOrDefault(name, 0L) + stocksAmount);
        return userRepository.save(user);
    }

    public User sellStocks(final Long userId, final String name, final Long stocksAmount) {
        User user = getUser(userId);
        double stocksPrice = exchangeService.getStocksPrice(name);
        if (user.getStocks().getOrDefault(name, 0L) < stocksAmount) {
            throw new RuntimeException("Not enough stocks");
        }

        exchangeService.sellStocks(name, stocksAmount);
        user.setBalance(user.getBalance() + stocksPrice * stocksAmount);
        user.getStocks().put(name, user.getStocks().getOrDefault(name, 0L) - stocksAmount);
        if (user.getStocks().get(name) == 0L) {
            user.getStocks().remove(name);
        }
        return userRepository.save(user);
    }

    public double calculateAllStocksCost(final Long userId) {
        User user = getUser(userId);
        double cost = 0.0;

        for (String name : user.getStocks().keySet()) {
            double stocksPrice = exchangeService.getStocksPrice(name);
            cost += stocksPrice * user.getStocks().get(name);
        }

        return cost;
    }
}
