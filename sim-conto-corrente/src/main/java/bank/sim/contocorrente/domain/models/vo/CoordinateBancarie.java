package bank.sim.contocorrente.domain.models.vo;

import lombok.Getter;

@Getter
public class CoordinateBancarie {

    private NumeroConto numeroConto;
    private Iban iban;
    private Bic bic;
    private Cab cab;
    private Abi abi;

    private CoordinateBancarie(NumeroConto numeroConto, Iban iban, Cab cab, Abi abi) {
        this.numeroConto = numeroConto;
        this.iban = iban;
        this.bic = Bic.with("BNKSIM91");
        this.cab = cab;
        this.abi = abi;
    }                                               

    public static CoordinateBancarie with(String numeroConto, String iban, String cab, String abi) {
        NumeroConto numeroContoVO = NumeroConto.with(numeroConto);
        Iban ibanVO = Iban.with(iban);
        Cab cabVO = Cab.with(cab);
        Abi abiVO = Abi.with(abi);
        return new CoordinateBancarie(numeroContoVO, ibanVO, cabVO, abiVO);
    }
}
