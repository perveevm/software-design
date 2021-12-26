package ru.perveevm.mock;

import ru.perveevm.mock.api.VkAPIClient;
import ru.perveevm.mock.api.VkAPIService;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        VkAPIClient client = VkAPIClient.fromEnvironment("ACCESS_TOKEN");
        VkAPIService service = new VkAPIService(client);

        int[] response = service.countPosts("#образование", 24);
        System.out.println(Arrays.toString(response));
    }
}
