package bank.sim.cliente.adapter.input.rest;

import bank.sim.cliente.adapter.input.rest.requests.CreaClienteReq;
import bank.sim.cliente.adapter.input.rest.requests.RichiediChiusuraContoRequest;
import bank.sim.cliente.application.ClienteUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Path("/clienti")
@ApplicationScoped
@Slf4j
public class ClienteResource {

    @Inject
    private ClienteUseCase clienteUseCase;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response creaCliente(CreaClienteReq req) {
        log.info("ENDPOINT CREAZIONE CLIENTE: {}", req);
        clienteUseCase.creaCliente(RestConverter.toCreaClienteCmd(req));
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/{idCliente}/richiedi-apertura-conto")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response richiediAperturaConto(@PathParam(value = "idCliente") String idCliente) {
        log.info("ENDPOINT RICHIEDI APERTURA CONTO: {}", idCliente);
        clienteUseCase.richiediAperturaConto(RestConverter.toRichiediAperturaContoCmd(idCliente));
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/{idCliente}/richiedi-chiusura-conto")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response richiediChiusuraConto(@PathParam(value = "idCliente") String idCliente, RichiediChiusuraContoRequest request) {
        log.info("ENDPOINT RICHIEDI CHIUSURA CONTO: {}", idCliente);
        clienteUseCase.richiediChiusuraConto(RestConverter.toRichiediChiusuraContoCmd(idCliente, request));
        return Response.status(Response.Status.ACCEPTED).build();
    }
}