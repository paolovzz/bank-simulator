package bank.sim.contocorrente.domain.models.aggregates;

import bank.sim.contocorrente.domain.models.events.EventPayload;

public interface Applier {
    void apply(EventPayload event);
}