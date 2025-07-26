package bank.sim.cliente.domain.models.vo;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record CodiceCliente(String codice){

    public CodiceCliente(String codice) {
        if (codice == null || codice.isBlank()) {
            throw new ValidazioneException(CodiceCliente.class.getSimpleName(), CodiceErrore.CODICE_CLIENTE_NON_VALIDO.getCodice());
        }
        this.codice = codice;
    }
}
