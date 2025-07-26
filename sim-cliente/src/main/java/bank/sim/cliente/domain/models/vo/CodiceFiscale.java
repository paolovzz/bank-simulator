package bank.sim.cliente.domain.models.vo;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record CodiceFiscale(String codice){

    public CodiceFiscale(String codice) {
        String regexCF = "^[A-Z]{6}[0-9]{2}[A-EHLMPRST][0-9]{2}[A-Z][0-9]{3}[A-Z]$";
        if (codice == null || !codice.matches(regexCF)) {
            throw new ValidazioneException(DatiAnagraficiCliente.class.getSimpleName(), CodiceErrore.CODICE_FISCALE_NON_VALIDO.getCodice());
        }
        this.codice = codice;
    }
}
