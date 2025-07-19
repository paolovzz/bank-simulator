package bank.sim.contocorrente.domain.models.events;

import bank.sim.contocorrente.domain.models.vo.CodiceCliente;

public record RichiestaCointestazioneValidata(CodiceCliente codiceCliente) implements EventPayload {

    @Override
    public String eventType() {
        return "RichiestaCointestazioneValidata";
    }
}
