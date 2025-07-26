package bank.sim.cliente.domain.models.vo;

import bank.sim.cliente.domain.exceptions.ValidazioneException;
import bank.sim.cliente.domain.models.enums.CodiceErrore;

public record Telefono(String numero){

    public Telefono(String numero) {
        if (numero == null || numero.isBlank()) {
            throw new ValidazioneException(Telefono.class.getSimpleName(), CodiceErrore.TELEFONO_NON_VALIDO.getCodice());
        }
        this.numero = numero;
    }
}
