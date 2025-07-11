package bank.sim.contocorrente.adapter.output.services;

import bank.sim.contocorrente.application.ports.output.ContoCorrenteOutputPort;
import bank.sim.contocorrente.application.ports.output.ContoCorrenteRepositoryPort;
import bank.sim.contocorrente.application.ports.output.EventsPublisherPort;
import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;
import bank.sim.contocorrente.domain.models.vo.IdCliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ContoCorrentOutputAdapter  implements ContoCorrenteOutputPort{

    private final ContoCorrenteRepositoryPort ccRepo;
    private final EventsPublisherPort publisherPort;

    @Override
    public void salva(IdCliente idCliente, ContoCorrente cc) {
        ccRepo.save(cc);
        publisherPort.publish(cc);
    }
    
}
