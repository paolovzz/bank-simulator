package bank.sim.cliente.application.ports.input.commands;

import bank.sim.cliente.domain.models.vo.IdCliente;
import bank.sim.cliente.domain.models.vo.IdContoCorrente;
import lombok.Value;

@Value
public class DissociaContoCorrenteCmd {
    
    private IdCliente idCliente;
    private IdContoCorrente idContoCorrente;
}
