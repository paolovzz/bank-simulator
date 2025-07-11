package bank.sim.contocorrente.infrastructure.persistence;

import java.util.stream.Collectors;

import bank.sim.contocorrente.domain.models.aggregates.ContoCorrente;
import bank.sim.contocorrente.domain.models.vo.CoordinateBancarie;
import bank.sim.contocorrente.domain.models.vo.DataApertura;
import bank.sim.contocorrente.domain.models.vo.DataChiusura;
import bank.sim.contocorrente.domain.models.vo.IdCliente;
import bank.sim.contocorrente.domain.models.vo.IdContoCorrente;
import bank.sim.contocorrente.domain.models.vo.SoglieBonifico;
import bank.sim.contocorrente.infrastructure.persistence.entities.ContoCorrenteEntity;

public class ContoCorrenteMapper {
    

    public static final ContoCorrenteEntity toEntity(ContoCorrente cc) {
        if (cc == null) {
            return null;
        }
        ContoCorrenteEntity entity = new ContoCorrenteEntity();
        CoordinateBancarie coordinateBancarie = cc.getCoordinateBancarie();
        entity.setAbi(coordinateBancarie.getAbi().getCodice());
        entity.setCab(coordinateBancarie.getCab().getCodice());
        entity.setDataApertura(cc.getDataApertura().getDataOra());
        entity.setDataChiusura(cc.getDataChiusura() != null ? cc.getDataChiusura().getDataOra() : null);
        entity.setIban(coordinateBancarie.getIban().getCodice());
        entity.setId(cc.getIdContoCorrente().getId());
        entity.setNumeroConto(coordinateBancarie.getNumeroConto().getNumero());
        entity.setSaldo(cc.getSaldo());
        entity.setBic(coordinateBancarie.getBic().getCodice());
        entity.setSogliaBonificoGiornaliera(cc.getSoglieBonifico().getSogliaGiornaliera());
        entity.setSogliaBonificoMensile(cc.getSoglieBonifico().getSogliaMensile());
        entity.setClientiAssociati(cc.getClientiAssociati().stream().map(idCliente -> idCliente.getId()).collect(Collectors.toSet()));
        return entity;
    }

    public static final ContoCorrente toAggregate(ContoCorrenteEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ContoCorrente(IdContoCorrente.with(entity.getId()), CoordinateBancarie.with(entity.getNumeroConto(), entity.getIban(), entity.getCab(), entity.getAbi()), SoglieBonifico.with(entity.getSogliaBonificoMensile(), entity.getSogliaBonificoGiornaliera()), DataApertura.with(entity.getDataApertura()), entity.getSaldo(), entity.getDataChiusura() != null ? DataChiusura.with(entity.getDataChiusura()) : null, entity.getClientiAssociati().stream().map(idCliente -> IdCliente.with(idCliente)).collect(Collectors.toSet()));
    }
}
