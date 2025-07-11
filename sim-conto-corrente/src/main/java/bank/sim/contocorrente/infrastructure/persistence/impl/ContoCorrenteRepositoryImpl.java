package bank.sim.contocorrente.infrastructure.persistence.impl;

import bank.sim.contocorrente.application.ports.output.ContoCorrenteRepositoryPort;
import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;
import bank.sim.contocorrente.infrastructure.persistence.ContoCorrenteMapper;
import bank.sim.contocorrente.infrastructure.persistence.entities.ContoCorrenteEntity;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContoCorrenteRepositoryImpl implements PanacheMongoRepository<ContoCorrenteEntity>, ContoCorrenteRepositoryPort {

    @Override
    public void save(ContoCorrente cc) {
        persist(ContoCorrenteMapper.toEntity(cc));
    }
    
}
