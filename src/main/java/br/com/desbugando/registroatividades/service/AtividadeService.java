package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.model.Atividade;
import br.com.desbugando.registroatividades.model.Estado;
import br.com.desbugando.registroatividades.repository.AtividadeRepository;
import br.com.desbugando.registroatividades.repository.CategoriaRepository;
import br.com.desbugando.registroatividades.repository.TagRepository;
import br.com.desbugando.registroatividades.service.exception.BusinessException;
import br.com.desbugando.registroatividades.service.exception.DataBaseException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AtividadeService {

    private final AtividadeRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final TagRepository tagRepository;
    private final ModelMapper mapper;

    @Transactional
    public AtividadeDTO insert(AtividadeDTO dto) {
        dto.setEstado(Estado.ATIVO);

        for (String tag : dto.getTags())
            if (!tagRepository.existsByNome(tag))
                throw new BusinessException("Tag não cadastrada: " + tag);

        if (!categoriaRepository.existsByNome(dto.getCategoria()))
            throw new BusinessException("Categoria não cadastrada: " + dto.getCategoria());

        Atividade model;

        try {
            model = repository.save(mapper.map(dto, Atividade.class));
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }

        return mapper.map(model, AtividadeDTO.class);
    }
}
