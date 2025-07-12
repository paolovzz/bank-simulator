package bank.sim.contocorrente.application.ports.input.commands;

import bank.sim.contocorrente.domain.models.vo.DatiCliente;
import lombok.Value;

@Value
public class CreaContoCorrenteCmd {
    
    private DatiCliente datiCliente;
}
