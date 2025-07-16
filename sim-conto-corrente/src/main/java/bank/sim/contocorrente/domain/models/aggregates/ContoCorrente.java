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
import bank.sim.contocorrente.domain.models.events.ContoCorrenteAperto;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteChiuso;
import bank.sim.contocorrente.domain.models.events.EventApplicator;
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
public class ContoCorrente extends AggregateRoot implements EventApplicator{

    public static final String AGGREGATE_NAME = "CONTO_CORRENTE";
    private IdContoCorrente idContoCorrente;
    private CoordinateBancarie coordinateBancarie;
    private SoglieBonifico soglieBonifico;
    private DataApertura dataApertura;
    private double saldo;
    private DataChiusura dataChiusura;
    private Set<DatiCliente> clientiAssociati;

    public static ContoCorrente apri(GeneratoreId generatoreIdService, GeneratoreCoordinateBancarie generatoreCoordinateBancarie, DatiCliente datiCliente) {
        
        LocalDate oggi = LocalDate.now();
        Period eta = Period.between(datiCliente.getDataNascita(), oggi);

        if(eta.getYears() < 18) {
            String message = String.format("Errore durante l'apertura del conto: Il cliente con codice [%s] deve aver raggiunto la maggiore eta'...",datiCliente.getCodiceCliente());
            throw new BusinessRuleException(message, AGGREGATE_NAME, null, AperturaContoCorrenteFallita.with(message, CodiceCliente.with(datiCliente.getCodiceCliente())));
        }
        
        IdContoCorrente idConto = IdContoCorrente.with(generatoreIdService.genera());
        CoordinateBancarie coordinateBancarie = generatoreCoordinateBancarie.genera();
        SoglieBonifico soglieBonifico = SoglieBonifico.with(5000, 1500);
        DataApertura dataApertura = DataApertura.with(LocalDateTime.now(ZoneOffset.UTC));
        double saldo = 0;
        Set<DatiCliente> clienti = new HashSet<>();
        clienti.add(datiCliente);
        ContoCorrente cc = new ContoCorrente();
        cc.idContoCorrente = idConto;
        cc.events(ContoCorrenteAperto.with(idConto, datiCliente, coordinateBancarie, soglieBonifico, dataApertura, saldo));
        return cc;
    }

    public void chiudi(DatiCliente codiceClienteRichiedente) {
        verificaAccessoCliente(codiceClienteRichiedente);
        dataChiusura = DataChiusura.with(LocalDateTime.now(ZoneOffset.UTC));
        events(ContoCorrenteChiuso.with(dataChiusura));
    }

    private void verificaAccessoCliente(DatiCliente codiceCliente) {
        if( !clientiAssociati.contains(codiceCliente)) {
            throw new AccessoNonAutorizzatoAlContoException(codiceCliente.getCodiceCliente());
        }
    }

    @Override
    public void apply(ContoCorrenteAperto event) {
        this.clientiAssociati.add(event.getDatiCliente());
        this.coordinateBancarie = event.getCoordinateBancarie();
        this.dataApertura = event.getDataApertura();
        this.idContoCorrente = event.getIdContoCorrente();
        this.saldo = event.getSaldo();
        this.soglieBonifico = event.getSoglieBonifico();
    }
}


