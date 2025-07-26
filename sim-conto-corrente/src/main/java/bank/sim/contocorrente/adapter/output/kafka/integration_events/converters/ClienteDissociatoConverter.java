package bank.sim.contocorrente.adapter.output.kafka.integration_events.converters;

import bank.sim.contocorrente.adapter.output.kafka.integration_events.IEClienteDissociato;
import bank.sim.contocorrente.domain.models.events.ClienteDissociato;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteDissociatoConverter implements IntegrationEventConverter<ClienteDissociato, IEClienteDissociato>, IntegrationEventConverterMarker{

    @Override
    public IEClienteDissociato convert(ClienteDissociato ev) {

        return new IEClienteDissociato(ev.idCliente().id());
    }

    @Override
    public Class<ClienteDissociato> supportedDomainEvent() {
        return ClienteDissociato.class;
    }
    
}
