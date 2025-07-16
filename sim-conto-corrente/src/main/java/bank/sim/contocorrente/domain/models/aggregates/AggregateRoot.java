package bank.sim.contocorrente.domain.models.aggregates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bank.sim.contocorrente.domain.models.events.EventPayload;

public class AggregateRoot {

    private List<EventPayload> events = new ArrayList<>();

     public List<EventPayload> popChanges() {
        List<EventPayload> changes = List.copyOf(events);
        events.clear();
        return changes;
    }

    public void events(EventPayload... payloads) {
        this.events.addAll(Arrays.asList(payloads));
    }
}
