package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.Getter;

@Getter
public class SoglieBonifico {

    private int sogliaMensile;
    private int sogliaGiornaliera;

    private SoglieBonifico(int sogliaMensile, int sogliaGiornaliera) {
        this.sogliaMensile = sogliaMensile;
        this.sogliaGiornaliera = sogliaGiornaliera;
    }

    public static SoglieBonifico with(int sogliaMensile, int sogliaGiornaliera) {
        if (sogliaMensile <= 0) {
            throw new ValidazioneException(Bic.class.getSimpleName(), CodiceErrore.SOGLIA_BONIFICO_MENSILE_MINORE_UGUALE_ZERO.getCodice());
        }
        if (sogliaGiornaliera <= 0) {
            throw new ValidazioneException(Bic.class.getSimpleName(), CodiceErrore.SOGLIA_BONIFICO_GIORNALIERA_MINORE_UGUALE_ZERO.getCodice());
        }
        if (sogliaMensile < sogliaGiornaliera) {
            throw new ValidazioneException(Bic.class.getSimpleName(), CodiceErrore.SOGLIE_BONIFICO_NON_VALIDE_NON_COERERENTI.getCodice());
        }
        return new SoglieBonifico(sogliaMensile, sogliaGiornaliera);
    }
}
