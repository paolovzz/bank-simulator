package bank.sim.cliente.domain.models.aggregates;

import java.util.HashMap;
import java.util.Map;

import bank.sim.cliente.domain.models.events.AperturaContoCorrenteRichiesta;
import bank.sim.cliente.domain.models.events.ClienteCreato;
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

    private void apply(ClienteCreato event) {
        this.idCliente = event.idCliente();
        this.codiceCliente = event.codiceCliente();
        this.datiAnagrafici = event.datiAnagrafici();
        this.iamId = event.iamId();
    }

    private void apply(AperturaContoCorrenteRichiesta event) {
        //do nothing
    }

    @Override
    public void apply(EventPayload event) {
         switch (event) {
            case ClienteCreato ev -> apply((ClienteCreato) ev);
            case AperturaContoCorrenteRichiesta ev -> apply((AperturaContoCorrenteRichiesta) ev);
            default -> throw new IllegalArgumentException("Evento non supportato");
        }
    }
}