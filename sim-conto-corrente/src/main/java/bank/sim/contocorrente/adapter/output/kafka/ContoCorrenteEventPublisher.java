package bank.sim.contocorrente.adapter.output.kafka;

import java.util.List;
import java.util.UUID;

import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bank.sim.contocorrente.adapter.output.kafka.integration_events.converters.IntegrationEventConverterFactory;
import bank.sim.contocorrente.application.ports.output.EventsPublisherPort;
import bank.sim.contocorrente.domain.models.events.EventPayload;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ContoCorrenteEventPublisher implements EventsPublisherPort {

    @Inject
    private ObjectMapper mapper;

    @Channel("conto-corrente-notifications")
    private Emitter<String> emitter;

    @Inject
    private IntegrationEventConverterFactory factory;

    @Override
    public void publish(String aggregateName, String aggregateId, List<EventPayload> events) {

        String key = aggregateId; 
        events.stream().forEachOrdered(ev -> {
            
            var converter = factory.getConverter(ev);
            Object integrationEvent = converter.convert(ev);
            Message<String> message = Message.of(toJsonString(integrationEvent))
            .addMetadata(OutgoingKafkaRecordMetadata.<String>builder()
                .withKey(key)
                .withHeaders(new RecordHeaders()
                    .add("eventType", ev.eventType().getBytes())
                    .add("aggregateName", aggregateName.getBytes())
                    .add("eventId", UUID.randomUUID().toString().getBytes()))
                .build());
            log.info("Evento inviato: {}", message);
            emitter.send(message);
        });
    }

    private String toJsonString(Object event) {
        try {
            return mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}