package bank.sim.contocorrente.domain.models.vo;

import java.time.LocalDate;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;

public record DatiCliente(String codiceCliente, LocalDate dataNascita) {


    public DatiCliente(String codiceCliente, LocalDate dataNascita) {
        if (codiceCliente == null) {
            throw new ValidazioneException(DatiCliente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        if (codiceCliente.isBlank()) {
            throw new ValidazioneException(DatiCliente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        if (dataNascita == null) {
            throw new ValidazioneException(DatiCliente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.codiceCliente = codiceCliente;
        this.dataNascita = dataNascita;
    }
}
