package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.model.Atividade;
import br.com.desbugando.registroatividades.repository.AtividadeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AtividadeService {

    private final AtividadeRepository repository;
    private final ModelMapper mapper;

    @Transactional
    public AtividadeDTO insert(AtividadeDTO dto) {
        Atividade model = repository.save(mapper.map(dto, Atividade.class));

        return mapper.map(model, AtividadeDTO.class);
    }
}
