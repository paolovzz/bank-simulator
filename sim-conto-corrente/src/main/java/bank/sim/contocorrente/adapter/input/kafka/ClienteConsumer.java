package bank.sim.contocorrente.adapter.input.kafka;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

// import java.util.concurrent.CompletionStage;

import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bank.sim.contocorrente.application.ContoCorrenteUseCase;
import bank.sim.contocorrente.application.ports.input.commands.ChiudiContoCorrenteCmd;
import bank.sim.contocorrente.application.ports.input.commands.CreaContoCorrenteCmd;
import bank.sim.contocorrente.application.ports.input.commands.ImpostaSoglieBonificoCmd;
import bank.sim.contocorrente.application.ports.input.commands.ValidaRichiestaCointestazioneCmd;
import bank.sim.contocorrente.application.ports.input.commands.ValutaCointestazioneCmd;
import bank.sim.contocorrente.domain.models.vo.IdCliente;
import bank.sim.contocorrente.domain.models.vo.DatiCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import bank.sim.contocorrente.domain.models.vo.SoglieBonifico;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ClienteConsumer {

    @Inject
    private ObjectMapper mapper;

    @Inject
    private ContoCorrenteUseCase app;

    private static final String EVENT_OWNER = "CLIENTE";
    private static final String EVENT_APERTURA_CONTO_CORRENTE_RICHIESTA = "AperturaContoCorrenteRichiesta";
    private static final String EVENT_CHIUSURA_CONTO_CORRENTE_RICHIESTA = "ChiusuraContoCorrenteRichiesta";
    private static final String EVENT_COINTESTAZIONE_CONTO_CORRENTE_RICHIESTA = "CointestazioneContoCorrenteRichiesta";
    private static final String EVENT_VALUTAZIONE_COINTESTAZIONE_AVVIATA = "ValutazioneCointestazioneAvviata";
    private static final String EVENT_AGGIORNAMENTO_SOGLIE_BONIFICO_RICHIESTO = "AggiornamentoSoglieBonificoRichiesto";

    @Incoming("cliente-notifications")
    public CompletionStage<Void> consume(Message<String> msg) {
        var metadata = msg.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        String eventType = new String(metadata.getHeaders().lastHeader("eventType").value(), StandardCharsets.UTF_8);
        String aggregateName = new String(metadata.getHeaders().lastHeader("aggregateName").value(),
                StandardCharsets.UTF_8);
        String payload = msg.getPayload();
        log.info("INCOMING:\n- EventType => {}\n- AggregateName => {}", eventType, aggregateName);
        if (aggregateName.equals(EVENT_OWNER)) {
            JsonNode json = convertToJsonNode(payload);
            String idClienteRichiedente = (String) metadata.getKey();
            switch (eventType) {
                
                case EVENT_APERTURA_CONTO_CORRENTE_RICHIESTA:{
                    String dataNascitaString = json.get("dataNascita").asText();
                    app.creaContoCorrente( new CreaContoCorrenteCmd(new DatiCliente(idClienteRichiedente, LocalDate.parse(dataNascitaString))));
                    break;
                }
                case EVENT_CHIUSURA_CONTO_CORRENTE_RICHIESTA:{
                    String idConto = json.get("idContoCorrente").asText();
                    app.chiudiContoCorrente( new ChiudiContoCorrenteCmd(new IdCliente(idClienteRichiedente), new IdContoCorrente(idConto)));
                    break;
                }
                case EVENT_COINTESTAZIONE_CONTO_CORRENTE_RICHIESTA:{
                    String idConto = json.get("idContoCorrente").asText();
                    String idClienteNuovoIntestatario = json.get("idClienteNuovoIntestatario").asText();
                    app.validaRichiestaCointestazione(new ValidaRichiestaCointestazioneCmd(new IdCliente(idClienteRichiedente), new IdCliente(idClienteNuovoIntestatario), new IdContoCorrente(idConto)));
                    break;
                }
                case EVENT_VALUTAZIONE_COINTESTAZIONE_AVVIATA:{
                    String idConto = json.get("idContoCorrente").asText();
                    String idClienteNuovoIntestatario = json.get("idClienteNuovoIntestatario").asText();
                    boolean conferma = json.get("conferma").asBoolean();
                    app.valutaCointestazione(new ValutaCointestazioneCmd(new IdCliente(idClienteNuovoIntestatario), new IdContoCorrente(idConto), conferma));
                    break;
                }
                case EVENT_AGGIORNAMENTO_SOGLIE_BONIFICO_RICHIESTO:{
                    String idConto = json.get("idContoCorrente").asText();
                    int sogliaGiornalieraBonifico = json.get("sogliaGiornalieraBonifico").asInt();
                    int sogliaMensileBonifico = json.get("sogliaMensileBonifico").asInt();
                    app.impostaSoglieBonifico(new ImpostaSoglieBonificoCmd(new IdCliente(idClienteRichiedente), new IdContoCorrente(idConto), new SoglieBonifico(sogliaMensileBonifico, sogliaGiornalieraBonifico)));
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
