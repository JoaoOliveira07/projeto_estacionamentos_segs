package com.estacionamentos.segs.estacionamentosegs;

import com.estacionamentos.segs.estacionamentosegs.service.RegistroDTO;
import com.estacionamentos.segs.estacionamentosegs.service.RegistroService;
import com.estacionamentos.segs.estacionamentosegs.service.VeiculoDTO;
import com.estacionamentos.segs.estacionamentosegs.service.VeiculoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EstacionamentosegsApplicationTests {

    VeiculoService veiculoService;

    RegistroService registroService;


    @Test
    void saveCar() {
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("MIG8223")
                .modelo("Clio")
                .ano(2011)
                .tipo("Carro")
                .build();

        veiculoService.cadastrarVeiculo(veiculoDTO);
    }

    @Test
    void saveDateEntrada() {
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("MIG8223")
                .build();
        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa(veiculoDTO.getPlaca())
                .entrada(LocalDateTime.now())
                .build();
        registroService.cadastrarEntrada(registroDTO, veiculoDTO);
    }

    @Test
    void saveDateSaida() {
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("MIG8223")
                .build();
        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa(veiculoDTO.getPlaca())
                .saida(LocalDateTime.now())
                .build();
        registroService.cadastrarSaida(registroDTO, veiculoDTO);
    }

}
