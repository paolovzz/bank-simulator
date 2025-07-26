package bank.sim.cliente.application;

import bank.sim.cliente.application.ports.input.commands.CreaClienteCmd;
import bank.sim.cliente.application.ports.input.commands.RichiediAperturaContoCmd;
import bank.sim.cliente.application.ports.output.ClienteOutputPort;
import bank.sim.cliente.domain.models.aggregates.Cliente;
import bank.sim.cliente.domain.ports.IAMService;
import bank.sim.cliente.domain.services.GeneratoreCodiceCliente;
import bank.sim.cliente.domain.services.GeneratoreIdCliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ClienteUseCase {
    
    @Inject
    private GeneratoreIdCliente generatoreIdCliente;
    @Inject
    private GeneratoreCodiceCliente generatoreCodiceCliente;
    @Inject
    private IAMService iamService;

    @Inject
    private ClienteOutputPort ccOutputPort;

    public void creaCliente(CreaClienteCmd cmd) {
        log.info("Comando [creaCliente] in esecuzione...");
        Cliente cliente = Cliente.crea(generatoreIdCliente, generatoreCodiceCliente, cmd.getDatiAnagrafici() , iamService);
        ccOutputPort.salva(cliente);
        log.info("Comando [creaCliente] terminato...");
    }

    public void richiediAperturaConto(RichiediAperturaContoCmd cmd) {
        log.info("Comando [richiediAperturaConto] in esecuzione...");
        Cliente cliente = ccOutputPort.recuperaDaId(cmd.getIdCliente());

        log.info("CLIENTE: {}", cliente);
        cliente.richiediAperturaConto();
        log.info("CLIENTE: {}", cliente);
        ccOutputPort.salva(cliente);
        log.info("Comando [richiediAperturaConto] terminato...");
    }
}