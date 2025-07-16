package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;

public record CodiceCliente(String codice){


    public CodiceCliente(String codice) {
        if (codice == null) {
            throw new ValidazioneException(CodiceCliente.class.getSimpleName(), CodiceErrore.CODICE_CLIENTE_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.codice = codice;
    }
}
