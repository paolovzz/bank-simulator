package bank.sim.contocorrente.application.ports.output;

import java.util.List;

import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;
import bank.sim.contocorrente.domain.models.events.EventPayload;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;

public interface ContoCorrenteRepositoryPort {
    
    public void save(IdContoCorrente idContoCorrente, List<EventPayload> events);
    public ContoCorrente findById (String aggregateId);
}
