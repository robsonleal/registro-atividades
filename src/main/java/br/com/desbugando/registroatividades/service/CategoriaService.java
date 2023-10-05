package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.CategoriaDTO;
import br.com.desbugando.registroatividades.dto.CategoriaPatchDTO;
import br.com.desbugando.registroatividades.model.Categoria;
import br.com.desbugando.registroatividades.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {
    private CategoriaRepository repository;
    private final ModelMapper mapper;

    public List<CategoriaDTO> buscarTodas() {
        return repository.findAll().stream()
            .map(categoria -> mapper.map(categoria, CategoriaDTO.class)).toList();
    }

    public CategoriaDTO buscarPorId(long id) {
        return mapper.map(repository.findById(id), CategoriaDTO.class);
    }

    public void patchNome(CategoriaPatchDTO categoriaPatchDTO, long id) {
        Categoria categoria = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada!"));
        categoria.setNome(categoriaPatchDTO.getNome());
        repository.save(categoria);
    }
}
