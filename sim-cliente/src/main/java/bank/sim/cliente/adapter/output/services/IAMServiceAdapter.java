package bank.sim.cliente.adapter.output.services;

import bank.sim.cliente.domain.ports.IAMService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IAMServiceAdapter implements IAMService{

    @Override
    public String registraUtente() {
        //FIXME
        return "iam_id_keyCloak";
    }
    
}
