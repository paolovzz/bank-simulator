package bank.sim.cliente.adapter.output.kafka.integration_events.converters;

import bank.sim.cliente.adapter.output.kafka.integration_events.IEChiusuraContoCorrenteRichiesta;
import bank.sim.cliente.domain.models.events.ChiusuraContoCorrenteRichiesta;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChiusuraContoCorrenteRichiestaConverter implements IntegrationEventConverter<ChiusuraContoCorrenteRichiesta, IEChiusuraContoCorrenteRichiesta>, IntegrationEventConverterMarker{

    @Override
    public IEChiusuraContoCorrenteRichiesta convert(ChiusuraContoCorrenteRichiesta domainEvent) {
        return new IEChiusuraContoCorrenteRichiesta(domainEvent.idContoCorrente().id());
    }

     @Override
    public Class<ChiusuraContoCorrenteRichiesta> supportedDomainEvent() {
        return ChiusuraContoCorrenteRichiesta.class;
    }
}
