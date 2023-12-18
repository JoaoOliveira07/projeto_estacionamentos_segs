package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException(String mensagem) {
        super(mensagem);
    }
}