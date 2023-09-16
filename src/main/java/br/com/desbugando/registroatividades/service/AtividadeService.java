package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.model.Atividade;
import br.com.desbugando.registroatividades.repository.AtividadeRepository;
import br.com.desbugando.registroatividades.repository.CategoriaRepository;
import br.com.desbugando.registroatividades.repository.TagRepository;
import br.com.desbugando.registroatividades.service.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AtividadeService {

    private final AtividadeRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final TagRepository tagRepository;
    private final ModelMapper mapper;

    @Transactional
    public AtividadeDTO insert(AtividadeDTO dto) throws BusinessException {
        for (String tag : dto.getTags())
            if (!tagRepository.existsByNome(tag))
                throw new BusinessException("Tag não cadastrada: " + tag);

        if (!categoriaRepository.existsByNome(dto.getCategoria()))
            throw new BusinessException("Categoria não cadastrada: " + dto.getCategoria());

        Atividade model = repository.save(mapper.map(dto, Atividade.class));

        return mapper.map(model, AtividadeDTO.class);
    }
}
