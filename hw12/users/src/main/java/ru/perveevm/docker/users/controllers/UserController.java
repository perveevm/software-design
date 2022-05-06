package ru.perveevm.docker.users.controllers;

import org.springframework.web.bind.annotation.*;
import ru.perveevm.docker.users.entities.User;
import ru.perveevm.docker.users.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add-user")
    public User addUser(@RequestParam final Double balance) {
        return userService.addUser(new User(balance));
    }

    @GetMapping("/get-user")
    public User getUser(@RequestParam final Long userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/change-balance")
    public User changeBalance(@RequestParam final Long userId,
                              @RequestParam final Double newBalance) {
        return userService.changeBalance(userId, newBalance);
    }

    @PostMapping("/buy-stocks")
    public User buyStocks(@RequestParam final Long userId,
                          @RequestParam final String name,
                          @RequestParam final Long stocksAmount) {
        return userService.buyStocks(userId, name, stocksAmount);
    }

    @PostMapping("/sell-stocks")
    public User sellStocks(@RequestParam final Long userId,
                           @RequestParam final String name,
                           @RequestParam final Long stocksAmount) {
        return userService.sellStocks(userId, name, stocksAmount);
    }

    @GetMapping("/calculate-all-stocks-cost")
    public Double calculateAllStocksCost(@RequestParam final Long userId) {
        return userService.calculateAllStocksCost(userId);
    }
}
