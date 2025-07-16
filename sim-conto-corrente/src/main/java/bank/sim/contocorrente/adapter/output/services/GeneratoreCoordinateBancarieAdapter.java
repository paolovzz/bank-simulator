package bank.sim.contocorrente.adapter.output.services;

import java.security.SecureRandom;

import org.iban4j.CountryCode;

import bank.sim.contocorrente.domain.models.vo.Abi;
import bank.sim.contocorrente.domain.models.vo.Bic;
import bank.sim.contocorrente.domain.models.vo.Cab;
import bank.sim.contocorrente.domain.models.vo.CoordinateBancarie;
import bank.sim.contocorrente.domain.models.vo.Iban;
import bank.sim.contocorrente.domain.models.vo.NumeroConto;
import bank.sim.contocorrente.domain.ports.GeneratoreCoordinateBancarie;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GeneratoreCoordinateBancarieAdapter implements GeneratoreCoordinateBancarie {

    @Override
    public CoordinateBancarie genera() {

        
        org.iban4j.Iban.Builder builder = new org.iban4j.Iban.Builder();
         org.iban4j.Iban iban = builder.countryCode(CountryCode.IT)      // Paese: Italia
                .bankCode("03069")                // ABI (Banca)
                .branchCode("09606")              // CAB (Filiale)
                .accountNumber(generateIdentificativoConto())
                .nationalCheckDigit("S")
                .build();    // Numero conto
        Bic bic = new Bic("BNKSIM91");
        return new CoordinateBancarie(new NumeroConto(iban.getAccountNumber()), new Iban(iban.toString()), bic, new Cab(iban.getBranchCode()), new Abi(iban.getBankCode()));
    }
    private String generateIdentificativoConto() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            int digit = random.nextInt(10); // genera 0-9
            sb.append(digit);
        }
        return sb.toString();
    }
}
