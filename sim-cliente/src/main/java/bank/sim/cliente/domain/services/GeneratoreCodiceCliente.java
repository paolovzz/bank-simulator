package bank.sim.cliente.domain.services;

import java.security.SecureRandom;

import bank.sim.cliente.domain.models.vo.CodiceCliente;

public final class GeneratoreCodiceCliente {

    private static final String DEFAULT_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 64 URL-safe chars
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(int size) {
        StringBuilder id = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int index = RANDOM.nextInt(DEFAULT_ALPHABET.length());
            id.append(DEFAULT_ALPHABET.charAt(index));
        }
        return id.toString();
    }

    
    public final CodiceCliente genera() {
        return new CodiceCliente(generate(10));
    }
}
