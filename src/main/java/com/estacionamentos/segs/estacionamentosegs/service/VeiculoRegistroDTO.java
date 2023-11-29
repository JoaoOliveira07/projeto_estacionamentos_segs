package com.estacionamentos.segs.estacionamentosegs.service;

import com.estacionamentos.segs.estacionamentosegs.entity.Registro;
import com.estacionamentos.segs.estacionamentosegs.entity.Veiculo;

public class VeiculoRegistroDTO {
    private Veiculo veiculo;
    private Registro registro;

    // construtores, getters e setters
    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }
}
