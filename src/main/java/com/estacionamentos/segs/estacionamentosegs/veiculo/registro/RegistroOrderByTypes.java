package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

public enum RegistroOrderByTypes {

    PLACA_DESC,MODELO_DESC,TIPO_DESC,ENTRADA_DESC,SAIDA_DESC,VALOR_DESC,STATUS_DESC;

    public static RegistroOrderByTypes fromString(String text) {
        for (RegistroOrderByTypes tipo : RegistroOrderByTypes.values()) {
            if (tipo.name().equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
