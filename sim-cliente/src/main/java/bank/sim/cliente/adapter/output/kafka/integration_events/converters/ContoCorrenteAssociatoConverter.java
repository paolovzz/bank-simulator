package bank.sim.cliente.adapter.output.kafka.integration_events.converters;

import bank.sim.cliente.adapter.output.kafka.integration_events.IEContoCorrenteAssociato;
import bank.sim.cliente.domain.models.events.ContoCorrenteAssociato;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContoCorrenteAssociatoConverter implements IntegrationEventConverter<ContoCorrenteAssociato, IEContoCorrenteAssociato>, IntegrationEventConverterMarker{

    @Override
    public IEContoCorrenteAssociato convert(ContoCorrenteAssociato domainEvent) {
        return new IEContoCorrenteAssociato(domainEvent.idContoCorrente().id());
    }

     @Override
    public Class<ContoCorrenteAssociato> supportedDomainEvent() {
        return ContoCorrenteAssociato.class;
    }
}
