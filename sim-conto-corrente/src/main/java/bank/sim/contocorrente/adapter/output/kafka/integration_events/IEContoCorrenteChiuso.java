package bank.sim.contocorrente.adapter.output.kafka.integration_events;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Value;

@Value
public class IEContoCorrenteChiuso implements Serializable {

    private LocalDateTime dataChiusura;
}
