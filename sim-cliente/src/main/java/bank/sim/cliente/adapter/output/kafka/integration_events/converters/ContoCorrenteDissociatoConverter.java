package bank.sim.cliente.adapter.output.kafka.integration_events.converters;

import bank.sim.cliente.adapter.output.kafka.integration_events.IEContoCorrenteDissociato;
import bank.sim.cliente.domain.models.events.ContoCorrenteDissociato;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContoCorrenteDissociatoConverter implements IntegrationEventConverter<ContoCorrenteDissociato, IEContoCorrenteDissociato>, IntegrationEventConverterMarker{

    @Override
    public IEContoCorrenteDissociato convert(ContoCorrenteDissociato domainEvent) {
        return new IEContoCorrenteDissociato(domainEvent.idContoCorrente().id());
    }

     @Override
    public Class<ContoCorrenteDissociato> supportedDomainEvent() {
        return ContoCorrenteDissociato.class;
    }
}
