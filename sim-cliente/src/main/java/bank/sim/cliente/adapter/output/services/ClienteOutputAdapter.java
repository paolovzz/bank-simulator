package bank.sim.cliente.adapter.output.services;

import java.util.List;

import bank.sim.cliente.application.ports.output.ClienteOutputPort;
import bank.sim.cliente.application.ports.output.ClienteRepositoryPort;
import bank.sim.cliente.application.ports.output.EventsPublisherPort;
import bank.sim.cliente.domain.models.aggregates.Cliente;
import bank.sim.cliente.domain.models.events.EventPayload;
import bank.sim.cliente.domain.models.vo.IdCliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ClienteOutputAdapter  implements ClienteOutputPort{

    private final ClienteRepositoryPort ccRepo;
    private final EventsPublisherPort publisherPort;

    @Override
    public void salva(Cliente cc) {

        List<EventPayload> events = cc.popChanges();
        ccRepo.save(cc.getIdCliente(), events);
        publisherPort.publish(Cliente.AGGREGATE_NAME, cc.getIdCliente().id(), events);
    }

    @Override
    public Cliente recuperaDaId(IdCliente idCliente) {
       return ccRepo.findById(idCliente.id());
    }
    
}
