package bank.sim.contocorrente.adapter.output.kafka.integration_events.converters;

import bank.sim.contocorrente.adapter.output.kafka.integration_events.IEContoCorrenteChiuso;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteChiuso;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContoCorrenteChiusoConverter implements IntegrationEventConverter<ContoCorrenteChiuso, IEContoCorrenteChiuso>, IntegrationEventConverterMarker{

    @Override
    public IEContoCorrenteChiuso convert(ContoCorrenteChiuso ev) {

        return new IEContoCorrenteChiuso(ev.dataChiusura().dataOra());
    }

    @Override
    public Class<ContoCorrenteChiuso> supportedDomainEvent() {
        return ContoCorrenteChiuso.class;
    }
    
}
