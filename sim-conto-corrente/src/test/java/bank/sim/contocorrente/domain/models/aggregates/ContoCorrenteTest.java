package bank.sim.contocorrente.domain.models.aggregates;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import bank.sim.contocorrente.domain.exceptions.BusinessRuleException;
import bank.sim.contocorrente.domain.models.events.ClienteDissociato;
import bank.sim.contocorrente.domain.models.events.CointestazioneConfermata;
import bank.sim.contocorrente.domain.models.events.CointestazioneRifiutata;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteAperto;
import bank.sim.contocorrente.domain.models.events.ContoCorrenteChiuso;
import bank.sim.contocorrente.domain.models.events.RichiestaCointestazioneValidata;
import bank.sim.contocorrente.domain.models.events.SoglieBonificoImpostate;
import bank.sim.contocorrente.domain.models.vo.Abi;
import bank.sim.contocorrente.domain.models.vo.Bic;
import bank.sim.contocorrente.domain.models.vo.Cab;
import bank.sim.contocorrente.domain.models.vo.IdCliente;
import bank.sim.contocorrente.domain.models.vo.CoordinateBancarie;
import bank.sim.contocorrente.domain.models.vo.DatiCliente;
import bank.sim.contocorrente.domain.models.vo.Iban;
import bank.sim.contocorrente.domain.models.vo.NumeroConto;
import bank.sim.contocorrente.domain.models.vo.SoglieBonifico;
import bank.sim.contocorrente.domain.ports.GeneratoreCoordinateBancarie;
import bank.sim.contocorrente.domain.services.GeneratoreId;

public class ContoCorrenteTest {

    private GeneratoreId generatoreIdService = Mockito.mock(GeneratoreId.class);
    private GeneratoreCoordinateBancarie generatoreCoordinateBancarie= Mockito.mock(GeneratoreCoordinateBancarie.class);

    private String idConto = null;
    private String idClienteString = "0001";
    private CoordinateBancarie coordinate = new CoordinateBancarie(new NumeroConto("832u48320"), new Iban("GB33BUKB20201555555555"), new Bic("BUKBGB22XXX"), new Cab("CHELTENHAM"), new Abi("1232asd"));
    private DatiCliente datiCliente = new DatiCliente(idClienteString, LocalDate.of(1991, 8, 31));
    
    private ContoCorrente buildContoCorrente() {
        
        idConto = "aaaa-bbbb-cccc-dddd";
        Mockito.when(generatoreIdService.genera()).thenReturn(idConto);
        coordinate = new CoordinateBancarie(new NumeroConto("832u48320"), new Iban("GB33BUKB20201555555555"), new Bic("BUKBGB22XXX"), new Cab("CHELTENHAM"), new Abi("1232asd"));
        Mockito.when(generatoreCoordinateBancarie.genera()).thenReturn(coordinate);
        ContoCorrente cc = ContoCorrente.apri(generatoreIdService, generatoreCoordinateBancarie, datiCliente);
        return cc;
    }
    
    @Test
    public void apri() {
        ContoCorrente cc = buildContoCorrente();
        var events = cc.popChanges();
        
        assertNotNull(cc.getDataApertura());
        assertEquals(idConto, cc.getIdContoCorrente().id());
        assertNull(cc.getDataChiusura());
        assertEquals(coordinate, cc.getCoordinateBancarie());
        assertEquals(0.0, cc.getSaldo());
        assertNotNull(cc.getSoglieBonifico());
        assertTrue(cc.getClientiAssociati().containsKey(new IdCliente(idClienteString)));
        assertTrue(cc.getClientiAssociati().get(new IdCliente(idClienteString)));

        assertEquals(1, events.size());
        ContoCorrenteAperto ev = (ContoCorrenteAperto) events.get(0);
            assertAll("Controllo evento ContoCorrenteAperto",
            () -> assertEquals(idClienteString, ev.idCliente().id()),
            () -> assertEquals(coordinate, ev.coordinateBancarie()),
            () -> assertNotNull( ev.dataApertura()),
            () -> assertEquals(idConto, ev.idContoCorrente().id()),
            () -> assertEquals(0, ev.saldo()),
            () -> assertNotNull(ev.soglieBonifico())
        );
    }

    @Test
    public void apri_ko() {
        DatiCliente datiCliente = new DatiCliente(idClienteString, LocalDate.of(2020, 8, 31));
        BusinessRuleException ex = assertThrows(BusinessRuleException.class, ()->ContoCorrente.apri(generatoreIdService, generatoreCoordinateBancarie, datiCliente));
        assertEquals("Apertura conto corrente fallita: il cliente non ha raggiunto la maggiore eta'", ex.getMessage());
    }
    
    @Test
    public void chiudi_ok() {
        ContoCorrente cc = buildContoCorrente();
        cc.popChanges();
        cc.chiudi(new IdCliente(idClienteString));
        var events = cc.popChanges();
        assertEquals(2, events.size());
        ClienteDissociato ev1 = (ClienteDissociato) events.get(0);
        assertAll("Controllo evento ClienteDissociato",
            () -> assertEquals(idClienteString, ev1.idCliente().id())
        );
        ContoCorrenteChiuso ev2 = (ContoCorrenteChiuso) events.get(1);
        assertAll("Controllo evento ContoCorrenteChiuso",
            () -> assertNotNull( ev2.dataChiusura()),
            () -> assertEquals( cc.getDataChiusura(), ev2.dataChiusura())
        );
    }

    @Test
    public void chiudi_ko_accesso_non_autorizzato() {
        ContoCorrente cc = buildContoCorrente();
        cc.popChanges();
        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () -> cc.chiudi(new IdCliente("0002")));
        assertEquals("Accesso al conto non autorizzato per il cliente [0002]", ex.getMessage());
    }

    @Test
    public void validaRichiestaCointestazione_ok() {
        ContoCorrente cc = buildContoCorrente();
        cc.popChanges();
        cc.validaRichiestaCointestazione(new IdCliente(idClienteString), new IdCliente("0002"));
        var events = cc.popChanges();
        assertEquals(1, events.size());
        RichiestaCointestazioneValidata ev1 = (RichiestaCointestazioneValidata) events.get(0);
        assertAll("Controllo evento RichiestaCointestazioneValidata",
            () -> assertEquals("0002", ev1.idCliente().id())
        );
    }

    @Test
    public void validaRichiestaCointestazione_ko_richiesta_convalida_gia_effettuata() {
        ContoCorrente cc = buildContoCorrente();
        cc.popChanges();
        cc.validaRichiestaCointestazione(new IdCliente(idClienteString), new IdCliente("0002"));
        cc.popChanges();
        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () ->cc.validaRichiestaCointestazione(new IdCliente(idClienteString), new IdCliente("0002")));
        assertEquals(String.format("La richiesta di cointestazione del conto [%s] e' gia stata validata e in attesa di conferma da parte del cliente [%s]", idConto, "0002"), ex.getMessage());
    }

    @Test
    public void validaRichiestaCointestazione_ko_cliente_gia_intestatario() {
        ContoCorrente cc = buildContoCorrente();
        cc.popChanges();
        cc.validaRichiestaCointestazione(new IdCliente(idClienteString), new IdCliente("0002"));
        cc.popChanges();
        cc.valutaCointestazione(new IdCliente("0002"), true);
        cc.popChanges();
        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () ->cc.validaRichiestaCointestazione(new IdCliente(idClienteString), new IdCliente("0002")));
        assertEquals(String.format("il cliente [%s] risulta gia' intestario del conto [%s]", "0002", idConto), ex.getMessage());
    }

    @Test
    public void valutaCointestazione_ok_cointestazione_confermata() {
        ContoCorrente cc = buildContoCorrente();
        cc.popChanges();
        cc.validaRichiestaCointestazione(new IdCliente(idClienteString), new IdCliente("0002"));
        cc.popChanges();
        cc.valutaCointestazione(new IdCliente("0002"), true);
        var events = cc.popChanges();
        assertEquals(1, events.size());
        CointestazioneConfermata ev1 = (CointestazioneConfermata) events.get(0);
        assertAll("Controllo evento CointestazioneConfermata",
            () -> assertEquals("0002", ev1.idCliente().id())
        );
    }

    @Test
    public void valutaCointestazione_ok_cointestazione_rifiutata() {
        ContoCorrente cc = buildContoCorrente();
        cc.popChanges();
        cc.validaRichiestaCointestazione(new IdCliente(idClienteString), new IdCliente("0002"));
        cc.popChanges();
        cc.valutaCointestazione(new IdCliente("0002"), false);
        var events = cc.popChanges();
        assertEquals(1, events.size());
        CointestazioneRifiutata ev1 = (CointestazioneRifiutata) events.get(0);
        assertAll("Controllo evento CointestazioneRifiutata",
            () -> assertEquals("0002", ev1.idCliente().id())
        );
    }

    @Test
    public void valutaCointestazione_ko_nessuna_richiesta_cointestazione() {
        ContoCorrente cc = buildContoCorrente();
        cc.popChanges();
        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () ->cc.valutaCointestazione(new IdCliente("0002"), false));
        assertEquals(String.format("Nessuna richiesta di cointestazione del conto [%s] e' presente per il cliente [%s]",idConto, "0002"), ex.getMessage());
    }

    @Test
    public void valutaCointestazione_ko_cointestazione_gia_accettata() {
        ContoCorrente cc = buildContoCorrente();
        cc.popChanges();
        cc.validaRichiestaCointestazione(new IdCliente(idClienteString), new IdCliente("0002"));
        cc.popChanges();
        cc.valutaCointestazione(new IdCliente("0002"), true);
        cc.popChanges();
        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () ->cc.valutaCointestazione(new IdCliente("0002"), true));
        assertEquals(String.format("il cliente [%s] risulta gia' intestario del conto [%s]", "0002", idConto), ex.getMessage());
    }

    @Test
    public void impostaSoglieBonifico_ok() {
        ContoCorrente cc = buildContoCorrente();
        cc.popChanges();
        cc.impostaSoglieBonifico(new IdCliente(idClienteString), new SoglieBonifico(10000, 5000));
        var events = cc.popChanges();
        assertEquals(1, events.size());
        SoglieBonificoImpostate ev1 = (SoglieBonificoImpostate) events.get(0);
        assertAll("Controllo evento SoglieBonificoImpostate",
            () -> assertEquals(new SoglieBonifico(10000, 5000), ev1.soglieBonifico())
        );
    }
}
