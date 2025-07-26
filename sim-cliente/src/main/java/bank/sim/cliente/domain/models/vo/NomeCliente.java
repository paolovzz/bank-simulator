package bank.sim.cliente.domain.models.vo;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record NomeCliente(String nome){

    public NomeCliente(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ValidazioneException(NomeCliente.class.getSimpleName(), CodiceErrore.NOME_CLIENTE_NON_VALIDO.getCodice());
        }
        this.nome = nome;
    }
}
