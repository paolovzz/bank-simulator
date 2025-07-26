package bank.sim.cliente.domain.models.events;

import bank.sim.cliente.domain.models.vo.DataNascita;

public record AperturaContoCorrenteRichiesta(DataNascita dataNascita) implements EventPayload {

    @Override
    public String eventType() {
        return "AperturaContoCorrenteRichiesta";
    }
}
