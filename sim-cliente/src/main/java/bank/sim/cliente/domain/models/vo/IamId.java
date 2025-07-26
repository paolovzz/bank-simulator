package bank.sim.cliente.domain.models.vo;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record IamId(String id){

    public IamId(String id) {
        if (id == null || id.isBlank()) {
            throw new ValidazioneException(CodiceCliente.class.getSimpleName(), CodiceErrore.ID_NON_VALIDO.getCodice());
        }
        this.id = id;
    }
}
