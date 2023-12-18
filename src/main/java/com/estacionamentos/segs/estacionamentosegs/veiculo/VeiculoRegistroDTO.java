package com.estacionamentos.segs.estacionamentosegs.veiculo;

import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.Registro;
import com.estacionamentos.segs.estacionamentosegs.veiculo.Veiculo;
import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.RegistroDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class VeiculoRegistroDTO {
    private VeiculoDTO veiculoDTO;
    private RegistroDTO registroDTO;

    public VeiculoDTO getVeiculoDTO() {
        return veiculoDTO;
    }

    public void setVeiculoDTO(VeiculoDTO veiculoDTO) {
        this.veiculoDTO = veiculoDTO;
    }

    public RegistroDTO getRegistroDTO() {
        return registroDTO;
    }

    public void setRegistroDTO(RegistroDTO registroDTO) {
        this.registroDTO = registroDTO;
    }
}
