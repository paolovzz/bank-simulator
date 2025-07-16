package bank.sim.contocorrente.infrastructure.persistence.entities;

import java.time.Instant;

import bank.sim.contocorrente.domain.models.events.EventPayload;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MongoEntity(collection="EventStore")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EventStoreEntity extends PanacheMongoEntityBase {

    private String aggregateId;
    private String eventType;
    private String payload;
    private long sequence;
    private Instant timestamp;


    public EventStoreEntity(String aggregateId, String eventType, String payload, long sequence) {
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.sequence = sequence;
        this.timestamp = Instant.now();
    }
    
}