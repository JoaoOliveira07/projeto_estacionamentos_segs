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
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("MIG8223")
                .modelo("Clio")
                .ano(2011)
                .tipo("Carro")
                .build();

        estacionamentoService.cadastrarVeiculo(veiculoDTO);
    }

}
