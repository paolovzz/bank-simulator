package bank.sim.contocorrente.domain.models.events;

import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import lombok.Value;

@Value(staticConstructor = "with")
public class ImpostazioneSoglieBonificoFallita implements EventPayload {

    private String message;
    private IdContoCorrente idContoCorrente;
    
    @Override
    public String eventType() {
        return "ImpostazioneSoglieBonificoFallita";
    }
}
