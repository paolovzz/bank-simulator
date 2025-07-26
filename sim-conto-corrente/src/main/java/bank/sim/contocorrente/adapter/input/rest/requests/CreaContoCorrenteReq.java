package bank.sim.contocorrente.adapter.input.rest.requests;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreaContoCorrenteReq {
    
    private String idCliente;
    private LocalDate dataNascita;
}
