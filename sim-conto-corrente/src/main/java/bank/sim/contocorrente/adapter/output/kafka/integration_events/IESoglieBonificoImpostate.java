package bank.sim.contocorrente.adapter.output.kafka.integration_events;

import java.io.Serializable;

import lombok.Value;

@Value
public class IESoglieBonificoImpostate implements Serializable {
    private int sogliaMensile;
    private int sogliaGiornaliera;
}
