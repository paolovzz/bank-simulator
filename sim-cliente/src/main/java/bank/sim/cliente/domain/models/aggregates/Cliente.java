package bank.sim.cliente.domain.models.aggregates;

import java.util.HashMap;
import java.util.Map;

import bank.sim.cliente.domain.exceptions.BusinessRuleException;
import bank.sim.cliente.domain.models.events.AperturaContoCorrenteRichiesta;
import bank.sim.cliente.domain.models.events.ChiusuraContoCorrenteRichiesta;
import bank.sim.cliente.domain.models.events.ClienteCreato;
import bank.sim.cliente.domain.models.events.ContoCorrenteAssociato;
import bank.sim.cliente.domain.models.events.ContoCorrenteDissociato;
import bank.sim.cliente.domain.models.events.EventPayload;
import bank.sim.cliente.domain.models.vo.CodiceCliente;
import bank.sim.cliente.domain.models.vo.DatiAnagraficiCliente;
import bank.sim.cliente.domain.models.vo.IamId;
import bank.sim.cliente.domain.models.vo.IdCliente;
import bank.sim.cliente.domain.models.vo.IdContoCorrente;
import bank.sim.cliente.domain.ports.IAMService;
import bank.sim.cliente.domain.services.GeneratoreCodiceCliente;
import bank.sim.cliente.domain.services.GeneratoreIdCliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends AggregateRoot<Cliente> implements Applier  {

    public static final String AGGREGATE_NAME = "CLIENTE";
    private IdCliente idCliente;
    private CodiceCliente codiceCliente;
    private DatiAnagraficiCliente datiAnagrafici;
    private IamId iamId;
    private Map<IdContoCorrente, Boolean> contiAssociati = new HashMap<>();

    public static Cliente crea(GeneratoreIdCliente generatoreIdCliente, GeneratoreCodiceCliente generatoreCodiceCliente, DatiAnagraficiCliente datiAnagrafici, IAMService iamService) {

        IdCliente idCliente = generatoreIdCliente.genera();
        Cliente cliente = new Cliente();
        cliente.idCliente = idCliente;
        CodiceCliente codiceCliente = generatoreCodiceCliente.genera();
        IamId iamId = new IamId(iamService.registraUtente());
        cliente.events(new ClienteCreato(idCliente, codiceCliente, datiAnagrafici, iamId));
        return cliente;
    }

    public void richiediAperturaConto() {
        events(new AperturaContoCorrenteRichiesta(datiAnagrafici.dataNascita()));
    }

    public void richiediChiusuraConto(IdContoCorrente idContoCorrente) {
        events(new ChiusuraContoCorrenteRichiesta(idContoCorrente));
    }

    public void associaContoCorrente(IdContoCorrente idContoCorrente) {
        if(contiAssociati.containsKey(idContoCorrente) && contiAssociati.get(idContoCorrente)) {
            throw new BusinessRuleException(String.format("Conto corrente con id [%s] gia associato", idContoCorrente.id()));
        }
        events(new ContoCorrenteAssociato(idContoCorrente));
    }

    public void dissociaContoCorrente(IdContoCorrente idContoCorrente) {
        if(contiAssociati.containsKey(idContoCorrente) && contiAssociati.get(idContoCorrente)) {
            events(new ContoCorrenteDissociato(idContoCorrente));
        } else {
            throw new BusinessRuleException(String.format("Conto corrente con id [%s] non risulta attualmente associato", idContoCorrente.id()));
        }
        
    }

    private void apply(ClienteCreato event) {
        this.idCliente = event.idCliente();
        this.codiceCliente = event.codiceCliente();
        this.datiAnagrafici = event.datiAnagrafici();
        this.iamId = event.iamId();
    }

    private void apply(AperturaContoCorrenteRichiesta event) {
        //do nothing
    }

    private void apply(ChiusuraContoCorrenteRichiesta event) {
        //do nothing
    }

    private void apply(ContoCorrenteAssociato event) {
        this.contiAssociati.put(event.idContoCorrente(), true);
    }
    private void apply(ContoCorrenteDissociato event) {
        this.contiAssociati.remove(event.idContoCorrente());
    }


    @Override
    public void apply(EventPayload event) {
         switch (event) {
            case ClienteCreato ev -> apply((ClienteCreato) ev);
            case AperturaContoCorrenteRichiesta ev -> apply((AperturaContoCorrenteRichiesta) ev);
            case ChiusuraContoCorrenteRichiesta ev -> apply((ChiusuraContoCorrenteRichiesta) ev);
            case ContoCorrenteAssociato ev -> apply((ContoCorrenteAssociato) ev);
            case ContoCorrenteDissociato ev -> apply((ContoCorrenteDissociato) ev);
            default -> throw new IllegalArgumentException("Evento non supportato");
        }
    }
}