package bank.sim.cliente.adapter.input.rest.requests;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreaClienteReq {
    
   private String nome; 
   private String cognome; 
   private LocalDate dataNascita; 
   private String codiceFiscale; 
   private String email; 
   private String telefono; 
   private String residenza;
}
