package bank.sim.cliente.adapter.output.kafka.integration_events.converters;

import bank.sim.cliente.domain.models.events.EventPayload;

public interface IntegrationEventConverterMarker {
    Class<? extends EventPayload> supportedDomainEvent();
}
