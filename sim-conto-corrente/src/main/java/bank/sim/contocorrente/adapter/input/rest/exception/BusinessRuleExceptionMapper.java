package bank.sim.contocorrente.adapter.input.rest.exception;

    
import java.time.Instant;

import bank.sim.contocorrente.adapter.input.rest.ErrorResponse;
import bank.sim.contocorrente.domain.exceptions.BusinessRuleException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessRuleExceptionMapper implements ExceptionMapper<BusinessRuleException> {

    @Override
    public Response toResponse(BusinessRuleException exception) {
        ErrorResponse errore = new ErrorResponse(exception.getMessage());
        return Response.status(422).entity(errore).build();
    }

}

