package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;

public record IdCliente(String id){


    public IdCliente(String id) {
        if (id == null) {
            throw new ValidazioneException(IdCliente.class.getSimpleName(), CodiceErrore.ID_CLIENTE_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.id = id;
    }
}
