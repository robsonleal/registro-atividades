package br.com.desbugando.registroatividades.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class MovimentoDTO {
    private Long id;
    private String descricao;
    private Instant registroInicio;
    private Instant registroFim;
}
