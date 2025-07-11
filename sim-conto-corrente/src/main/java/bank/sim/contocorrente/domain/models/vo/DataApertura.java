package bank.sim.contocorrente.domain.models.vo;

import java.time.LocalDateTime;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.Getter;

@Getter
public class DataApertura {

    private LocalDateTime dataOra;

    private DataApertura(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    public static DataApertura with(LocalDateTime dataOra) {
        if (dataOra == null) {
            throw new ValidazioneException(DataApertura.class.getSimpleName(), CodiceErrore.DATA_APERTURA_NON_PUO_ESSERE_NULL.getCodice());
        }
        return new DataApertura(dataOra);
    }
}
