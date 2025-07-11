package bank.monitoraggio.contocorrente.adapter.input.kafka.consumer;



// import java.util.concurrent.CompletionStage;

import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ContoCorrenteConsumer {
    @Incoming("conto-corrente-notifications")
    public CompletionStage<Void> consume(Message<String> msg) {
        // access record metadata
        var metadata = msg.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        // process the message payload.
        String price = msg.getPayload();
        log.info("MESSAGGIO RICEVUTO: {}", price);
        // Acknowledge the incoming message (commit the offset)
        return msg.ack();
    }

}
