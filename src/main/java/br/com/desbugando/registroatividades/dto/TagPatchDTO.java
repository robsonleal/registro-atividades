package br.com.desbugando.registroatividades.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagPatchDTO {
    @JsonProperty("descricao")
    private String nome;
}
