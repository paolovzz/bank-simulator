package bank.sim.cliente.domain.models.vo;

import java.time.LocalDate;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record DataNascita(LocalDate data){

    public DataNascita(LocalDate data) {
        if (data == null) {
            throw new ValidazioneException(DataNascita.class.getSimpleName(), CodiceErrore.DATA_NASCITA_NON_PUO_ESSERE_NULL.getCodice());
        }
        this.data = data;
    }
}
