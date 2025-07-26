package bank.sim.cliente.adapter.output.kafka.integration_events;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Value;

@Value
public class IEAperturaContoCorrenteRichiesta implements Serializable {
    
    private LocalDate dataNascita;
}
