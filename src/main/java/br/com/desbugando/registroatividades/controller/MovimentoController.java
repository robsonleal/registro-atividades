package br.com.desbugando.registroatividades.controller;

import br.com.desbugando.registroatividades.dto.MovimentoDTO;
import br.com.desbugando.registroatividades.service.MovimentoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movimentos")
@AllArgsConstructor
public class MovimentoController {
    private MovimentoService movimentoService;

    @PostMapping("/{atividadeId}")
    public String criar(@ModelAttribute MovimentoDTO movimentoDTO, @PathVariable Long atividadeId) {
        movimentoService.criar(atividadeId, movimentoDTO);

        return "redirect:/atividades/" + atividadeId;
    }
}
