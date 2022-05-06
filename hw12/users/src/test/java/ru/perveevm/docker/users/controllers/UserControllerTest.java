package ru.perveevm.docker.users.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.perveevm.docker.users.entities.User;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Transactional
public class UserControllerTest {
    public static final DockerImageName IMAGE = DockerImageName.parse("exchange:1.0");

    @Container
    public static GenericContainer<?> exchange = new GenericContainer<>(IMAGE).withExposedPorts(8080);

    @Autowired
    private UserController userController;

    private static final String HOST = "http://localhost:8080/exchange";

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    private Long userId;

    @BeforeEach
    public void prepare() {
        exchange.start();
        addCompany("test-company", 100000L, 10.0);
        userId = userController.addUser(1000.0).getId();
    }

    @AfterEach
    public void clean() {
        exchange.stop();
    }

    @Test
    public void testAddUser() {
        User newUser = userController.addUser(10000.0);
        assertThat(newUser.getBalance()).isEqualTo(10000.0);
        assertThat(newUser.getStocks()).isEmpty();
    }
    
    @Test
    public void testChangeBalance() {
        User user = userController.changeBalance(userId, 20.0);
        assertThat(user.getBalance()).isEqualTo(20.0);
        assertThat(user.getStocks()).isEmpty();
    }

    @Test
    public void testBuyStocks() {
        User user = userController.buyStocks(userId, "test-company", 2L);
        assertThat(user.getBalance()).isEqualTo(1000.0 - 2.0 * 10.0);
        assertThat(user.getStocks()).size().isEqualTo(1);
        assertThat(user.getStocks()).containsEntry("test-company", 2L);
    }

    @Test
    public void testSellStocks() {
        testBuyStocks();
        User user = userController.sellStocks(userId, "test-company", 1L);
        assertThat(user.getBalance()).isEqualTo(1000.0 - 10.0);
        assertThat(user.getStocks()).size().isEqualTo(1);
        assertThat(user.getStocks()).containsEntry("test-company", 1L);
    }

    private void addCompany(final String name, final Long stocks, final Double stockPrice) {
        testRestTemplate.postForObject(HOST + "/add-company?name={name}&stocks={stocks}&stockPrice={stockPrice}",
                null, String.class, name, stocks, stockPrice);
    }

    private void changeStocksPrice(final String name, final Double newPrice) {
        testRestTemplate.postForObject(HOST + "/change-stocks-price?name={name}&newPrice={newPrice}",
                null, String.class, name, newPrice);
    }
}
