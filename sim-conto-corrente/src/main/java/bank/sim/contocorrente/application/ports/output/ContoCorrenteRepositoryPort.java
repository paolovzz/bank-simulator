package bank.sim.contocorrente.application.ports.output;

import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;

public interface ContoCorrenteRepositoryPort {
    
    public void save(ContoCorrente cc);
}
