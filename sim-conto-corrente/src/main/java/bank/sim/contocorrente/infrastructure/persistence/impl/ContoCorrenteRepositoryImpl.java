package bank.sim.contocorrente.infrastructure.persistence.impl;

import java.util.Comparator;
import java.util.List;

import bank.sim.contocorrente.application.ports.output.ContoCorrenteRepositoryPort;
import bank.sim.contocorrente.domain.models.events.EventPayload;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import bank.sim.contocorrente.infrastructure.persistence.entities.EventStoreEntity;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContoCorrenteRepositoryImpl implements PanacheMongoRepository<EventStoreEntity>, ContoCorrenteRepositoryPort {

    @Override
    public void save(String aggregateName, IdContoCorrente idContoCorrente, List<EventPayload> events) {
       long nextSequence = getNextSequence(idContoCorrente.getId());

       for(EventPayload ev: events) {
            EventStoreEntity entity = new EventStoreEntity(aggregateName, ev.eventType(), ev, nextSequence);
            entity.persist();
            nextSequence += 1;
       }
    }


    private long getNextSequence(String aggregateId) {
        EventStoreEntity last = EventStoreEntity.find("aggregateId = ?1", aggregateId)
                .stream()
                .map(e -> (EventStoreEntity) e)
                .max(Comparator.comparingLong(se -> se.getSequence()))
                .orElse(null);
        return last != null ? last.getSequence() + 1 : 1;
    }

    
}
