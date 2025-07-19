package bank.sim.contocorrente.application.ports.input.commands;

import bank.sim.contocorrente.domain.models.vo.CodiceCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import lombok.Value;

@Value
public class ValidaRichiestaCointestazioneCmd {
    
    private CodiceCliente codiceClienteRichiedente;
    private CodiceCliente nuovoCodiceCliente;
    private IdContoCorrente idContoCorrente;
}
