package bank.sim.contocorrente.domain.models.vo;

import bank.sim.contocorrente.domain.exceptions.ValidazioneException;
import bank.sim.contocorrente.domain.models.enums.CodiceErrore;
import lombok.Getter;

@Getter
public class IdContoCorrente {
    
    private String id;

    private IdContoCorrente(String id) {
        this.id = id;
    }

    public static IdContoCorrente with(String id) {
        if (id == null) {
            throw new ValidazioneException(IdContoCorrente.class.getSimpleName(), CodiceErrore.ID_NON_PUO_ESSERE_NULL.getCodice());
        }
        return new IdContoCorrente(id);
    }
}
