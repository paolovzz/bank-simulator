package bank.sim.contocorrente.domain.services;

import java.util.UUID;

public final class GeneratoreId {
    
    public final String genera() {
        return UUID.randomUUID().toString();
    }
}
