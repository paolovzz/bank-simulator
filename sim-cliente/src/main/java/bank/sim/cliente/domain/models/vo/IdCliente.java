package bank.sim.cliente.domain.models.vo;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record IdCliente(String id) {
    public IdCliente {
        if (id == null || id.isBlank()) {
            throw new ValidazioneException(
                    IdCliente.class.getSimpleName(),
                    CodiceErrore.ID_NON_VALIDO.getCodice());
        }
    }
}
