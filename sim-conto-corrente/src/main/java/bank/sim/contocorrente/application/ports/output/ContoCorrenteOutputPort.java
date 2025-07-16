package bank.sim.contocorrente.application.ports.output;

import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;

public interface ContoCorrenteOutputPort {
    
    public void salva(ContoCorrente cc);
    public ContoCorrente recuperaDaId(IdContoCorrente idContoCorrente);
}
