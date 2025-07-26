package bank.sim.contocorrente.adapter.output.kafka.integration_events.converters;

import bank.sim.contocorrente.domain.models.events.EventPayload;

public interface IntegrationEventConverterMarker {
    Class<? extends EventPayload> supportedDomainEvent();
}
