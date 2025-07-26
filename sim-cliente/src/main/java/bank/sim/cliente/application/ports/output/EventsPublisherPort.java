package bank.sim.cliente.application.ports.output;

import java.util.List;

import bank.sim.cliente.domain.models.events.EventPayload;

public interface EventsPublisherPort {
    void publish(String aggregateName, String aggregateId, List<EventPayload> events);
}
