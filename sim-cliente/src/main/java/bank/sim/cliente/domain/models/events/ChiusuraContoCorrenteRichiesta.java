package bank.sim.cliente.domain.models.events;

import bank.sim.cliente.domain.models.vo.IdContoCorrente;

public record ChiusuraContoCorrenteRichiesta(IdContoCorrente idContoCorrente) implements EventPayload {

    @Override
    public String eventType() {
        return "ChiusuraContoCorrenteRichiesta";
    }
}
