package bank.sim.cliente.application.ports.input.commands;

import bank.sim.cliente.domain.models.vo.IdCliente;
import lombok.Value;

@Value
public class RichiediAperturaContoCmd {
    
    private IdCliente idCliente;
}
