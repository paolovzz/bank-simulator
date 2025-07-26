package bank.sim.contocorrente.adapter.output.kafka.integration_events.converters;

import bank.sim.contocorrente.adapter.output.kafka.integration_events.IERichiestaCointestazioneValidata;
import bank.sim.contocorrente.domain.models.events.RichiestaCointestazioneValidata;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RichiestaCointestazioneValidataConverter implements IntegrationEventConverter<RichiestaCointestazioneValidata, IERichiestaCointestazioneValidata>, IntegrationEventConverterMarker{

    @Override
    public IERichiestaCointestazioneValidata convert(RichiestaCointestazioneValidata ev) {

        return new IERichiestaCointestazioneValidata(ev.idCliente().id());
    }

    @Override
    public Class<RichiestaCointestazioneValidata> supportedDomainEvent() {
        return RichiestaCointestazioneValidata.class;
    }
    
}
