package bank.sim.contocorrente.application;

import bank.sim.contocorrente.application.ports.input.commands.ChiudiContoCorrenteCmd;
import bank.sim.contocorrente.application.ports.input.commands.CreaContoCorrenteCmd;
import bank.sim.contocorrente.application.ports.input.commands.ImpostaSoglieBonificoCmd;
import bank.sim.contocorrente.application.ports.input.commands.ValidaRichiestaCointestazioneCmd;
import bank.sim.contocorrente.application.ports.input.commands.ValutaCointestazioneCmd;
import bank.sim.contocorrente.application.ports.output.ContoCorrenteOutputPort;
import bank.sim.contocorrente.application.ports.output.ErrorEventsPublisherPort;
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
    @Inject
    private ErrorEventsPublisherPort errorEventsPublisherPort;

    public void creaContoCorrente(CreaContoCorrenteCmd cmd) {
        log.info("Comando [creaContoCorrente] in esecuzione...");
        ContoCorrente cc = ContoCorrente.apri(generatoreIdService, generatoreCoordinateBancarie, cmd.getDatiCliente());
        ccOutputPort.salva(cc);
        log.info("Comando [creaContoCorrente] terminato...");
    }

    public void chiudiContoCorrente(ChiudiContoCorrenteCmd cmd) {
        log.info("Comando [chiudiContoCorrente] in esecuzione...");
        ContoCorrente cc = ccOutputPort.recuperaDaId(cmd.getIdContoCorrente());
        cc.chiudi(cmd.getIdCliente());
        ccOutputPort.salva(cc);
        log.info("Comando [chiudiContoCorrente] terminato...");
    }

    public void validaRichiestaCointestazione(ValidaRichiestaCointestazioneCmd cmd) {
        log.info("Comando [validaRichiestaCointestazione] in esecuzione...");
        ContoCorrente cc = ccOutputPort.recuperaDaId(cmd.getIdContoCorrente());
        cc.validaRichiestaCointestazione(cmd.getIdClienteRichiedente(), cmd.getNuovoIdCliente());
        ccOutputPort.salva(cc);
        log.info("Comando [validaRichiestaCointestazione] terminato...");
    }

    public void valutaCointestazione(ValutaCointestazioneCmd cmd) {
        log.info("Comando [valutaCointestazione] in esecuzione...");
        ContoCorrente cc = ccOutputPort.recuperaDaId(cmd.getIdContoCorrente());
        cc.valutaCointestazione(cmd.getIdCliente(), cmd.isConferma());
        ccOutputPort.salva(cc);
        log.info("Comando [valutaCointestazione] terminato...");
    }

    public void impostaSoglieBonifico(ImpostaSoglieBonificoCmd cmd) {
        log.info("Comando [impostaSoglieBonifico] in esecuzione...");
        ContoCorrente cc = ccOutputPort.recuperaDaId(cmd.getIdContoCorrente());
        cc.impostaSoglieBonifico(cmd.getIdCliente(), cmd.getNuovSoglieBonifico());
        ccOutputPort.salva(cc);
        log.info("Comando [impostaSoglieBonifico] terminato...");
    }
}