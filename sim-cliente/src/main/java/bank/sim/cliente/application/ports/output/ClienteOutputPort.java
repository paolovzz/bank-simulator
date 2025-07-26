package bank.sim.cliente.application.ports.output;

import bank.sim.cliente.domain.models.aggregates.Cliente;
import bank.sim.cliente.domain.models.vo.IdCliente;

public interface ClienteOutputPort {
    
    public void salva(Cliente cc);
    public Cliente recuperaDaId(IdCliente idCliente);
}
