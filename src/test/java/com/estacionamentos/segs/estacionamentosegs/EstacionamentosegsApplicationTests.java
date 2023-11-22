package com.estacionamentos.segs.estacionamentosegs;

import com.estacionamentos.segs.estacionamentosegs.service.EstacionamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EstacionamentosegsApplicationTests {

    @Autowired
    EstacionamentoService estacionamentoService;



    @Test
    void save() {
        estacionamentoService.cadastrarVeiculo("MIG8223", "Clio", 2011, "Carro");
    }

}
