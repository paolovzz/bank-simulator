package bank.sim.cliente.application.ports.output;

import java.util.List;

import bank.sim.cliente.domain.models.aggregates.Cliente;
import bank.sim.cliente.domain.models.events.EventPayload;
import bank.sim.cliente.domain.models.vo.IdCliente;

public interface ClienteRepositoryPort {
    
    public void save(IdCliente idCliente, List<EventPayload> events);
    public Cliente findById (String aggregateId);
}
