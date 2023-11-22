package com.estacionamentos.segs.estacionamentosegs;

import com.estacionamentos.segs.estacionamentosegs.service.EstacionamentoService;
import com.estacionamentos.segs.estacionamentosegs.service.VeiculoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EstacionamentosegsApplicationTests {

    @Autowired
    EstacionamentoService estacionamentoService;



    @Test
    void save() {
        veiculosService.save(VeiculoDTO.builder()
                .placaDoVeiculo("MIG8924")
                .modeloDoVeiculo("Clio")
                .anoDoVeiculo(2011)
                .tipoDoVeiculo("Carro")
                .build());
    }

    @Test
    void delete() {
        veiculosService.deleteById(VeiculoDTO.builder()
                .id(1)
                .build());
    }

}
