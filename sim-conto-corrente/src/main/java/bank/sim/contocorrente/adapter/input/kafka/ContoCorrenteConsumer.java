package bank.sim.contocorrente.adapter.input.kafka;



// import java.util.concurrent.CompletionStage;

import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bank.sim.contocorrente.application.ContoCorrenteUseCase;
import bank.sim.contocorrente.application.ports.input.commands.CreaContoCorrenteCmd;
import bank.sim.contocorrente.domain.models.vo.IdCliente;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ContoCorrenteConsumer {

    @Inject
    private ObjectMapper mapper;

    @Inject
    private ContoCorrenteUseCase app;

    @Incoming("cliente-notifications")
    public CompletionStage<Void> consume(Message<String> msg) {
        // access record metadata
        var metadata = msg.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        // process the message payload.
        String idCliente = (String) metadata.getKey();
        app.creaContoCorrente(new CreaContoCorrenteCmd(IdCliente.with(idCliente)));
        // String payload = msg.getPayload();
        log.info("Chiave messaggio: {}", idCliente);

        // try {
            // JsonNode json = mapper.readTree(payload);
            // System.out.println("Field value: " + json.get("idCliente").asText());
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // Acknowledge the incoming message (commit the offset)
        return msg.ack();
    }

}
