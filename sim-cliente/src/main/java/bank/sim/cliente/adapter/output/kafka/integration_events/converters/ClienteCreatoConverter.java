package bank.sim.cliente.adapter.output.kafka.integration_events.converters;

import bank.sim.cliente.adapter.output.kafka.integration_events.IEClienteCreato;
import bank.sim.cliente.domain.models.events.ClienteCreato;
import bank.sim.cliente.domain.models.vo.DatiAnagraficiCliente;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteCreatoConverter implements IntegrationEventConverter<ClienteCreato, IEClienteCreato>, IntegrationEventConverterMarker{

    @Override
    public IEClienteCreato convert(ClienteCreato domainEvent) {
        DatiAnagraficiCliente datiAnagraficiCliente = domainEvent.datiAnagrafici();
        return new IEClienteCreato(domainEvent.idCliente().id(), domainEvent.codiceCliente().codice(), datiAnagraficiCliente.nomeCliente().nome(), datiAnagraficiCliente.cognomeCliente().cognome(),datiAnagraficiCliente.dataNascita().data(), datiAnagraficiCliente.codiceFiscale().codice(), datiAnagraficiCliente.email().indirizzo(), datiAnagraficiCliente.telefono().numero(), datiAnagraficiCliente.residenza().residenza(), domainEvent.iamId().id());
    }

    @Override
    public Class<ClienteCreato> supportedDomainEvent() {
        return ClienteCreato.class;
    }
    
}
