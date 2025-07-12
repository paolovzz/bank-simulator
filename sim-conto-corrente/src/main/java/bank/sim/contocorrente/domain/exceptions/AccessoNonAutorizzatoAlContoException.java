package bank.sim.contocorrente.domain.exceptions;

public class AccessoNonAutorizzatoAlContoException extends RuntimeException {


    public AccessoNonAutorizzatoAlContoException(String codiceCliente) {
        super(String.format("Accesso al conto non autorizzato per il cliente [%s]", codiceCliente));
    }


    
}
