package bank.sim.cliente.domain.models.vo;

import java.time.LocalDate;
import java.time.Period;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record DatiAnagraficiCliente(NomeCliente nomeCliente, CognomeCliente cognomeCliente, DataNascita dataNascita, CodiceFiscale codiceFiscale, Email email, Telefono telefono, Residenza residenza){

    public DatiAnagraficiCliente(NomeCliente nomeCliente, CognomeCliente cognomeCliente, DataNascita dataNascita, CodiceFiscale codiceFiscale, Email email, Telefono telefono, Residenza residenza) {
        LocalDate oggi = LocalDate.now();
        Period eta = Period.between(dataNascita.data(), oggi);
        if(eta.getYears() < 18) {
            throw new ValidazioneException(DatiAnagraficiCliente.class.getSimpleName(), CodiceErrore.CLIENTE_DEVE_ESSERE_MAGGIORENNE.getCodice());
        }
        
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.telefono = telefono;
        this.residenza = residenza;
    }
}
