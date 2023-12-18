package com.estacionamentos.segs.estacionamentosegs.veiculo;

import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.Registro;
import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.RegistroDTO;
import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.RegistroService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RelatorioServiceTest {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private RegistroService registroService;

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void afterEach(){
        jdbcTemplate.execute("""
                DELETE FROM registro
                """);
        jdbcTemplate.execute("""
                DELETE FROM veiculos
                """);
    }
    DateTimeFormatter defaultFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private List<Registro> createMultiRegistrosSoEntrada() {
        List<Veiculo> veiculos = createDefaultMultiCars();
        LocalDateTime entryDate = LocalDateTime.of(2023, 12, 15, 10, 15, 15);

        for (Veiculo veiculo : veiculos) {
            registroService.cadastrarEntrada(null, VeiculoDTO.fromVeiculo(veiculo), entryDate.format(defaultFormat));
            entryDate = entryDate.plusHours(1);
        }


        return registroService.findAll();
    }

    private List<Veiculo> createDefaultMultiCars() {
        VeiculoDTO veiculoDTO1 = VeiculoDTO.builder()
                .placa("ZZQ1234")
                .modelo("Fusca")
                .tipo("Carro")
                .build();
        veiculoService.getOrCreateVeiculo(veiculoDTO1);
        VeiculoDTO veiculoDTO2 = VeiculoDTO.builder()
                .placa("FGH6549")
                .modelo("Civic")
                .tipo("Moto")
                .build();
        veiculoService.getOrCreateVeiculo(veiculoDTO2);
        VeiculoDTO veiculoDTO3 = VeiculoDTO.builder()
                .placa("ABC1234")
                .modelo("Phost")
                .tipo("Carro")
                .build();
        veiculoService.getOrCreateVeiculo(veiculoDTO3);
        return veiculoService.findAll();
    }

    @Test
    void gerarRelatorio() throws IOException {
        createMultiRegistrosSoEntrada();
        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa("ABC1234")
                .saida(LocalDateTime.of(2023, 12, 15, 13, 15, 15))
                .build();
        registroService.cadastrarSaida(registroDTO.getPlaca(), String.valueOf(registroDTO.getSaida()));
        List<Veiculo> veiculos = veiculoService.findAll();
        List<Registro> registros = registroService.findAll();

        byte[] bytes = relatorioService.gerarRelatorio(veiculos, registros);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Workbook workbook = new XSSFWorkbook(byteArrayInputStream)) {

            assertEquals(1, workbook.getNumberOfSheets());
            assertEquals(workbook.getSheetName(0),"Veiculo_Registros");

            Sheet headers = workbook.getSheetAt(0);

            assertEquals("Placa", headers.getRow(0).getCell(0).getStringCellValue());
            assertEquals("Modelo", headers.getRow(0).getCell(1).getStringCellValue());
            assertEquals("Tipo do Veiculo", headers.getRow(0).getCell(2).getStringCellValue());
            assertEquals("Entrada", headers.getRow(0).getCell(3).getStringCellValue());
            assertEquals("Saída", headers.getRow(0).getCell(4).getStringCellValue());
            assertEquals("Valor", headers.getRow(0).getCell(5).getStringCellValue());
            assertEquals("Status", headers.getRow(0).getCell(6).getStringCellValue());

            assertEquals(veiculos.get(0).getPlaca(), headers.getRow(1).getCell(0).getStringCellValue());
            assertEquals(veiculos.get(0).getModelo(), headers.getRow(1).getCell(1).getStringCellValue());
            assertEquals(veiculos.get(0).getTipo(), headers.getRow(1).getCell(2).getStringCellValue());
            assertEquals(registros.get(0).getEntrada().format(defaultFormat), headers.getRow(1).getCell(3).getStringCellValue());
            assertEquals("", headers.getRow(1).getCell(4).getStringCellValue());
            assertEquals("R$0.0", headers.getRow(1).getCell(5).getStringCellValue());
            assertEquals("Em Andamento", headers.getRow(1).getCell(6).getStringCellValue());

            assertEquals(veiculos.get(1).getPlaca(), headers.getRow(2).getCell(0).getStringCellValue());
            assertEquals(veiculos.get(1).getModelo(), headers.getRow(2).getCell(1).getStringCellValue());
            assertEquals(veiculos.get(1).getTipo(), headers.getRow(2).getCell(2).getStringCellValue());
            assertEquals(registros.get(1).getEntrada().format(defaultFormat), headers.getRow(2).getCell(3).getStringCellValue());
            assertEquals("", headers.getRow(2).getCell(4).getStringCellValue());
            assertEquals("R$0.0", headers.getRow(2).getCell(5).getStringCellValue());
            assertEquals("Em Andamento", headers.getRow(2).getCell(6).getStringCellValue());

            assertEquals(veiculos.get(2).getPlaca(), headers.getRow(3).getCell(0).getStringCellValue());
            assertEquals(veiculos.get(2).getModelo(), headers.getRow(3).getCell(1).getStringCellValue());
            assertEquals(veiculos.get(2).getTipo(), headers.getRow(3).getCell(2).getStringCellValue());
            assertEquals(registros.get(2).getEntrada().format(defaultFormat), headers.getRow(3).getCell(3).getStringCellValue());
            assertEquals(registros.get(2).getSaida().format(defaultFormat), headers.getRow(3).getCell(4).getStringCellValue());
            assertEquals("R$2.0", headers.getRow(3).getCell(5).getStringCellValue());
            assertEquals("Concluído", headers.getRow(3).getCell(6).getStringCellValue());
        }
    }
}