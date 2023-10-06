package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.CategoriaDTO;
import br.com.desbugando.registroatividades.dto.CategoriaPatchDTO;
import br.com.desbugando.registroatividades.model.Categoria;
import br.com.desbugando.registroatividades.repository.CategoriaRepository;
import br.com.desbugando.registroatividades.service.exception.DataBaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
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

    public String[] inserir(CategoriaDTO categoriaDTO) {
        String[] alertaResultado;

        try {
            repository.save(mapper.map(categoriaDTO, Categoria.class));
            alertaResultado = new String[] {"mensagemSucesso", "Categoria cadastrada com sucesso!"};
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getLocalizedMessage());
        }

        return alertaResultado;
    }
}
