package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.Getter;

@Getter
public class Iban {

    private String codice;

    private Iban(String codice) {
        this.codice = codice;
    }

    public static Iban with(String codice) {
        if (codice == null) {
            throw new ValidazioneException(Iban.class.getSimpleName(), CodiceErrore.IBAN_NON_PUO_ESSERE_NULL.getCodice());
        }
        return new Iban(codice);
    }
}
