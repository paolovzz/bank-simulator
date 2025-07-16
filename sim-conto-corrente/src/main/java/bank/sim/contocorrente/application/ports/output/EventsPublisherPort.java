package bank.sim.contocorrente.application.ports.output;

import java.util.List;

import bank.sim.contocorrente.domain.models.events.EventPayload;

public interface EventsPublisherPort {
    void publish(String aggregateName, String aggregateId, List<EventPayload> events);
}
