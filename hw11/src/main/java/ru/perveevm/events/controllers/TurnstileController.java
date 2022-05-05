package ru.perveevm.events.controllers;

import org.springframework.web.bind.annotation.*;
import ru.perveevm.events.services.TurnstileService;

@RestController
@RequestMapping("/turnstile")
public class TurnstileController {
    private final TurnstileService turnstileService;

    public TurnstileController(final TurnstileService turnstileService) {
        this.turnstileService = turnstileService;
    }

    @PostMapping("/addIn/{clientId}")
    public void addIn(@PathVariable final Long clientId) {
        turnstileService.addClientIn(clientId);
    }

    @PostMapping("/addOut/{clientId}")
    public void addOut(@PathVariable final Long clientId) {
        turnstileService.addClientOut(clientId);
    }
}
