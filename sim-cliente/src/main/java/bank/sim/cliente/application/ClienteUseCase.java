package bank.sim.cliente.application;

import bank.sim.cliente.application.exceptions.ClienteNonTrovatoException;
import bank.sim.cliente.application.ports.input.commands.AssociaContoCorrenteCmd;
import bank.sim.cliente.application.ports.input.commands.CreaClienteCmd;
import bank.sim.cliente.application.ports.input.commands.DissociaContoCorrenteCmd;
import bank.sim.cliente.application.ports.input.commands.RichiediAperturaContoCmd;
import bank.sim.cliente.application.ports.input.commands.RichiediChiusuraContoCmd;
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
        if(cliente == null) {
            throw new ClienteNonTrovatoException(cmd.getIdCliente().id());
        }
        log.info("CLIENTE: {}", cliente);
        cliente.richiediAperturaConto();
        log.info("CLIENTE: {}", cliente);
        ccOutputPort.salva(cliente);
        log.info("Comando [richiediAperturaConto] terminato...");
    }

    public void richiediChiusuraConto(RichiediChiusuraContoCmd cmd) {
        log.info("Comando [richiediChiusuraConto] in esecuzione...");
        Cliente cliente = ccOutputPort.recuperaDaId(cmd.getIdCliente());
        if(cliente == null) {
            throw new ClienteNonTrovatoException(cmd.getIdCliente().id());
        }
        cliente.richiediChiusuraConto(cmd.getIdContoCorrente());
        ccOutputPort.salva(cliente);
        log.info("Comando [richiediChiusuraConto] terminato...");
    }

    public void associaContoCorrente(AssociaContoCorrenteCmd cmd) {
        log.info("Comando [associaContoCorrente] in esecuzione...");
        Cliente cliente = ccOutputPort.recuperaDaId(cmd.getIdCliente());
        if(cliente == null) {
            throw new ClienteNonTrovatoException(cmd.getIdCliente().id());
        }
        cliente.associaContoCorrente(cmd.getIdContoCorrente());
        ccOutputPort.salva(cliente);
        log.info("Comando [associaContoCorrente] terminato...");
    }

    public void dissociaContoCorrente(DissociaContoCorrenteCmd cmd) {
        log.info("Comando [dissociaContoCorrente] in esecuzione...");
        Cliente cliente = ccOutputPort.recuperaDaId(cmd.getIdCliente());
        if(cliente == null) {
            throw new ClienteNonTrovatoException(cmd.getIdCliente().id());
        }
        cliente.dissociaContoCorrente(cmd.getIdContoCorrente());
        ccOutputPort.salva(cliente);
        log.info("Comando [dissociaContoCorrente] terminato...");
    }
}