package bank.sim.contocorrente.domain.models.events;

import bank.sim.contocorrente.domain.models.vo.CodiceCliente;
import lombok.Value;

@Value(staticConstructor = "with")
public class AperturaContoCorrenteFallita implements EventPayload {

    private String message;
    private CodiceCliente codiceCliente;
    
    @Override
    public String eventType() {
        return "AperturaContoCorrenteFallita";
    }
}
