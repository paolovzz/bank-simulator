package bank.sim.contocorrente.application.ports.input.commands;

import bank.sim.contocorrente.domain.models.vo.IdCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import lombok.Value;

@Value
public class RifiutaCointestazioneCmd {
    
    private IdCliente idCliente;
    private IdContoCorrente idContoCorrente;
}
