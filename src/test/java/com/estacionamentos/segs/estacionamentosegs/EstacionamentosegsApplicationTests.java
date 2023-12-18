package com.estacionamentos.segs.estacionamentosegs;

import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.DataInvalidaException;
import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.RegistroDTO;
import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.RegistroService;
import com.estacionamentos.segs.estacionamentosegs.veiculo.VeiculoDTO;
import com.estacionamentos.segs.estacionamentosegs.veiculo.VeiculoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EstacionamentosegsApplicationTests {

}

