package bank.sim.contocorrente.domain.models.vo;

import java.time.LocalDate;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;

public record DatiCliente(String idCliente, LocalDate dataNascita) {


    public DatiCliente(String idCliente, LocalDate dataNascita) {
        if (idCliente == null) {
            throw new ValidazioneException(DatiCliente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        if (idCliente.isBlank()) {
            throw new ValidazioneException(DatiCliente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        if (dataNascita == null) {
            throw new ValidazioneException(DatiCliente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.idCliente = idCliente;
        this.dataNascita = dataNascita;
    }
}
