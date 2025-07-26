package bank.sim.cliente.infrastructure.persistence.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bank.sim.cliente.application.ports.output.ClienteRepositoryPort;
import bank.sim.cliente.domain.models.aggregates.Cliente;
import bank.sim.cliente.domain.models.events.EventPayload;
import bank.sim.cliente.domain.models.vo.IdCliente;
import bank.sim.cliente.infrastructure.persistence.entities.EventStoreEntity;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ClienteRepositoryImpl implements PanacheMongoRepository<EventStoreEntity>, ClienteRepositoryPort {

    @Inject
    private ObjectMapper mapper;

    @Override
    public void save(IdCliente idCliente, List<EventPayload> events) {
        long nextSequence = getNextSequence(idCliente.id());
        try {
            for (EventPayload ev : events) {
                EventStoreEntity entity;

                entity = new EventStoreEntity(idCliente.id(), ev.eventType(), mapper.writeValueAsString(ev),
                        nextSequence);

                entity.persist();
                nextSequence += 1;
            }
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Cliente findById(String aggregateId) {
        List<EventPayload> eventPayloads = EventStoreEntity.find("aggregateId = ?1", aggregateId)
                .stream()
                .sorted(Comparator.comparingLong(e -> ((EventStoreEntity) e).getSequence()))
                .map(e -> deserializeEvent((EventStoreEntity) e))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Cliente cc = new Cliente();
        for (EventPayload ev : eventPayloads) {
            cc.apply(ev);
        }
        return cc;
    }

    private EventPayload deserializeEvent(EventStoreEntity e) {
        try {
            Class<?> clazz = Class.forName("bank.sim.cliente.domain.models.events." + e.getEventType());
            return (EventPayload) mapper.readValue(e.getPayload(), clazz);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private long getNextSequence(String aggregateId) {
        EventStoreEntity last = EventStoreEntity.find("aggregateId = ?1", aggregateId)
                .stream()
                .map(e -> (EventStoreEntity) e)
                .max(Comparator.comparingLong(se -> se.getSequence()))
                .orElse(null);
        return last != null ? last.getSequence() + 1 : 1;
    }
}
