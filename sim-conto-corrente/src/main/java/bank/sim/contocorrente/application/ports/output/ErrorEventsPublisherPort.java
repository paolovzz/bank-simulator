package bank.sim.contocorrente.application.ports.output;

import bank.sim.contocorrente.domain.models.events.EventPayload;

public interface ErrorEventsPublisherPort {
    void publish(EventPayload event, String aggregateName, String aggregateId);
}
