package com.estacionamentos.segs.estacionamentosegs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class EstacionamentosegsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstacionamentosegsApplication.class, args);
    }
}
