package bank.sim.contocorrente.adapter.config;

import bank.sim.contocorrente.domain.services.GeneratoreId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class Producer {
    
    @Produces
    public GeneratoreId generatoreIdService() {
        return new GeneratoreId();
    }
}
