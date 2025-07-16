package bank.sim.contocorrente.adapter.output.services;

import java.util.List;

import bank.sim.contocorrente.application.ports.output.ContoCorrenteOutputPort;
import bank.sim.contocorrente.application.ports.output.ContoCorrenteRepositoryPort;
import bank.sim.contocorrente.application.ports.output.EventsPublisherPort;
import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;
import bank.sim.contocorrente.domain.models.events.EventPayload;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ContoCorrentOutputAdapter  implements ContoCorrenteOutputPort{

    private final ContoCorrenteRepositoryPort ccRepo;
    private final EventsPublisherPort publisherPort;

    @Override
    public void salva(ContoCorrente cc) {

        List<EventPayload> events = cc.popChanges();
        ccRepo.save(cc.getIdContoCorrente(), events);
        publisherPort.publish(ContoCorrente.AGGREGATE_NAME, cc.getIdContoCorrente().id(), events);
    }

    @Override
    public ContoCorrente recuperaDaId(IdContoCorrente idContoCorrente) {
       return ccRepo.findById(idContoCorrente.id());
    }
    
}
