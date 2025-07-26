package bank.sim.contocorrente.adapter.output.kafka.integration_events.converters;

import bank.sim.contocorrente.adapter.output.kafka.integration_events.IECointestazioneConfermata;
import bank.sim.contocorrente.domain.models.events.CointestazioneConfermata;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CointestazioneConfermataConverter implements IntegrationEventConverter<CointestazioneConfermata, IECointestazioneConfermata>, IntegrationEventConverterMarker{

    @Override
    public IECointestazioneConfermata convert(CointestazioneConfermata ev) {

        return new IECointestazioneConfermata(ev.idCliente().id());
    }

    @Override
    public Class<CointestazioneConfermata> supportedDomainEvent() {
        return CointestazioneConfermata.class;
    }
    
}
