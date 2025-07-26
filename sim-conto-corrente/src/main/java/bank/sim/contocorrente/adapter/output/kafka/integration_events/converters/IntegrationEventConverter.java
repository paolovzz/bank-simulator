package bank.sim.contocorrente.adapter.output.kafka.integration_events.converters;

import bank.sim.contocorrente.domain.models.events.EventPayload;

public interface IntegrationEventConverter<DE extends EventPayload, IE> {
    IE convert(DE domainEvent);
}