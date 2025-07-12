package bank.sim.contocorrente.domain.models.vo;

import java.time.LocalDate;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DatiCliente {

    private String codiceCliente;
    private LocalDate dataNascita;

    private DatiCliente(String codiceCliente, LocalDate dataNascita) {
        this.codiceCliente = codiceCliente;
        this.dataNascita = dataNascita;
    }

    public static DatiCliente with(String codiceCliente, LocalDate dataNascita) {
        if (codiceCliente == null) {
            throw new ValidazioneException(DatiCliente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        if (codiceCliente.isBlank()) {
            throw new ValidazioneException(DatiCliente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        if (dataNascita == null) {
            throw new ValidazioneException(DatiCliente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        return new DatiCliente(codiceCliente, dataNascita);
    }
}
