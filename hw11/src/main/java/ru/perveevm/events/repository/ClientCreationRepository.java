package ru.perveevm.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.perveevm.events.events.ClientCreationEvent;

@Repository
public interface ClientCreationRepository extends JpaRepository<ClientCreationEvent, Long> {
}
