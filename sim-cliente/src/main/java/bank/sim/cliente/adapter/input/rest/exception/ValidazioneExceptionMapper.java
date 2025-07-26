package bank.sim.cliente.adapter.input.rest.exception;

    
import bank.sim.cliente.adapter.input.rest.ErrorResponse;
import bank.sim.cliente.domain.exceptions.ValidazioneException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidazioneExceptionMapper implements ExceptionMapper<ValidazioneException> {

    @Override
    public Response toResponse(ValidazioneException exception) {
        ErrorResponse errore = new ErrorResponse(exception.getMessage());
        return Response.status(400).entity(errore).build();
    }

}

