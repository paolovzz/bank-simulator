package bank.sim.contocorrente.application.ports.input.commands;

import bank.sim.contocorrente.domain.models.vo.IdCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import bank.sim.contocorrente.domain.models.vo.SoglieBonifico;
import lombok.Value;

@Value
public class ImpostaSoglieBonificoCmd {
    
    private IdCliente idCliente;
    private IdContoCorrente idContoCorrente;
    private SoglieBonifico nuovSoglieBonifico;
}
