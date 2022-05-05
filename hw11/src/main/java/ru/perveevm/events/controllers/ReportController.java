package ru.perveevm.events.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.perveevm.events.queries.Statistics;
import ru.perveevm.events.services.ReportService;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(final ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/daily_report")
    public Map<Instant, Long> getDailyReport(@RequestParam final Long days) {
        return reportService.calculateDailyStatistics(days);
    }

    @GetMapping("/average_report")
    public Statistics getAverageReport() {
        return reportService.calculateAverageStatistics();
    }
}
