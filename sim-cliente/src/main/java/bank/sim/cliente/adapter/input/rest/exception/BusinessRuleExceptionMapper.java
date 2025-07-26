package bank.sim.cliente.adapter.input.rest.exception;

    
import bank.sim.cliente.adapter.input.rest.ErrorResponse;
import bank.sim.cliente.domain.exceptions.BusinessRuleException;
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

