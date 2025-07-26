package bank.sim.cliente.domain.services;

import java.util.UUID;

import bank.sim.cliente.domain.models.vo.IdCliente;

public final class GeneratoreIdCliente {
    
    public final IdCliente genera() {
        return new IdCliente(UUID.randomUUID().toString());
    }
}
