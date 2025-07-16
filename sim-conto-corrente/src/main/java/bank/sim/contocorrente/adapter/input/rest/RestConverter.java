package bank.sim.contocorrente.adapter.input.rest;

import bank.sim.contocorrente.adapter.input.rest.requests.CreaContoCorrenteReq;
import bank.sim.contocorrente.application.ports.input.commands.CreaContoCorrenteCmd;
import bank.sim.contocorrente.domain.models.vo.DatiCliente;

public class RestConverter {
    
    public static CreaContoCorrenteCmd toCreaContoCorrenteCmd(CreaContoCorrenteReq req) {
        return new CreaContoCorrenteCmd(new DatiCliente(req.getCodiceCliente(), req.getDataNascita()));
    }
}
