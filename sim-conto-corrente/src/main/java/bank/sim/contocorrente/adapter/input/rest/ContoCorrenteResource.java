package bank.sim.contocorrente.adapter.input.rest;

import bank.sim.contocorrente.adapter.input.rest.requests.CreaContoCorrenteReq;
import bank.sim.contocorrente.application.ContoCorrenteUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;

@Path("/conticorrenti")
@ApplicationScoped
@Slf4j
public class ContoCorrenteResource {

    @Inject
    private ContoCorrenteUseCase ccUsecase;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response creaConto(CreaContoCorrenteReq req) {
        log.info("ENDPOINT CREAZIONE CONTO: {}", req);
        ccUsecase.creaContoCorrente(RestConverter.toCreaContoCorrenteCmd(req));
        return Response.status(Response.Status.CREATED).build();
    }
}