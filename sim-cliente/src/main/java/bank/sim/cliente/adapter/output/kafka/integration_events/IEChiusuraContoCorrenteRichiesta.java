package bank.sim.cliente.adapter.output.kafka.integration_events;

import java.io.Serializable;

import lombok.Value;

@Value
public class IEChiusuraContoCorrenteRichiesta implements Serializable {
    
    private String idContoCorrente;
}
