package bank.sim.contocorrente.domain.models.aggregates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import bank.sim.contocorrente.domain.exceptions.AccessoNonAutorizzatoAlContoException;
import bank.sim.contocorrente.domain.exceptions.BusinessRuleException;
import bank.sim.contocorrente.domain.models.events.ClienteDissociato;
import bank.sim.contocorrente.domain.models.events.CointestazioneConfermata;
import bank.sim.contocorrente.domain.models.events.CointestazioneRifiutata;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteAperto;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteChiuso;
import bank.sim.contocorrente.domain.models.events.EventPayload;
import bank.sim.contocorrente.domain.models.events.RichiestaCointestazioneValidata;
import bank.sim.contocorrente.domain.models.events.SoglieBonificoImpostate;
import bank.sim.contocorrente.domain.models.vo.CodiceCliente;
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
    private Map<CodiceCliente, Boolean> clientiAssociati = new HashMap<>();

    public static ContoCorrente apri(GeneratoreId generatoreIdService, GeneratoreCoordinateBancarie generatoreCoordinateBancarie, DatiCliente datiCliente) {
        
        LocalDate oggi = LocalDate.now();
        Period eta = Period.between(datiCliente.dataNascita(), oggi);

        if(eta.getYears() < 18) {
            log.error("Errore durante l'apertura del conto: Il cliente con codice [{}] deve aver raggiunto la maggiore eta'...",datiCliente.codiceCliente());
            throw new BusinessRuleException("Apertura conto corrente fallita: il cliente non ha raggiunto la maggiore eta'");
        }
        
        IdContoCorrente idConto = new IdContoCorrente(generatoreIdService.genera());
        CoordinateBancarie coordinateBancarie = generatoreCoordinateBancarie.genera();
        SoglieBonifico soglieBonifico = new SoglieBonifico(5000, 1500);
        DataApertura dataApertura = new DataApertura(LocalDateTime.now(ZoneOffset.UTC));
        double saldo = 0;
        ContoCorrente cc = new ContoCorrente();
        cc.idContoCorrente = idConto;
        cc.events(new ContoCorrenteAperto(idConto, new CodiceCliente(datiCliente.codiceCliente()), coordinateBancarie, soglieBonifico, dataApertura, saldo));
        return cc;
    }

    public void chiudi(CodiceCliente codiceClienteRichiedente) {
        verificaAccessoCliente(codiceClienteRichiedente);
        verificaContoChiuso();
        dataChiusura = new DataChiusura(LocalDateTime.now(ZoneOffset.UTC));
        for(CodiceCliente codiceCliente: clientiAssociati.keySet()) {
            events(new ClienteDissociato(new CodiceCliente(codiceCliente.codice())));
        }
        events(new ContoCorrenteChiuso(dataChiusura));
    }

    private void verificaAccessoCliente(CodiceCliente codiceCliente) {
        if( !clientiAssociati.keySet().stream().anyMatch(c -> c.codice().equals(codiceCliente.codice()))){
            throw new AccessoNonAutorizzatoAlContoException(codiceCliente.codice());
        }
    }

    public void verificaContoChiuso() {
        if(dataChiusura != null) {
            throw new BusinessRuleException("Chiusura del conto fallita: risulta gia chiuso");
        }
    }

    public void validaRichiestaCointestazione(CodiceCliente codiceClienteRichiedente, CodiceCliente nuovoCodiceCliente) {
        verificaAccessoCliente(codiceClienteRichiedente);
        verificaContoChiuso();
        if(!clientiAssociati.containsKey(nuovoCodiceCliente)) {
            this.clientiAssociati.put(nuovoCodiceCliente, false);
        } else {
            if(clientiAssociati.get(nuovoCodiceCliente)) {
                throw new RuntimeException(String.format("il cliente [%s] risulta gia' intestario del conto [%s]",nuovoCodiceCliente.codice(), idContoCorrente.id()));
            } else {
                throw new RuntimeException(String.format("La richiesta di cointestazione del conto [%s] e' gia stata validata per il cliente [%s]",idContoCorrente.id(), nuovoCodiceCliente.codice()));
            }
        }
        events(new RichiestaCointestazioneValidata(nuovoCodiceCliente));
    }

    public void valutaCointestazione(CodiceCliente codiceClienteRichiedente, boolean conferma) {
        verificaContoChiuso();
        if(!clientiAssociati.containsKey(codiceClienteRichiedente)) {
            throw new RuntimeException(String.format("Nessuna richiesta di cointestazione del conto [%s] e' presente per il cliente [%s]",idContoCorrente.id(), codiceClienteRichiedente.codice()));
        } 
        if(Boolean.TRUE.equals(clientiAssociati.get(codiceClienteRichiedente)) ){
            throw new RuntimeException(String.format("il cliente [%s] risulta gia' intestario del conto [%s]", codiceClienteRichiedente.codice(), idContoCorrente.id()));
        } else {
            if(conferma) {
                events(new CointestazioneConfermata(codiceClienteRichiedente));
            } else {
                events(new CointestazioneRifiutata(codiceClienteRichiedente));
            }
        }
    }

    public void impostaSoglieBonifico(CodiceCliente codiceClienteRichiedente, SoglieBonifico nuoveSoglieBonifico) {
        verificaAccessoCliente(codiceClienteRichiedente);
        verificaContoChiuso();
        if(nuoveSoglieBonifico.sogliaGiornaliera() > nuoveSoglieBonifico.sogliaMensile()) {
            log.error("Errore durante l'impostazione delle soglie bonifico per il conto [{}]. Soglie bonifico inconsistenti: SogliaGiornaliera [{}], SogliaMensile [{}]", idContoCorrente.id(), nuoveSoglieBonifico.sogliaGiornaliera(), nuoveSoglieBonifico.sogliaMensile());
            String message = String.format("Errore durante l'impostazione delle soglie bonifico. Soglie bonifico inconsistenti: SogliaGiornaliera [%s], SogliaMensile [%s]", nuoveSoglieBonifico.sogliaGiornaliera(), nuoveSoglieBonifico.sogliaMensile());
            throw new BusinessRuleException(message);
        }
        events(new SoglieBonificoImpostate(nuoveSoglieBonifico));
    }

    private void apply(ContoCorrenteAperto event) {
        this.clientiAssociati.put(event.codiceCliente(), true);
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
        clientiAssociati.compute(event.codiceCliente(), (key, val) -> false); 
    }

    private void apply(ClienteDissociato event) {
        this.clientiAssociati.remove(event.codiceCliente());
    }

    private void apply(CointestazioneConfermata event) {
        clientiAssociati.compute(event.codiceCliente(), (key, val) -> true); 
    }

    private void apply(CointestazioneRifiutata event) {
        clientiAssociati.remove(event.codiceCliente()); 
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
}


