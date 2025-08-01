package bank.sim.contocorrente.adapter.output.kafka.integration_events;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Value;

@Value
public class IEContoCorrenteAperto implements Serializable {
    private String idContoCorrente;
    private String idCliente;
    private String numeroConto;
    private String iban;
    private String bic;
    private String cab;
    private String abi;
    private int sogliaMensile;
    private int sogliaGiornaliera;
    private LocalDateTime dataApertura;
    private double saldo;
}
