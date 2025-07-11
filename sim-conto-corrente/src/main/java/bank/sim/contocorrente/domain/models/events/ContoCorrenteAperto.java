package bank.sim.contocorrente.domain.models.events;

import bank.sim.contocorrente.domain.models.vo.CoordinateBancarie;
import bank.sim.contocorrente.domain.models.vo.DataApertura;
import bank.sim.contocorrente.domain.models.vo.IdCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import bank.sim.contocorrente.domain.models.vo.SoglieBonifico;
import lombok.Value;

@Value(staticConstructor = "with")
public class ContoCorrenteAperto implements EventPayload {

    private IdContoCorrente idContoCorrente;
    private IdCliente idCliente;
    private CoordinateBancarie coordinateBancarie;
    private SoglieBonifico soglieBonifico;
    private DataApertura dataApertura;
    private double saldo;
    
    @Override
    public String eventType() {
        return "ContoCorrenteAperto";
    }
}
