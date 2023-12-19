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


    public VeiculoController(VeiculoService theVeiculoService) {
        veiculoService = theVeiculoService;
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


    //Deletar um veículo e todos os registros relacionados a ele

    @PostMapping("/delete")
    @Transactional
    public String delete(@RequestParam("placaVeiculo") String placaVeiculo) {
        // Encontrar o veículo pelo número da placa
        Veiculo veiculo = veiculoService.findByPlaca(placaVeiculo);

        // Verificar se o veículo foi encontrado
        if (veiculo != null) {

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

    @GetMapping("/verificarPlaca/{placa}")
    @ResponseBody
    public Veiculo verificarPlaca(@PathVariable String placa) {
        return veiculoService.getVeiculoByPlaca(placa);
    }
}