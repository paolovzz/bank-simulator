package bank.sim.contocorrente.domain.models.vo;

import java.time.LocalDateTime;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.Getter;

@Getter
public class DataChiusura {

    private LocalDateTime dataOra;

    private DataChiusura(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    public static DataChiusura with(LocalDateTime dataOra) {
        if (dataOra == null) {
            throw new ValidazioneException(DataChiusura.class.getSimpleName(), CodiceErrore.DATA_APERTURA_NON_PUO_ESSERE_NULL.getCodice());
        }
        return new DataChiusura(dataOra);
    }
}
