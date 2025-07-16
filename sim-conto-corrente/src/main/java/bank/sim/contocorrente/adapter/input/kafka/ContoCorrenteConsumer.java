package bank.sim.contocorrente.adapter.input.kafka;



import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

// import java.util.concurrent.CompletionStage;

import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bank.sim.contocorrente.application.ContoCorrenteUseCase;
import bank.sim.contocorrente.application.ports.input.commands.ChiudiContoCorrenteCmd;
import bank.sim.contocorrente.application.ports.input.commands.CreaContoCorrenteCmd;
import bank.sim.contocorrente.domain.models.vo.CodiceCliente;
import bank.sim.contocorrente.domain.models.vo.DatiCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
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
        var metadata = msg.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String eventType = new String(metadata.getHeaders().lastHeader("eventType").value(), StandardCharsets.UTF_8);
        String aggregateName = new String(metadata.getHeaders().lastHeader("aggregateName").value(), StandardCharsets.UTF_8);
        String payload = msg.getPayload();
        log.info("INCOMING: eventType => {}, aggregateName => {}", eventType, aggregateName);
        if(aggregateName.equals("CLIENTE")) {
            if(eventType.equals("AperturaContoCorrenteRichiesto")) {
                String dataNascitaString = null;
                try {
                    JsonNode json = mapper.readTree(payload);
                    dataNascitaString = json.get("dataNascita").asText();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String codiceCliente = (String) metadata.getKey();
                app.creaContoCorrente(new CreaContoCorrenteCmd(new DatiCliente(codiceCliente, LocalDate.parse(dataNascitaString))));
        
            } else if(eventType.equals("ChiusuraContoCorrenteRichiesta")) {
                String idConto = null;
                 try {
                    JsonNode json = mapper.readTree(payload);
                    idConto = json.get("idContoCorrente").asText();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String codiceCliente = (String) metadata.getKey();
                app.chiudiContoCorrente(new ChiudiContoCorrenteCmd(new CodiceCliente(codiceCliente), new IdContoCorrente(idConto)));
            } else {
                log.warn("Evento [{}] non gestito...", eventType);
            }
        }
        return msg.ack();
    }
}
