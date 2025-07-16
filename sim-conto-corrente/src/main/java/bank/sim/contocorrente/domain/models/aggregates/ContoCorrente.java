package bank.sim.contocorrente.domain.models.aggregates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import bank.sim.contocorrente.domain.exceptions.AccessoNonAutorizzatoAlContoException;
import bank.sim.contocorrente.domain.exceptions.BusinessRuleException;
import bank.sim.contocorrente.domain.models.events.AperturaContoCorrenteFallita;
import bank.sim.contocorrente.domain.models.events.ClienteDissociato;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteAperto;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteChiuso;
import bank.sim.contocorrente.domain.models.events.EventPayload;
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
public class ContoCorrente extends AggregateRoot {

    public static final String AGGREGATE_NAME = "CONTO_CORRENTE";
    private IdContoCorrente idContoCorrente;
    private CoordinateBancarie coordinateBancarie;
    private SoglieBonifico soglieBonifico;
    private DataApertura dataApertura;
    private double saldo;
    private DataChiusura dataChiusura;
    private Set<DatiCliente> clientiAssociati = new HashSet<>();

    public static ContoCorrente apri(GeneratoreId generatoreIdService, GeneratoreCoordinateBancarie generatoreCoordinateBancarie, DatiCliente datiCliente) {
        
        LocalDate oggi = LocalDate.now();
        Period eta = Period.between(datiCliente.dataNascita(), oggi);

        if(eta.getYears() < 18) {
            String message = String.format("Errore durante l'apertura del conto: Il cliente con codice [%s] deve aver raggiunto la maggiore eta'...",datiCliente.codiceCliente());
            throw new BusinessRuleException(message, AGGREGATE_NAME, null, AperturaContoCorrenteFallita.with(message, new CodiceCliente(datiCliente.codiceCliente())));
        }
        
        IdContoCorrente idConto = new IdContoCorrente(generatoreIdService.genera());
        CoordinateBancarie coordinateBancarie = generatoreCoordinateBancarie.genera();
        SoglieBonifico soglieBonifico = new SoglieBonifico(5000, 1500);
        DataApertura dataApertura = new DataApertura(LocalDateTime.now(ZoneOffset.UTC));
        double saldo = 0;
        ContoCorrente cc = new ContoCorrente();
        cc.idContoCorrente = idConto;
        cc.events(new ContoCorrenteAperto(idConto, datiCliente, coordinateBancarie, soglieBonifico, dataApertura, saldo));
        return cc;
    }

    public void chiudi(CodiceCliente codiceClienteRichiedente) {
        verificaAccessoCliente(codiceClienteRichiedente);
        dataChiusura = new DataChiusura(LocalDateTime.now(ZoneOffset.UTC));
        for(DatiCliente datiCliente: clientiAssociati) {
            events(new ClienteDissociato(new CodiceCliente(datiCliente.codiceCliente())));
        }
        events(new ContoCorrenteChiuso(dataChiusura));
    }

    private void verificaAccessoCliente(CodiceCliente codiceCliente) {
        if( !clientiAssociati.stream().anyMatch(c -> c.codiceCliente().equals(codiceCliente.codice()))){
            throw new AccessoNonAutorizzatoAlContoException(codiceCliente.codice());
        }
    }

    private void apply(ContoCorrenteAperto event) {
        this.clientiAssociati.add(event.datiCliente());
        this.coordinateBancarie = event.coordinateBancarie();
        this.dataApertura = event.dataApertura();
        this.idContoCorrente = event.idContoCorrente();
        this.saldo = event.saldo();
        this.soglieBonifico = event.soglieBonifico();
    }

    private void apply(ContoCorrenteChiuso event) {
        this.dataChiusura = event.dataChiusura();
    }

    private void apply(ClienteDissociato event) {
        this.clientiAssociati.removeIf(c -> c.codiceCliente().equals(event.codiceCliente().codice()));
    }

    public void apply(EventPayload event) {
        switch (event) {
            case ContoCorrenteAperto c -> apply((ContoCorrenteAperto)c);
            case ContoCorrenteChiuso v -> apply((ContoCorrenteChiuso)v);
            case ClienteDissociato p -> apply((ClienteDissociato)p);
            default -> throw new IllegalArgumentException("Evento non supportato");
        }
    }
}


