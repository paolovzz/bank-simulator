package bank.sim.contocorrente.domain.models.aggregates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import bank.sim.contocorrente.domain.exceptions.BusinessRuleException;
import bank.sim.contocorrente.domain.models.vo.Abi;
import bank.sim.contocorrente.domain.models.vo.Bic;
import bank.sim.contocorrente.domain.models.vo.Cab;
import bank.sim.contocorrente.domain.models.vo.CodiceCliente;
import bank.sim.contocorrente.domain.models.vo.CoordinateBancarie;
import bank.sim.contocorrente.domain.models.vo.DatiCliente;
import bank.sim.contocorrente.domain.models.vo.Iban;
import bank.sim.contocorrente.domain.models.vo.NumeroConto;
import bank.sim.contocorrente.domain.ports.GeneratoreCoordinateBancarie;
import bank.sim.contocorrente.domain.services.GeneratoreId;

public class ContoCorrenteTest {

    private GeneratoreId generatoreIdService = Mockito.mock(GeneratoreId.class);
    private GeneratoreCoordinateBancarie generatoreCoordinateBancarie= Mockito.mock(GeneratoreCoordinateBancarie.class);

    private ContoCorrente cc;
    
    @Test
    public void apri() {
        DatiCliente datiCliente = new DatiCliente("0001", LocalDate.of(1991, 8, 31));

        String idConto = "aaaa-bbbb-cccc-dddd";
        Mockito.when(generatoreIdService.genera()).thenReturn(idConto);

        CoordinateBancarie coordinate = new CoordinateBancarie(new NumeroConto("832u48320"), new Iban("GB33BUKB20201555555555"), new Bic("BUKBGB22XXX"), new Cab("CHELTENHAM"), new Abi("1232asd"));
        Mockito.when(generatoreCoordinateBancarie.genera()).thenReturn(coordinate);
        cc = ContoCorrente.apri(generatoreIdService, generatoreCoordinateBancarie, datiCliente);
        cc.popChanges();
        assertNotNull(cc.getDataApertura());
        assertEquals(idConto, cc.getIdContoCorrente().id());
        assertNull(cc.getDataChiusura());
        assertEquals(coordinate, cc.getCoordinateBancarie());
        assertEquals(0.0, cc.getSaldo());
        assertNotNull(cc.getSoglieBonifico());
        assertTrue(cc.getClientiAssociati().containsKey(new CodiceCliente("0001")));
        assertTrue(cc.getClientiAssociati().get(new CodiceCliente("0001")));
    }

    @Test
    public void apri_ko() {
        DatiCliente datiCliente = new DatiCliente("0001", LocalDate.of(2020, 8, 31));
        BusinessRuleException ex = assertThrows(BusinessRuleException.class, ()->ContoCorrente.apri(generatoreIdService, generatoreCoordinateBancarie, datiCliente));
    }
    
}
