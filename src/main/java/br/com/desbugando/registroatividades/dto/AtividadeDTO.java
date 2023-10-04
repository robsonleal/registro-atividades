package br.com.desbugando.registroatividades.dto;

import br.com.desbugando.registroatividades.model.Estado;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@ToString
public class AtividadeDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String categoria;
    private Set<String> tags;
    private Estado estado;
    private Instant criadoEm;
    private Instant atualizadoEm;
    private Set<MovimentoDTO> movimentos;
}
