package bank.sim.cliente.adapter.output.kafka.integration_events.converters;

import bank.sim.cliente.adapter.output.kafka.integration_events.IEAperturaContoCorrenteRichiesta;
import bank.sim.cliente.domain.models.events.AperturaContoCorrenteRichiesta;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AperturaContoCorrenteRichiestaConverter implements IntegrationEventConverter<AperturaContoCorrenteRichiesta, IEAperturaContoCorrenteRichiesta>, IntegrationEventConverterMarker{

    @Override
    public IEAperturaContoCorrenteRichiesta convert(AperturaContoCorrenteRichiesta domainEvent) {
        return new IEAperturaContoCorrenteRichiesta(domainEvent.dataNascita().data());
    }

     @Override
    public Class<AperturaContoCorrenteRichiesta> supportedDomainEvent() {
        return AperturaContoCorrenteRichiesta.class;
    }
}
