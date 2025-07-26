package bank.sim.cliente.domain.models.aggregates;

import bank.sim.cliente.domain.models.events.EventPayload;

public interface Applier {
    void apply(EventPayload event);
}