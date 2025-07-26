package bank.sim.cliente.domain.models.vo;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record Residenza(String residenza){

    public Residenza(String residenza) {
        if (residenza == null || residenza.isBlank()) {
            throw new ValidazioneException(Residenza.class.getSimpleName(), CodiceErrore.RESIDENZA_NON_VALIDA.getCodice());
        }
        this.residenza = residenza;
    }
}
