package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.TagDTO;
import br.com.desbugando.registroatividades.dto.TagPatchDTO;
import br.com.desbugando.registroatividades.model.Tag;
import br.com.desbugando.registroatividades.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public TagDTO buscarPorId(Long id) {
        return mapper.map(repository.findById(id), TagDTO.class);
    }

    public void patchNome(TagPatchDTO tagPatchDTO, long id) {
        Tag tag = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tag não encontrada"));

        if (tag.getNome() != null) {
            tag.setNome(tagPatchDTO.getNome());
        }

        repository.save(tag);
    }
}
