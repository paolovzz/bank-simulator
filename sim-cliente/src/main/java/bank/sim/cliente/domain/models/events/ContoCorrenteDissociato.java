package bank.sim.cliente.domain.models.events;

import bank.sim.cliente.domain.models.vo.IdContoCorrente;

public record ContoCorrenteDissociato(IdContoCorrente idContoCorrente) implements EventPayload {

    @Override
    public String eventType() {
        return "ContoCorrenteDissociato";
    }
}
