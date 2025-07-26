package bank.sim.cliente.adapter.input.rest.exception;

    
import bank.sim.cliente.adapter.input.rest.ErrorResponse;
import bank.sim.cliente.application.exceptions.ClienteNonTrovatoException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ClienteNonTrovatoExceptionMapper implements ExceptionMapper<ClienteNonTrovatoException> {

    @Override
    public Response toResponse(ClienteNonTrovatoException exception) {
        ErrorResponse errore = new ErrorResponse(exception.getMessage());
        return Response.status(404).entity(errore).build();
    }

}

