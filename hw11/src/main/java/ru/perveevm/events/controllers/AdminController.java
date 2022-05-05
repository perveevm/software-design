package ru.perveevm.events.controllers;

import org.springframework.web.bind.annotation.*;
import ru.perveevm.events.commands.ClientCreationCommand;
import ru.perveevm.events.commands.SubscriptionExtensionCommand;
import ru.perveevm.events.queries.Subscription;
import ru.perveevm.events.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(final AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create")
    public Long createClient(@RequestBody final ClientCreationCommand command) {
        return adminService.createClient(command);
    }

    @PostMapping("/extend")
    public void extendSubscription(@RequestBody final SubscriptionExtensionCommand command) {
        adminService.extendSubscription(command);
    }

    @GetMapping("/subscriptions/{clientId}")
    public Subscription getSubscription(@PathVariable final Long clientId) {
        return adminService.getSubscription(clientId);
    }
}
