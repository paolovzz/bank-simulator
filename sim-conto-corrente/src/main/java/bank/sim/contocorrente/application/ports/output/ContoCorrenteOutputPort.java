package bank.sim.contocorrente.application.ports.output;

import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;
import bank.sim.contocorrente.domain.models.vo.DatiCliente;

public interface ContoCorrenteOutputPort {
    
    public void salva(DatiCliente codiceCliente, ContoCorrente cc);
}
