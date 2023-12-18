package com.estacionamentos.segs.estacionamentosegs.veiculo;

import com.estacionamentos.segs.estacionamentosegs.ConsultaPlacaExternaDto;
import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.DataInvalidaException;
import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.RegistroDTO;
import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.RegistroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    private VeiculoService veiculoService;


    private RegistroService registroService;

    public VeiculoController(VeiculoService theVeiculoService, RegistroService theRegistroService) {
        veiculoService = theVeiculoService;
        registroService = theRegistroService;
    }


    //Lista de Veículos
    @GetMapping("/listagemDeVeiculos")
    public String listVeiculos(Model theModel) {

        List<Veiculo> theVeiculos = veiculoService.findAll();

        theModel.addAttribute("veiculo", theVeiculos);

        return "veiculos/list-veiculos";
    }

    //Botão adicionar um novo Veículo
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        VeiculoRegistroDTO veiculoRegistroDTO = new VeiculoRegistroDTO();
        theModel.addAttribute("veiculoRegistroDTO", veiculoRegistroDTO);

        return "veiculos/cadastro-veiculo";
    }

    //Salvar no banco o novo veículo e um registro de Entrada
    @PostMapping("/save")
    public String saveVeiculoRegistro(@RequestParam("dataLocal") String dataLocal, @ModelAttribute("veiculoRegistroDTO") VeiculoRegistroDTO veiculoRegistroDTO) {
        VeiculoDTO veiculoDTO = veiculoRegistroDTO.getVeiculoDTO();
        RegistroDTO registroDTO = veiculoRegistroDTO.getRegistroDTO();

        registroService.cadastrarEntrada(registroDTO, veiculoDTO, dataLocal);
        return "redirect:/listagemDeRegistros";
    }

    //Deletar um veículo e todos os registros relacionados a ele
    @PostMapping("/delete")
    @Transactional
    public String delete(@RequestParam("placaVeiculo") String placaVeiculo) {
        // Encontrar o veículo pelo número da placa
        Veiculo veiculo = veiculoService.findByPlaca(placaVeiculo);

        // Verificar se o veículo foi encontrado
        if (veiculo != null) {
            // Deletar todos os registros vinculados ao veículo
            registroService.deleteByVeiculo(veiculo);

            // Deletar o veículo
            veiculoService.deleteByPlaca(placaVeiculo);

            return "redirect:/veiculos/listagemDeVeiculos";
        } else {
            return "redirect:/veiculos/listagemDeVeiculos";
        }
    }


    //Botão Alterar, que retorna os dados do veículo pelo ID
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("veiculoId") int theId, Model theModel) {
        Veiculo theVeiculo = veiculoService.findById(theId);
        theModel.addAttribute("veiculos", theVeiculo);
        return "veiculos/update-veiculo";
    }

    //Botão de salvar a alteração
    @PostMapping("/saveUpdate")
    public String saveVeiculo(@ModelAttribute("veiculos") Veiculo theVeiculo) {

        veiculoService.save(theVeiculo);

        return "redirect:/veiculos/listagemDeVeiculos";
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
        return "redirect:/listagemDeRegistros";
    }

    @GetMapping("/verificarPlaca/{placa}")
    @ResponseBody
    public Veiculo verificarPlaca(@PathVariable String placa) {
        return veiculoService.getVeiculoByPlaca(placa);
    }
}