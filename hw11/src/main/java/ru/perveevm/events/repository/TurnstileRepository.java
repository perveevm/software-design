package ru.perveevm.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.perveevm.events.events.TurnstileEvent;

import java.util.List;

@Repository
public interface TurnstileRepository extends JpaRepository<TurnstileEvent, Long> {
    List<TurnstileEvent> findAllByClientId(final Long clientId);
}
