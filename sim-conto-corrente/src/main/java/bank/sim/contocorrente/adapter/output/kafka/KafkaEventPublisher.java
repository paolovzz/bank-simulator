package bank.sim.contocorrente.adapter.output.kafka;

import java.util.UUID;

import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bank.sim.contocorrente.application.ports.output.EventsPublisherPort;
import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;
import bank.sim.contocorrente.domain.models.events.EventPayload;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class KafkaEventPublisher implements EventsPublisherPort {

    @Inject
    private ObjectMapper mapper;

    @Channel("conto-corrente-notifications")
    Emitter<String> emitter;

    @Override
    public void publish(ContoCorrente cc) {

        String key = cc.getIdContoCorrente().getId(); 
        String aggregateName = ContoCorrente.AGGREGATE_NAME;
        cc.popChanges().stream().forEachOrdered(ev -> {
            Message<String> message = Message.of(toJsonString(ev))
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

    private String toJsonString(EventPayload event) {
        try {
            return mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}