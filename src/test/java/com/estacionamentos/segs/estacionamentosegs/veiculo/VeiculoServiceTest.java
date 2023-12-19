package com.estacionamentos.segs.estacionamentosegs.veiculo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VeiculoServiceTest {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Veiculo defaultCar;

    @BeforeEach
    public void setupDefaultEntities(){
        defaultCar = createDefaultCar();
    }


    @AfterEach
    public void afterEach(){
        jdbcTemplate.execute("""
                DELETE FROM registro
                """);
        jdbcTemplate.execute("""
                DELETE FROM veiculos
                """);
    }
    @Test
    void getOrCreateVeiculo() {
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("AVD1234")
                .modelo("Clio")
                .tipo("Carro")
                .deleteTimeStamp(null)
                .build();
        veiculoService.getOrCreateVeiculo(veiculoDTO);
        List<Veiculo> veiculos = veiculoService.findAll();
        assertEquals(2,veiculos.size());
        assertEquals(VeiculoDTO.fromVeiculo(defaultCar).getPlaca(),veiculos.get(0).getPlaca());
        assertEquals(VeiculoDTO.fromVeiculo(defaultCar).getModelo(),veiculos.get(0).getModelo());
        assertEquals(VeiculoDTO.fromVeiculo(defaultCar).getTipo(),veiculos.get(0).getTipo());
        assertEquals(VeiculoDTO.fromVeiculo(defaultCar).getDeleteTimeStamp(),veiculos.get(0).getDeleteTimeStamp());
        assertEquals(veiculoDTO.getPlaca(),veiculos.get(1).getPlaca());
        assertEquals(veiculoDTO.getModelo(),veiculos.get(1).getModelo());
        assertEquals(veiculoDTO.getTipo(),veiculos.get(1).getTipo());
        assertEquals(veiculoDTO.getDeleteTimeStamp(),veiculos.get(1).getDeleteTimeStamp());
    }

    @Test
    void salvandoVeiculoComPlacaAntiga() {
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("AVD1D34")
                .modelo("GOL 1.0")
                .tipo("Carro")
                .deleteTimeStamp(null)
                .build();

        Veiculo veiculo = veiculoService.getVeiculoByPlaca(veiculoDTO.getPlaca());
        assertNotNull(veiculo);
        assertEquals(veiculoDTO.getModelo(), veiculo.getModelo());
    }

    @Test
    void salvandoVeiculoComPlacaMercoSul() {
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("ABC1D23")
                .modelo("PASSAT TS")
                .tipo("Carro")
                .deleteTimeStamp(null)
                .build();

        Veiculo veiculo = veiculoService.getVeiculoByPlaca(veiculoDTO.getPlaca());
        assertNotNull(veiculo);
        assertEquals(veiculoDTO.getModelo(), veiculo.getModelo());
    }

    @Test
    void tentandoSalvarVeiculoComPlacaErrada() {
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("AVDDD34")
                .tipo("Carro")
                .deleteTimeStamp(null)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            veiculoService.getVeiculoByPlaca(veiculoDTO.getPlaca());
        });
    }
    @Test
    void findAllCar() {
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("AVD1234")
                .modelo("Clio")
                .tipo("Carro")
                .deleteTimeStamp(null)
                .build();
        veiculoService.getOrCreateVeiculo(veiculoDTO);
        List<Veiculo> veiculos = veiculoService.findAll();
        assertEquals(2,veiculos.size());
    }


    @Test
    void findById() {
        Veiculo veiculo = veiculoService.findById(VeiculoDTO.fromVeiculo(defaultCar).getId());
        assertNotNull(veiculo);
    }

    @Test
    void findByIdNotExist() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> veiculoService.findById(1),
                "Não foi encontrado ID- 1"
        );
    }

    @Test
    void deleteByPlaca() {
        List<Veiculo> veiculos = veiculoService.findAll();
        assertEquals(1,veiculos.size());
        Assertions.assertNull(veiculos.get(0).getDeleteTimeStamp());
        veiculoService.deleteByPlaca(VeiculoDTO.fromVeiculo(defaultCar).getPlaca());
        veiculos = veiculoService.findAll();
        Assertions.assertTrue(veiculos.isEmpty());
    }

    @Test
    void getVeiculoByPlaca() {
        String placa = "MIG8209";
        Veiculo veiculo = veiculoService.getVeiculoByPlaca(placa);
        assertNotNull(veiculo);
        assertEquals(placa.toLowerCase(), veiculo.getPlaca().toLowerCase());
    }

    private Veiculo createDefaultCar(){
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("MIG8209")
                .modelo("Hornet")
                .tipo("Moto")
                .deleteTimeStamp(LocalDateTime.now())
                .build();
        return veiculoService.getOrCreateVeiculo(veiculoDTO);
    }

}