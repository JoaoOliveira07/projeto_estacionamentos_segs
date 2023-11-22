package com.estacionamentos.segs.estacionamentosegs;

import com.estacionamentos.segs.estacionamentosegs.service.EstacionamentoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EstacionamentosegsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstacionamentosegsApplication.class, args);
	}
}
