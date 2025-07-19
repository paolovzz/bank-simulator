package bank.sim.contocorrente.application.ports.input.commands;

import bank.sim.contocorrente.domain.models.vo.CodiceCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import bank.sim.contocorrente.domain.models.vo.SoglieBonifico;
import lombok.Value;

@Value
public class ImpostaSoglieBonificoCmd {
    
    private CodiceCliente codiceCliente;
    private IdContoCorrente idContoCorrente;
    private SoglieBonifico nuovSoglieBonifico;
}
