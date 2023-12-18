package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

import com.estacionamentos.segs.estacionamentosegs.veiculo.RelatorioService;
import com.estacionamentos.segs.estacionamentosegs.veiculo.Veiculo;
import com.estacionamentos.segs.estacionamentosegs.veiculo.VeiculoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class RegistroController {

    private final RegistroService registroService;
    private final VeiculoService veiculoService;
    private final RelatorioService relatorioService;

    public RegistroController(VeiculoService veiculoService, RegistroService registroService, RelatorioService relatorioService) {
        this.veiculoService = veiculoService;
        this.registroService = registroService;
        this.relatorioService = relatorioService;
    }
    //Listagem de veículo e registros

    @GetMapping("/gerarRelatorio")
    public ResponseEntity<byte[]> gerarRelatorio(
            @RequestParam("outputPath") String outputPath
    ) throws IOException {
        List<Veiculo> veiculos = veiculoService.findAll();
        List<Registro> registros = registroService.findAll();

        byte[] relatorioBytes = relatorioService.gerarRelatorio(veiculos, registros);

        // Configurar o cabeçalho para indicar o tipo de conteúdo e o nome do arquivo
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "relatorio.xlsx");

        return new ResponseEntity<>(relatorioBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/listagemDeRegistros")
    public String listRegistros(Model theModel,
                                @RequestParam(name = "filter", required = false) String filter,
                                @RequestParam(name = "orderBy", required = false, defaultValue = "STATUS_DESC") String orderBy) {

        List<RegistroVeiculoDTO> registros = registroService.findAllByFilter(filter, orderBy);

        theModel.addAttribute("registros", registros);

        return "veiculos/list-registros";
    }


}
