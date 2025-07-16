package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;

public record Cab ( String codice){


    public Cab(String codice) {
        if (codice == null) {
            throw new ValidazioneException(Cab.class.getSimpleName(), CodiceErrore.CODICE_CAB_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.codice = codice;
    }
}
