package ru.perveevm.events.events;

import javax.persistence.*;

@Entity
@Table(name = "creation_events")
public class ClientCreationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }
}
