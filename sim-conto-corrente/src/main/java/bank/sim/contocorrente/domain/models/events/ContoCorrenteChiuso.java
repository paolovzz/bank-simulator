package bank.sim.contocorrente.domain.models.events;

import bank.sim.contocorrente.domain.models.vo.DataChiusura;
import lombok.Value;

@Value(staticConstructor = "with")
public class ContoCorrenteChiuso implements EventPayload {

    private DataChiusura dataChiusura;
    
    @Override
    public String eventType() {
        return "ContoCorrenteChiuso";
    }
}
