package bank.sim.contocorrente.adapter.output.kafka.integration_events.converters;

import bank.sim.contocorrente.adapter.output.kafka.integration_events.IECointestazioneRifiutata;
import bank.sim.contocorrente.domain.models.events.CointestazioneRifiutata;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CointestazioneRifiutataConverter implements IntegrationEventConverter<CointestazioneRifiutata, IECointestazioneRifiutata>, IntegrationEventConverterMarker{

    @Override
    public IECointestazioneRifiutata convert(CointestazioneRifiutata ev) {

        return new IECointestazioneRifiutata(ev.idCliente().id());
    }

    @Override
    public Class<CointestazioneRifiutata> supportedDomainEvent() {
        return CointestazioneRifiutata.class;
    }
    
}
