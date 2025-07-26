package bank.sim.cliente.domain.models.events;

import bank.sim.cliente.domain.models.vo.CodiceCliente;
import bank.sim.cliente.domain.models.vo.DatiAnagraficiCliente;
import bank.sim.cliente.domain.models.vo.IamId;
import bank.sim.cliente.domain.models.vo.IdCliente;

public record ClienteCreato(
        IdCliente idCliente,
        CodiceCliente codiceCliente,
        DatiAnagraficiCliente datiAnagrafici,
        IamId iamId) implements EventPayload {

    @Override
    public String eventType() {
        return "ClienteCreato";
    }
}
