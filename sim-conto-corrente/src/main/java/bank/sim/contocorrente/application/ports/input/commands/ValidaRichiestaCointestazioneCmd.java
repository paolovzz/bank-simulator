package bank.sim.contocorrente.application.ports.input.commands;

import bank.sim.contocorrente.domain.models.vo.IdCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import lombok.Value;

@Value
public class ValidaRichiestaCointestazioneCmd {
    
    private IdCliente idClienteRichiedente;
    private IdCliente nuovoIdCliente;
    private IdContoCorrente idContoCorrente;
}
