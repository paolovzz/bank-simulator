package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;

public record Abi(String codice) {


    public Abi(String codice) {
        if (codice == null) {
            throw new ValidazioneException(Abi.class.getSimpleName(), CodiceErrore.CODICE_ABI_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.codice = codice;
    }
}
