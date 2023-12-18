package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

import com.estacionamentos.segs.estacionamentosegs.veiculo.Veiculo;
import com.estacionamentos.segs.estacionamentosegs.veiculo.VeiculoDTO;
import com.estacionamentos.segs.estacionamentosegs.veiculo.VeiculoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistroServiceTest {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private RegistroService registroService;

    @Autowired
    private JdbcTemplate jdbcTemplate;



    private Veiculo defaultCar;
    DateTimeFormatter defaultFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
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
    void cadastrarEntrada_should_create_new_register() {
        assertTrue(registroService.findAll().isEmpty());
        String entryDate = LocalDateTime.now().format(defaultFormat);
        
        registroService.cadastrarEntrada(null, VeiculoDTO.fromVeiculo(defaultCar), entryDate);

        List<Registro> registros = registroService.findAll();
        Assertions.assertEquals(1,registros.size());
        
        Registro registro = registros.get(0);
        assertEquals(registro.getEntrada(),LocalDateTime.parse(entryDate,defaultFormat));
        assertEquals(registro.getVeiculos().getId(),defaultCar.getId());
    }

    @Test
    void cadastrarSaida() {
        String entryDate = LocalDateTime.of(2023, 12, 05, 10, 59, 15).format(defaultFormat);
        registroService.cadastrarEntrada(null, VeiculoDTO.fromVeiculo(defaultCar), entryDate);

        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa("MIG8209")
                .saida(LocalDateTime.of(2023, 12, 05, 11, 59, 15))
                .build();
        registroService.cadastrarSaida(registroDTO.getPlaca(), String.valueOf(registroDTO.getSaida()));

        List<Registro> registros = registroService.findAll();
        Registro registro = registros.get(0);
        assertEquals(registro.getValor(),2.0);
        assertEquals(registro.getSaida(), LocalDateTime.of(2023, 12, 05, 11, 59, 15));
    }


    @Test
    void salvarDataSaidaMaiorQueEntrada() {
        String entryDate = LocalDateTime.of(2023, 12, 05, 10, 59, 15).format(defaultFormat);
        registroService.cadastrarEntrada(null, VeiculoDTO.fromVeiculo(defaultCar), entryDate);

        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa("MIG8209")
                .saida(LocalDateTime.of(2023, 12, 04, 18, 59, 15))
                .build();
        DataInvalidaException exception = assertThrows(DataInvalidaException.class, () -> {
            registroService.cadastrarSaida(registroDTO.getPlaca(), String.valueOf(registroDTO.getSaida()));
        });
        List<Registro> registros = registroService.findAll();
        Registro registro = registros.get(0);

        assertNull(registro.getSaida());
        assertEquals(registro.getValor(),0.0);
        assertEquals("Data de Saída é menor que a de entrada.", exception.getMessage());
    }

    @Test
    void calcularValorComUmaHora() {
        String entryDate = LocalDateTime.of(2023, 12, 05, 10, 59, 15).format(defaultFormat);
        registroService.cadastrarEntrada(null, VeiculoDTO.fromVeiculo(defaultCar), entryDate);

        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa("MIG8209")
                .saida(LocalDateTime.of(2023, 12, 05, 11, 59, 15))
                .build();
        registroService.cadastrarSaida(registroDTO.getPlaca(), String.valueOf(registroDTO.getSaida()));

        List<Registro> registros = registroService.findAll();
        Registro registro = registros.get(0);
        assertEquals(registro.getValor(),2.0);
    }


    @Test
    void calcularValorComDuasHora() {
        String entryDate = LocalDateTime.of(2023, 12, 05, 10, 59, 15).format(defaultFormat);
        registroService.cadastrarEntrada(null, VeiculoDTO.fromVeiculo(defaultCar), entryDate);

        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa("MIG8209")
                .saida(LocalDateTime.of(2023, 12, 05, 12, 59, 15))
                .build();
        registroService.cadastrarSaida(registroDTO.getPlaca(), String.valueOf(registroDTO.getSaida()));

        List<Registro> registros = registroService.findAll();
        Registro registro = registros.get(0);
        assertEquals(registro.getValor(),3.0);
    }

    @Test
    void calcularValorComTresHora() {
        String entryDate = LocalDateTime.of(2023, 12, 05, 10, 59, 15).format(defaultFormat);
        registroService.cadastrarEntrada(null, VeiculoDTO.fromVeiculo(defaultCar), entryDate);

        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa("MIG8209")
                .saida(LocalDateTime.of(2023, 12, 05, 13, 59, 15))
                .build();
        registroService.cadastrarSaida(registroDTO.getPlaca(), String.valueOf(registroDTO.getSaida()));

        List<Registro> registros = registroService.findAll();
        Registro registro = registros.get(0);
        assertEquals(registro.getValor(),5.0);
    }

    @Test
    void calcularValorComMaisDeTresHora() {
        String entryDate = LocalDateTime.of(2023, 12, 05, 10, 59, 15).format(defaultFormat);
        registroService.cadastrarEntrada(null, VeiculoDTO.fromVeiculo(defaultCar), entryDate);

        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa("MIG8209")
                .saida(LocalDateTime.of(2023, 12, 05, 14, 59, 15))
                .build();
        registroService.cadastrarSaida(registroDTO.getPlaca(), String.valueOf(registroDTO.getSaida()));

        List<Registro> registros = registroService.findAll();
        Registro registro = registros.get(0);
        assertEquals(registro.getValor(),7.0);
    }

    @Test
    void pesquisarPorPlaca() {
        createMultiRegistrosSoEntrada();
        List<RegistroVeiculoDTO> resultados = registroService.findAllByFilter("MIG","PLACA_DESC");
        Assertions.assertEquals(1,resultados.size());
    }
    @Test

    void pesquisarPorModelo() {
        createMultiRegistrosSoEntrada();
        List<RegistroVeiculoDTO> resultados = registroService.findAllByFilter("Civic","MODELO_DESC");
        Assertions.assertEquals(1,resultados.size());
    }

    @Test
    void pesquisarPorTipo() {
        createMultiRegistrosSoEntrada();
        List<RegistroVeiculoDTO> resultados = registroService.findAllByFilter("moto","TIPO_DESC");
        Assertions.assertEquals(2,resultados.size());
    }

    @Test
    void ordenarPelaPlaca() {
        createMultiRegistrosSoEntrada();
        List<Registro> registros = registroService.findAll();
        List<RegistroVeiculoDTO> resultados = registroService.findAllByFilter(null,"PLACA_DESC");
        Assertions.assertEquals(registros.get(1).getVeiculos().getPlaca(), resultados.get(0).getPlaca());
    }

    @Test
    void ordenarPeloModelo() {
        createMultiRegistrosSoEntrada();
        List<Registro> registros = registroService.findAll();
        List<RegistroVeiculoDTO> resultados = registroService.findAllByFilter(null,"MODELO_DESC");
        Assertions.assertEquals(registros.get(4).getVeiculos().getModelo(), resultados.get(0).getModelo());
    }

    @Test
    void ordenarPelpTipo() {
        createMultiRegistrosSoEntrada();
        List<Registro> registros = registroService.findAll();
        List<RegistroVeiculoDTO> resultados = registroService.findAllByFilter(null,"TIPO_DESC");
        Assertions.assertEquals(registros.get(0).getVeiculos().getTipo(), resultados.get(0).getTipo());
    }
    @Test
    void ordenarPelaEntrada() {
        createMultiRegistrosSoEntrada();
        List<Registro> registros = registroService.findAll();
        List<RegistroVeiculoDTO> resultados = registroService.findAllByFilter(null,"ENTRADA_DESC");
        Assertions.assertEquals(registros.get(4).getEntrada(), resultados.get(0).getEntrada());
    }
    @Test
    void ordenarPelaSaida() {
        createMultiRegistrosSoEntrada();
        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa("PHO9458")
                .saida(LocalDateTime.of(2023,12,15,20,15,15))
                .build();
        registroService.cadastrarSaida(registroDTO.getPlaca(), String.valueOf(registroDTO.getSaida()));
        List<Registro> registros = registroService.findAll();
        List<RegistroVeiculoDTO> resultados = registroService.findAllByFilter(null,"SAIDA_DESC");
        Assertions.assertEquals(registros.get(4).getSaida(), resultados.get(4).getSaida());
        Assertions.assertEquals(registros.get(0).getSaida(), resultados.get(0).getSaida());
    }
    @Test
    void ordenarPelaValor() {
        createMultiRegistrosSoEntrada();
        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa("PHO9458")
                .saida(LocalDateTime.of(2023,12,15,20,15,15))
                .build();
        registroService.cadastrarSaida(registroDTO.getPlaca(), String.valueOf(registroDTO.getSaida()));
        List<Registro> registros = registroService.findAll();
        List<RegistroVeiculoDTO> resultados = registroService.findAllByFilter(null,"VALOR_DESC");
        Assertions.assertEquals(registros.get(4).getValor(), resultados.get(0).getValor());
    }
    @Test
    void ordenarPelaStatus() {
        createMultiRegistrosSoEntrada();
        RegistroDTO registroDTO = RegistroDTO.builder()
                .placa("MIG8209")
                .saida(LocalDateTime.of(2023,12,15,20,15,15))
                .build();
        registroService.cadastrarSaida(registroDTO.getPlaca(), String.valueOf(registroDTO.getSaida()));
        List<RegistroVeiculoDTO> resultados = registroService.findAllByFilter(null,"STATUS_DESC");
        List<Registro> registros = registroService.findAll();
        Assertions.assertEquals("Em andamento",resultados.get(0).getStatus());
        Assertions.assertEquals("Concluído",resultados.get(4).getStatus());
    }

    private List<Registro> createMultiRegistrosSoEntrada(){
        List<Veiculo> veiculos = createDefaultMultiCars();
        LocalDateTime entryDate = LocalDateTime.of(2023,12,15,10,15,15);

        for(Veiculo veiculo : veiculos){
            registroService.cadastrarEntrada(null, VeiculoDTO.fromVeiculo(veiculo), entryDate.format(defaultFormat));
            entryDate = entryDate.plusHours(1);
        }


        return registroService.findAll();
    }

    private Veiculo createDefaultCar(){
        VeiculoDTO veiculoDTO = VeiculoDTO.builder()
                .placa("MIG8209")
                .modelo("Hornet")
                .tipo("Moto")
                .deleteTimeStamp(null)
                .build();
       return veiculoService.getOrCreateVeiculo(veiculoDTO);
    }

    private List<Veiculo> createDefaultMultiCars(){
        VeiculoDTO veiculoDTO4 = VeiculoDTO.builder()
                .placa("ZZQ1234")
                .modelo("Fusca")
                .tipo("Carro")
                .build();
        veiculoService.getOrCreateVeiculo(veiculoDTO4);
        VeiculoDTO veiculoDTO2 = VeiculoDTO.builder()
                .placa("FGH6549")
                .modelo("Civic")
                .tipo("Moto")
                .build();
        veiculoService.getOrCreateVeiculo(veiculoDTO2);
        VeiculoDTO veiculoDTO3 = VeiculoDTO.builder()
                .placa("PHO9458")
                .modelo("Aurora")
                .tipo("Carro")
                .build();
        veiculoService.getOrCreateVeiculo(veiculoDTO3);
        VeiculoDTO veiculoDTO1 = VeiculoDTO.builder()
                .placa("ABC1234")
                .modelo("Phost")
                .tipo("Carro")
                .build();
        veiculoService.getOrCreateVeiculo(veiculoDTO1);
        return veiculoService.findAll();
    }
}