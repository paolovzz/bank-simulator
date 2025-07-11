package bank.sim.contocorrente.application;

import bank.sim.contocorrente.application.ports.input.commands.CreaContoCorrenteCmd;
import bank.sim.contocorrente.application.ports.output.ContoCorrenteOutputPort;
import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;
import bank.sim.contocorrente.domain.ports.GeneratoreCoordinateBancarie;
import bank.sim.contocorrente.domain.services.GeneratoreId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ContoCorrenteUseCase {
    
    @Inject
    private GeneratoreId generatoreIdService;
    
    @Inject
    private GeneratoreCoordinateBancarie generatoreCoordinateBancarie;

    @Inject
    private ContoCorrenteOutputPort ccOutputPort;

    public void creaContoCorrente(CreaContoCorrenteCmd cmd) {
        log.info("Comando [creaContoCorrente] in esecuzione...");
        ContoCorrente cc = ContoCorrente.apri(generatoreIdService, generatoreCoordinateBancarie, cmd.getIdCliente());
        ccOutputPort.salva(cmd.getIdCliente(), cc);
        log.info("Comando [creaContoCorrente] terminato...");
    }
}
