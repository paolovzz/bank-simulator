package bank.sim.cliente.adapter.output.kafka.integration_events.converters;

import bank.sim.cliente.domain.models.events.EventPayload;

public interface IntegrationEventConverter<DE extends EventPayload, IE> {
    IE convert(DE domainEvent);
}