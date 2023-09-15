package br.com.desbugando.registroatividades.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MovimentoDTO {
    private Long id;
    private String descricao;
    private Instant registroInicio;
    private Instant registroFim;
}
