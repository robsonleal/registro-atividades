package br.com.desbugando.registroatividades.controller;

import br.com.desbugando.registroatividades.dto.MovimentoDTO;
import br.com.desbugando.registroatividades.dto.MovimentoPatchDTO;
import br.com.desbugando.registroatividades.service.MovimentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

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

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, String>> patchDescricao(
        @RequestBody MovimentoPatchDTO movimentoPatchDTO, @PathVariable long id) {
        MovimentoDTO movimentoDTO = movimentoService.patchDescricao(movimentoPatchDTO, id);

        return ResponseEntity.ok(Map.of("redirectUrl", "/atividades/" + movimentoDTO.getAtividadeId()));
    }
}
