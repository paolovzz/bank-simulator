package bank.sim.contocorrente.domain.exceptions;

import bank.sim.contocorrente.domain.models.events.EventPayload;
import lombok.Getter;

@Getter
public class BusinessRuleException extends RuntimeException {

    private String aggregateName;
    private String aggregateId;
    private EventPayload payload;
    public BusinessRuleException(String message, String aggregateName, String aggregateId, EventPayload payload) {
        super(message);
        this.aggregateName = aggregateName;
        this.aggregateId = aggregateId;
        this.payload = payload;
    }


    
}
