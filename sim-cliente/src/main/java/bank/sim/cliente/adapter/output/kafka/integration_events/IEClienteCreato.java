package bank.sim.cliente.adapter.output.kafka.integration_events;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Value;

@Value
public class IEClienteCreato implements Serializable {
    private String idCliente;
    private String codiceCliente;
    private String nomeCliente;
    private String cognomeCliente;
    private LocalDate dataNascita;
    private String codiceFiscale;
    private String email;
    private String telefono;
    private String residenza;
    private String iamId;
}
