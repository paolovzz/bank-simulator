package bank.sim.contocorrente.application.ports.output;

import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;

public interface EventsPublisherPort {
    void publish(ContoCorrente cc);
}
