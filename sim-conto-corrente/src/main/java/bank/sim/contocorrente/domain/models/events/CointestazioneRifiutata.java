package bank.sim.contocorrente.domain.models.events;

import bank.sim.contocorrente.domain.models.vo.IdCliente;

public record CointestazioneRifiutata(IdCliente idCliente) implements EventPayload {

    @Override
    public String eventType() {
        return "CointestazioneRifiutata";
    }
}
