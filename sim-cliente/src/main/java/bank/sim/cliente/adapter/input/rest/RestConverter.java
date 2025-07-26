package bank.sim.cliente.adapter.input.rest;

import bank.sim.cliente.adapter.input.rest.requests.CreaClienteReq;
import bank.sim.cliente.application.ports.input.commands.CreaClienteCmd;
import bank.sim.cliente.application.ports.input.commands.RichiediAperturaContoCmd;
import bank.sim.cliente.domain.models.vo.CodiceFiscale;
import bank.sim.cliente.domain.models.vo.CognomeCliente;
import bank.sim.cliente.domain.models.vo.DataNascita;
import bank.sim.cliente.domain.models.vo.DatiAnagraficiCliente;
import bank.sim.cliente.domain.models.vo.Email;
import bank.sim.cliente.domain.models.vo.IdCliente;
import bank.sim.cliente.domain.models.vo.NomeCliente;
import bank.sim.cliente.domain.models.vo.Residenza;
import bank.sim.cliente.domain.models.vo.Telefono;

public class RestConverter {
    
    public static CreaClienteCmd toCreaClienteCmd(CreaClienteReq req) {
        return new CreaClienteCmd(new DatiAnagraficiCliente(new NomeCliente(req.getNome()), new CognomeCliente(req.getCognome()), new DataNascita(req.getDataNascita()), new CodiceFiscale(req.getCodiceFiscale()), new Email(req.getEmail()), new Telefono(req.getTelefono()), new Residenza(req.getResidenza())));
    }
    
    public static RichiediAperturaContoCmd toRichiediAperturaContoCmd(String idCliente) {
        return new RichiediAperturaContoCmd(new IdCliente(idCliente));
    }
}
