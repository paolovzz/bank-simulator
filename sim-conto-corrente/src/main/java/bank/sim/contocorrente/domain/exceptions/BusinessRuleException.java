package bank.sim.contocorrente.domain.exceptions;

import lombok.Getter;

@Getter
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }


    
}
