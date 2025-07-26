package bank.sim.contocorrente.domain.models.aggregates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import bank.sim.contocorrente.domain.exceptions.BusinessRuleException;
import bank.sim.contocorrente.domain.models.events.ClienteDissociato;
import bank.sim.contocorrente.domain.models.events.CointestazioneConfermata;
import bank.sim.contocorrente.domain.models.events.CointestazioneRifiutata;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteAperto;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteChiuso;
import bank.sim.contocorrente.domain.models.events.EventPayload;
import bank.sim.contocorrente.domain.models.events.RichiestaCointestazioneValidata;
import bank.sim.contocorrente.domain.models.events.SoglieBonificoImpostate;
import bank.sim.contocorrente.domain.models.vo.IdCliente;
import bank.sim.contocorrente.domain.models.vo.CoordinateBancarie;
import bank.sim.contocorrente.domain.models.vo.DataApertura;
import bank.sim.contocorrente.domain.models.vo.DataChiusura;
import bank.sim.contocorrente.domain.models.vo.DatiCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import bank.sim.contocorrente.domain.models.vo.SoglieBonifico;
import bank.sim.contocorrente.domain.ports.GeneratoreCoordinateBancarie;
import bank.sim.contocorrente.domain.services.GeneratoreId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContoCorrente extends AggregateRoot<ContoCorrente> implements Applier  {

    public static final String AGGREGATE_NAME = "CONTO_CORRENTE";
    private IdContoCorrente idContoCorrente;
    private CoordinateBancarie coordinateBancarie;
    private SoglieBonifico soglieBonifico;
    private DataApertura dataApertura;
    private double saldo;
    private DataChiusura dataChiusura;
    private Map<IdCliente, Boolean> clientiAssociati = new HashMap<>();

    public static ContoCorrente apri(GeneratoreId generatoreIdService, GeneratoreCoordinateBancarie generatoreCoordinateBancarie, DatiCliente datiCliente) {
        
        LocalDate oggi = LocalDate.now();
        Period eta = Period.between(datiCliente.dataNascita(), oggi);

        if(eta.getYears() < 18) {
            log.error("Errore durante l'apertura del conto: Il cliente con codice [{}] deve aver raggiunto la maggiore eta'...",datiCliente.idCliente());
            throw new BusinessRuleException("Apertura conto corrente fallita: il cliente non ha raggiunto la maggiore eta'");
        }
        
        IdContoCorrente idConto = new IdContoCorrente(generatoreIdService.genera());
        CoordinateBancarie coordinateBancarie = generatoreCoordinateBancarie.genera();
        SoglieBonifico soglieBonifico = new SoglieBonifico(5000, 1500);
        DataApertura dataApertura = new DataApertura(LocalDateTime.now(ZoneOffset.UTC));
        double saldo = 0;
        ContoCorrente cc = new ContoCorrente();
        cc.idContoCorrente = idConto;
        cc.events(new ContoCorrenteAperto(idConto, new IdCliente(datiCliente.idCliente()), coordinateBancarie, soglieBonifico, dataApertura, saldo));
        return cc;
    }

    public void chiudi(IdCliente idClienteRichiedente) {
        verificaAccessoCliente(idClienteRichiedente);
        dataChiusura = new DataChiusura(LocalDateTime.now(ZoneOffset.UTC));
        for(IdCliente idCliente: clientiAssociati.keySet()) {
            events(new ClienteDissociato(new IdCliente(idCliente.id())));
        }
        events(new ContoCorrenteChiuso(dataChiusura));
    }

    public void validaRichiestaCointestazione(IdCliente idClienteRichiedente, IdCliente nuovoIdCliente) {
        verificaAccessoCliente(idClienteRichiedente);
        verificaContoChiuso();
        if(!clientiAssociati.containsKey(nuovoIdCliente)) {
            this.clientiAssociati.put(nuovoIdCliente, false);
            events(new RichiestaCointestazioneValidata(nuovoIdCliente));
        } else {
            if(clientiAssociati.get(nuovoIdCliente)) {
                throw new BusinessRuleException(String.format("il cliente [%s] risulta gia' intestario del conto [%s]",nuovoIdCliente.id(), idContoCorrente.id()));
            } else {
                throw new BusinessRuleException(String.format("La richiesta di cointestazione del conto [%s] e' gia stata validata e in attesa di conferma da parte del cliente [%s]",idContoCorrente.id(), nuovoIdCliente.id()));
            }
        }
    }

    public void valutaCointestazione(IdCliente idClienteRichiedente, boolean conferma) {
        verificaContoChiuso();
        if(!clientiAssociati.containsKey(idClienteRichiedente)) {
            throw new BusinessRuleException(String.format("Nessuna richiesta di cointestazione del conto [%s] e' presente per il cliente [%s]",idContoCorrente.id(), idClienteRichiedente.id()));
        } 
        if(Boolean.TRUE.equals(clientiAssociati.get(idClienteRichiedente)) ){
            throw new BusinessRuleException(String.format("il cliente [%s] risulta gia' intestario del conto [%s]", idClienteRichiedente.id(), idContoCorrente.id()));
        } else {
            if(conferma) {
                events(new CointestazioneConfermata(idClienteRichiedente));
            } else {
                events(new CointestazioneRifiutata(idClienteRichiedente));
            }
        }
    }

    public void impostaSoglieBonifico(IdCliente idClienteRichiedente, SoglieBonifico nuoveSoglieBonifico) {
        verificaAccessoCliente(idClienteRichiedente);
        verificaContoChiuso();
        events(new SoglieBonificoImpostate(nuoveSoglieBonifico));
    }

    private void apply(ContoCorrenteAperto event) {
        this.clientiAssociati.put(event.idCliente(), true);
        this.coordinateBancarie = event.coordinateBancarie();
        this.dataApertura = event.dataApertura();
        this.idContoCorrente = event.idContoCorrente();
        this.saldo = event.saldo();
        this.soglieBonifico = event.soglieBonifico();
    }

    private void apply(ContoCorrenteChiuso event) {
        this.dataChiusura = event.dataChiusura();
    }
    
    private void apply(RichiestaCointestazioneValidata event) {
        clientiAssociati.compute(event.idCliente(), (key, val) -> false); 
    }

    private void apply(ClienteDissociato event) {
        this.clientiAssociati.remove(event.idCliente());
    }

    private void apply(CointestazioneConfermata event) {
        clientiAssociati.compute(event.idCliente(), (key, val) -> true); 
    }

    private void apply(CointestazioneRifiutata event) {
        clientiAssociati.remove(event.idCliente()); 
    }

    private void apply(SoglieBonificoImpostate event) {
        soglieBonifico = event.soglieBonifico();
    }

    @Override
    public void apply(EventPayload event) {
        switch (event) {
            case ContoCorrenteAperto ev -> apply((ContoCorrenteAperto) ev);
            case ContoCorrenteChiuso ev -> apply((ContoCorrenteChiuso) ev);
            case ClienteDissociato ev -> apply((ClienteDissociato) ev);
            case RichiestaCointestazioneValidata ev -> apply((RichiestaCointestazioneValidata) ev);
            case CointestazioneConfermata ev -> apply((CointestazioneConfermata) ev);
            case CointestazioneRifiutata ev -> apply((CointestazioneRifiutata) ev);
            case SoglieBonificoImpostate ev -> apply((SoglieBonificoImpostate) ev);
            default -> throw new IllegalArgumentException("Evento non supportato");
        }
    }

     private void verificaAccessoCliente(IdCliente idCliente) {
        if( !clientiAssociati.keySet().stream().anyMatch(c -> c.id().equals(idCliente.id()))){
            throw new BusinessRuleException(String.format("Accesso al conto non autorizzato per il cliente [%s]", idCliente.id()));
        }
    }

    public void verificaContoChiuso() {
        if(dataChiusura != null) {
            throw new BusinessRuleException("Chiusura del conto fallita: risulta gia chiuso");
        }
    }
}


