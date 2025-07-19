package bank.sim.contocorrente.domain.models.events;

import bank.sim.contocorrente.domain.models.vo.DataChiusura;

public record ContoCorrenteChiuso(DataChiusura dataChiusura) implements EventPayload {
    @Override
    public String eventType() {
        return "ContoCorrenteChiuso";
    }
}
