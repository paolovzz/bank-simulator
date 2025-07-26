package bank.sim.contocorrente.adapter.output.kafka.integration_events.converters;

import bank.sim.contocorrente.adapter.output.kafka.integration_events.IESoglieBonificoImpostate;
import bank.sim.contocorrente.domain.models.events.SoglieBonificoImpostate;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SoglieBonificoImpostateConverter implements IntegrationEventConverter<SoglieBonificoImpostate, IESoglieBonificoImpostate>, IntegrationEventConverterMarker{

    @Override
    public IESoglieBonificoImpostate convert(SoglieBonificoImpostate ev) {

        return new IESoglieBonificoImpostate(ev.soglieBonifico().sogliaMensile(), ev.soglieBonifico().sogliaGiornaliera());
    }

    @Override
    public Class<SoglieBonificoImpostate> supportedDomainEvent() {
        return SoglieBonificoImpostate.class;
    }
    
}
