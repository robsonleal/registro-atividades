package br.com.desbugando.registroatividades.controller;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.service.AtividadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/atividades")
@AllArgsConstructor
public class AtividadeController {

    private AtividadeService service;

    @GetMapping
    public ResponseEntity<List<AtividadeDTO>> getNaoConcluidas() {

        return ResponseEntity.ok(service.getNaoConcluidas());
    }

    @PostMapping
    public ResponseEntity<AtividadeDTO> insert(@RequestBody AtividadeDTO dto) {
        dto = service.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }
}
