package com.estacionamentos.segs.estacionamentosegs.veiculo.registro;

import com.estacionamentos.segs.estacionamentosegs.veiculo.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/registros")
public class RegistroController {

    private final RegistroService registroService;
    private final RelatorioService relatorioService;

    public RegistroController(RegistroService registroService, RelatorioService relatorioService) {
        this.registroService = registroService;
        this.relatorioService = relatorioService;
    }
    //Listagem de veículo e registros

    @GetMapping("/gerarRelatorio")
    public ResponseEntity<byte[]> gerarRelatorio(
            @RequestParam("outputPath") String outputPath
    ) throws IOException {
        List<Registro> registros = registroService.findAll();

        byte[] relatorioBytes = relatorioService.gerarRelatorio(registros);

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

    //Salvar no banco o novo veículo e um registro de Entrada
    @PostMapping("/save")
    public String saveVeiculoRegistro(@RequestParam("dataLocal") String dataLocal, @ModelAttribute("veiculoRegistroDTO") VeiculoRegistroDTO veiculoRegistroDTO) {
        VeiculoDTO veiculoDTO = veiculoRegistroDTO.getVeiculoDTO();
        RegistroDTO registroDTO = veiculoRegistroDTO.getRegistroDTO();

        registroService.cadastrarEntrada(registroDTO, veiculoDTO, dataLocal);
        return "redirect:/registros/listagemDeRegistros";
    }

    //Registrar a saída do veículo
    @PostMapping("/saveSaida")
    public String saveSaidaRegistro(@RequestParam("placa") String placa,
                                    @RequestParam("saida") String saida, RedirectAttributes redirectAttributes) {
        try {
            registroService.cadastrarSaida(placa, saida);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Saída cadastrada com sucesso.");
        } catch (DataInvalidaException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar a saída: " + e.getMessage());
        }
        return "redirect:/registros/listagemDeRegistros";
    }

}
