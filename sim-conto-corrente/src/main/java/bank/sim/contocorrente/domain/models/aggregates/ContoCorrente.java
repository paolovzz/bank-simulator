package bank.sim.contocorrente.domain.models.aggregates;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import bank.sim.contocorrente.domain.exceptions.AccessoNonAutorizzatoAlContoException;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteAperto;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteChiuso;
import bank.sim.contocorrente.domain.models.vo.CoordinateBancarie;
import bank.sim.contocorrente.domain.models.vo.DataApertura;
import bank.sim.contocorrente.domain.models.vo.DataChiusura;
import bank.sim.contocorrente.domain.models.vo.IdCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import bank.sim.contocorrente.domain.models.vo.SoglieBonifico;
import bank.sim.contocorrente.domain.ports.GeneratoreCoordinateBancarie;
import bank.sim.contocorrente.domain.services.GeneratoreId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
@AllArgsConstructor
public class ContoCorrente extends AggregateRoot{

    public static final String AGGREGATE_NAME = "CONTO_CORRENTE";
    private IdContoCorrente idContoCorrente;
    private CoordinateBancarie coordinateBancarie;
    private SoglieBonifico soglieBonifico;
    private DataApertura dataApertura;
    private double saldo;
    private DataChiusura dataChiusura;
    private Set<IdCliente> clientiAssociati;

    public static ContoCorrente apri(GeneratoreId generatoreIdService, GeneratoreCoordinateBancarie generatoreCoordinateBancarie, IdCliente idCliente) {
        IdContoCorrente idConto = IdContoCorrente.with(generatoreIdService.genera());
        CoordinateBancarie coordinateBancarie = generatoreCoordinateBancarie.genera();
        SoglieBonifico soglieBonifico = SoglieBonifico.with(5000, 1500);
        DataApertura dataApertura = DataApertura.with(LocalDateTime.now(ZoneOffset.UTC));
        double saldo = 0;
        Set<IdCliente> clienti = new HashSet<>();
        clienti.add(idCliente);
        ContoCorrente cc = new ContoCorrente(idConto, coordinateBancarie, soglieBonifico, dataApertura, saldo, null, clienti);
        cc.events(ContoCorrenteAperto.with(idConto, idCliente, coordinateBancarie, soglieBonifico, dataApertura, saldo));
        return cc;
    }

    public void chiudi(IdCliente idClienteRichiedente) {
        verificaAccessoCliente(idClienteRichiedente);
        dataChiusura = DataChiusura.with(LocalDateTime.now(ZoneOffset.UTC));
        events(ContoCorrenteChiuso.with(dataChiusura));
    }

    private void verificaAccessoCliente(IdCliente idCliente) {
        if( !clientiAssociati.contains(idCliente)) {
            throw new AccessoNonAutorizzatoAlContoException(idCliente.getId());
        }
    }
}


