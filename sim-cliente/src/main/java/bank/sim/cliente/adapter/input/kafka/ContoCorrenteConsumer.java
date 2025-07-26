package bank.sim.cliente.adapter.input.kafka;

import java.nio.charset.StandardCharsets;

// import java.util.concurrent.CompletionStage;

import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bank.sim.cliente.application.ClienteUseCase;
import bank.sim.cliente.application.ports.input.commands.AssociaContoCorrenteCmd;
import bank.sim.cliente.application.ports.input.commands.DissociaContoCorrenteCmd;
import bank.sim.cliente.domain.models.vo.IdCliente;
import bank.sim.cliente.domain.models.vo.IdContoCorrente;
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
    private ClienteUseCase app;

    private static final String EVENT_OWNER = "CONTO_CORRENTE";
    private static final String EVENT_CONTO_CORRENTE_APERTO = "ContoCorrenteAperto";
    private static final String EVENT_CLIENTE_DISSOCIATO = "ClienteDissociato";

    @Incoming("conto-corrente-notifications")
    public CompletionStage<Void> consume(Message<String> msg) {
        var metadata = msg.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String eventType = new String(metadata.getHeaders().lastHeader("eventType").value(), StandardCharsets.UTF_8);
        String aggregateName = new String(metadata.getHeaders().lastHeader("aggregateName").value(),
                StandardCharsets.UTF_8);
        String payload = msg.getPayload();
        log.info("INCOMING:\n- EventType => {}\n- AggregateName => {}", eventType, aggregateName);
        if (aggregateName.equals(EVENT_OWNER)) {
            JsonNode json = convertToJsonNode(payload);
            String idContoCorrente = (String) metadata.getKey();
            switch (eventType) {
                case EVENT_CONTO_CORRENTE_APERTO:{
                    IdCliente idCliente = new IdCliente(json.get("idCliente").asText());
                    app.associaContoCorrente(new AssociaContoCorrenteCmd(idCliente, new IdContoCorrente(idContoCorrente)));
                    break;
                }
                case EVENT_CLIENTE_DISSOCIATO:{
                    IdCliente idCliente = new IdCliente(json.get("idCliente").asText());
                    app.dissociaContoCorrente(new DissociaContoCorrenteCmd(idCliente, new IdContoCorrente(idContoCorrente)));
                    break;
                }
                default:
                    log.warn("Evento [{}] non gestito...", eventType);
                    break;
            }
        }
        return msg.ack();
    }

    private JsonNode convertToJsonNode(String payload) {
        try {
            return mapper.readTree(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Errore durante la conversione json del messaggio kafka", e);
        }
    }
}
