package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.Getter;

@Getter
public class CodiceCliente {

    private String codice;

    private CodiceCliente(String codice) {
        this.codice = codice;
    }

    public static CodiceCliente with(String codice) {
        if (codice == null) {
            throw new ValidazioneException(CodiceCliente.class.getSimpleName(), CodiceErrore.CODICE_CLIENTE_NON_PUO_ESSERE_NULL.getCodice());
        }
        return new CodiceCliente(codice);
    }
}
