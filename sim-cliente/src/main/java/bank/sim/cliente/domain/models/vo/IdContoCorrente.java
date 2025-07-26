package bank.sim.cliente.domain.models.vo;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record IdContoCorrente(String id) {
    public IdContoCorrente {
        if (id == null || id.isBlank()) {
            throw new ValidazioneException(
                IdContoCorrente.class.getSimpleName(),
                CodiceErrore.ID_NON_VALIDO.getCodice()
            );
        }
    }
}
