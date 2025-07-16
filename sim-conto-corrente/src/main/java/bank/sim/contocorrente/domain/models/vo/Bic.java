package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;

public record Bic(String codice) {


    public Bic(String codice) {
        if (codice == null) {
            throw new ValidazioneException(Bic.class.getSimpleName(), CodiceErrore.CODICE_SWIFT_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.codice = codice;
    }
}
