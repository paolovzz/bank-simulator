package bank.sim.cliente.adapter.config;

import bank.sim.cliente.domain.services.GeneratoreCodiceCliente;
import bank.sim.cliente.domain.services.GeneratoreIdCliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class Producer {
    
    @Produces
    public GeneratoreIdCliente generatoreIdService() {
        return new GeneratoreIdCliente();
    }
 
    @Produces
    public GeneratoreCodiceCliente generatoreCodiceCliente() {
        return new GeneratoreCodiceCliente();
    }
}
