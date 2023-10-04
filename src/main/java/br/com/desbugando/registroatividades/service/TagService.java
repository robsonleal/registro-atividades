package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.TagDTO;
import br.com.desbugando.registroatividades.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {
    private TagRepository repository;
    private final ModelMapper mapper;

    public List<TagDTO> buscarTodas() {
        return repository.findAll().stream().map(tag -> mapper.map(tag, TagDTO.class)).toList();
    }
}
