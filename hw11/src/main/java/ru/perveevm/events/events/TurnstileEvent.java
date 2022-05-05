package ru.perveevm.events.events;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "turnstile_events")
public class TurnstileEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "direction", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TurnstileDirection direction;

    @Column(name = "modification_time", nullable = false)
    private Instant modificationTime;

    @PrePersist
    public void modify() {
        modificationTime = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public TurnstileDirection getDirection() {
        return direction;
    }

    public Instant getModificationTime() {
        return modificationTime;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setClientId(final Long clientId) {
        this.clientId = clientId;
    }

    public void setDirection(final TurnstileDirection direction) {
        this.direction = direction;
    }

    public void setModificationTime(final Instant modificationTime) {
        this.modificationTime = modificationTime;
    }
}
