package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.Getter;

@Getter
public class NumeroConto {

    private String numero;

    private NumeroConto(String numero) {
        this.numero = numero;
    }

    public static NumeroConto with(String numero) {
        if (numero == null) {
            throw new ValidazioneException(NumeroConto.class.getSimpleName(), CodiceErrore.NUMERO_CONTO_NON_PUO_ESSERE_NULL.getCodice());
        }
        return new NumeroConto(numero);
    }
}
