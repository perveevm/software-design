package ru.perveevm.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.perveevm.events.events.SubscriptionExtensionEvent;

import java.util.List;

@Repository
public interface SubscriptionExtensionRepository extends JpaRepository<SubscriptionExtensionEvent, Long> {
    List<SubscriptionExtensionEvent> findAllByClientId(final Long clientId);
}
