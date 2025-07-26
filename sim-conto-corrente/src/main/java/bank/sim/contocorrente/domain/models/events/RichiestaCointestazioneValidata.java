package bank.sim.contocorrente.domain.models.events;

import bank.sim.contocorrente.domain.models.vo.IdCliente;

public record RichiestaCointestazioneValidata(IdCliente idCliente) implements EventPayload {

    @Override
    public String eventType() {
        return "RichiestaCointestazioneValidata";
    }
}
