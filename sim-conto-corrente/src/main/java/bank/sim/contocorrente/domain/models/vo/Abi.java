package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.Getter;

@Getter
public class Abi {

    private String codice;

    private Abi(String codice) {
        this.codice = codice;
    }

    public static Abi with(String codice) {
        if (codice == null) {
            throw new ValidazioneException(Abi.class.getSimpleName(), CodiceErrore.CODICE_ABI_NON_PUO_ESSERE_NULL.getCodice());
        }
        return new Abi(codice);
    }
}
