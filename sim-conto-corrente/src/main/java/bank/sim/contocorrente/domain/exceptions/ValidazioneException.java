package bank.sim.contocorrente.domain.exceptions;

public class ValidazioneException extends RuntimeException {


    public ValidazioneException(String componente, String errore) {
        super(String.format("[%s] non valido: [%s]", componente, errore));
    }


    
}
