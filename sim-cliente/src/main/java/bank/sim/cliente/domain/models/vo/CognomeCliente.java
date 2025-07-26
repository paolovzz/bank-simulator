package bank.sim.cliente.domain.models.vo;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record CognomeCliente(String cognome){

    public CognomeCliente(String cognome) {
        if (cognome == null || cognome.isBlank()) {
            throw new ValidazioneException(CognomeCliente.class.getSimpleName(), CodiceErrore.COGNOME_CLIENTE_NON_VALIDO.getCodice());
        }
        this.cognome = cognome;
    }
}
