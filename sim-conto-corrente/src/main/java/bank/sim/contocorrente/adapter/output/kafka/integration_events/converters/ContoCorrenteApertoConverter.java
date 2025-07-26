package bank.sim.contocorrente.adapter.output.kafka.integration_events.converters;

import bank.sim.contocorrente.adapter.output.kafka.integration_events.IEContoCorrenteAperto;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteAperto;
import bank.sim.contocorrente.domain.models.vo.CoordinateBancarie;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContoCorrenteApertoConverter implements IntegrationEventConverter<ContoCorrenteAperto, IEContoCorrenteAperto>, IntegrationEventConverterMarker{

    @Override
    public IEContoCorrenteAperto convert(ContoCorrenteAperto ev) {

        CoordinateBancarie coordinate = ev.coordinateBancarie();
        return new IEContoCorrenteAperto(ev.idContoCorrente().id(), ev.idCliente().id(), coordinate.numeroConto().numero(), coordinate.iban().codice(), coordinate.bic().codice(), coordinate.cab().codice(), coordinate.abi().codice(), ev.soglieBonifico().sogliaMensile(), ev.soglieBonifico().sogliaGiornaliera(), ev.dataApertura().dataOra(), ev.saldo());
    }

    @Override
    public Class<ContoCorrenteAperto> supportedDomainEvent() {
        return ContoCorrenteAperto.class;
    }
    
}
