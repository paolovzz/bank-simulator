package bank.sim.contocorrente.domain.models.events;

import bank.sim.contocorrente.domain.models.vo.SoglieBonifico;

public record SoglieBonificoImpostate(
        SoglieBonifico soglieBonifico) implements EventPayload {

    @Override
    public String eventType() {
        return "SoglieBonificoImpostate";
    }
}
