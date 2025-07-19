package bank.sim.contocorrente.domain.models.events;

import bank.sim.contocorrente.domain.models.vo.CodiceCliente;
import bank.sim.contocorrente.domain.models.vo.CoordinateBancarie;
import bank.sim.contocorrente.domain.models.vo.DataApertura;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import bank.sim.contocorrente.domain.models.vo.SoglieBonifico;

public record ContoCorrenteAperto(
        IdContoCorrente idContoCorrente,
        CodiceCliente codiceCliente,
        CoordinateBancarie coordinateBancarie,
        SoglieBonifico soglieBonifico,
        DataApertura dataApertura,
        double saldo) implements EventPayload {

    @Override
    public String eventType() {
        return "ContoCorrenteAperto";
    }
}
