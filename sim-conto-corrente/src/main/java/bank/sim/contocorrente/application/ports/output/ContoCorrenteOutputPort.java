package bank.sim.contocorrente.application.ports.output;

import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;
import bank.sim.contocorrente.domain.models.vo.IdCliente;

public interface ContoCorrenteOutputPort {
    
    public void salva(IdCliente idCliente, ContoCorrente cc);
}
