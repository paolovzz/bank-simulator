package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.Getter;

@Getter
public class Bic {

    private String codice;

    private Bic(String codice) {
        this.codice = codice;
    }

    public static Bic with(String codice) {
        if (codice == null) {
            throw new ValidazioneException(Bic.class.getSimpleName(), CodiceErrore.CODICE_SWIFT_NON_PUO_ESSERE_NULL.getCodice());
        }
        return new Bic(codice);
    }
}
