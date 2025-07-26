package bank.sim.cliente.adapter.output.kafka.integration_events;

import java.io.Serializable;

import lombok.Value;

@Value
public class IEContoCorrenteAssociato implements Serializable {
    
    private String idContoCorrente;
}
