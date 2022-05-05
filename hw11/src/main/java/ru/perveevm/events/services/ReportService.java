package ru.perveevm.events.services;

import org.springframework.stereotype.Service;
import ru.perveevm.events.events.TurnstileDirection;
import ru.perveevm.events.events.TurnstileEvent;
import ru.perveevm.events.queries.Statistics;
import ru.perveevm.events.repository.TurnstileRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReportService {
    private final TurnstileRepository turnstileRepository;

    private Instant lastSnapshot = Instant.MIN;
    private Long numberOfVisits = 0L;
    private Long numberOfMilliSeconds = 0L;
    private Instant firstTiming = null;

    public ReportService(final TurnstileRepository turnstileRepository) {
        this.turnstileRepository = turnstileRepository;
    }

    public Map<Instant, Long> calculateDailyStatistics(final Long daysBack) {
        Instant curTime = Instant.now();
        List<Instant> timings = turnstileRepository.findAll().stream()
                .filter(t -> t.getDirection() == TurnstileDirection.IN)
                .map(TurnstileEvent::getModificationTime)
                .filter(modificationTime -> modificationTime.isAfter(curTime.minus(daysBack, ChronoUnit.DAYS)))
                .map(t -> t.truncatedTo(ChronoUnit.DAYS))
                .toList();

        Map<Instant, Long> result = new HashMap<>();
        timings.forEach(t -> result.put(t, result.getOrDefault(t, 0L) + 1));
        return result;
    }

    public Statistics calculateAverageStatistics() {
        if (firstTiming == null) {
            Optional<Instant> firstEvent = turnstileRepository.findAll().stream()
                    .map(TurnstileEvent::getModificationTime)
                    .min(Instant::compareTo);

            if (firstEvent.isEmpty()) {
                return new Statistics(0.0, 0.0);
            }

            firstTiming = firstEvent.get();
        }

        Instant currentTime = Instant.now();
        numberOfVisits += turnstileRepository.findAll().stream()
                .filter(t -> t.getDirection() == TurnstileDirection.IN)
                .filter(t -> t.getModificationTime().isAfter(lastSnapshot))
                .filter(t -> t.getModificationTime().isBefore(currentTime))
                .count();
        numberOfMilliSeconds += turnstileRepository.findAll().stream()
                .filter(t -> t.getDirection() == TurnstileDirection.OUT)
                .map(TurnstileEvent::getModificationTime)
                .filter(modificationTime -> modificationTime.isAfter(lastSnapshot))
                .filter(modificationTime -> modificationTime.isBefore(currentTime))
                .mapToLong(Instant::toEpochMilli)
                .sum();
        numberOfMilliSeconds -= turnstileRepository.findAll().stream()
                .filter(t -> t.getDirection() == TurnstileDirection.IN)
                .map(TurnstileEvent::getModificationTime)
                .filter(modificationTime -> modificationTime.isAfter(lastSnapshot))
                .filter(modificationTime -> modificationTime.isBefore(currentTime))
                .mapToLong(Instant::toEpochMilli)
                .sum();

        lastSnapshot = currentTime;
        long numberOfDays = Duration.between(firstTiming, currentTime).toDays();
        return new Statistics(1.0 * numberOfVisits / numberOfDays,
                1.0 * numberOfMilliSeconds / numberOfVisits / 60.0 / 1000.0);
    }
}
