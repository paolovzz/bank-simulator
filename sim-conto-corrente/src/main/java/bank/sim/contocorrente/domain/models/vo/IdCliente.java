package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class IdCliente {

    private String id;

    private IdCliente(String id) {
        this.id = id;
    }

    public static IdCliente with(String id) {
        if (id == null) {
            throw new ValidazioneException(IdCliente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        return new IdCliente(id);
    }
}
