package bank.sim.cliente.application.ports.input.commands;

import bank.sim.cliente.domain.models.vo.DatiAnagraficiCliente;
import lombok.Value;

@Value
public class CreaClienteCmd {
    
    private DatiAnagraficiCliente datiAnagrafici;
}
