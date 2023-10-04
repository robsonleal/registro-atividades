package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.CategoriaDTO;
import br.com.desbugando.registroatividades.repository.CategoriaRepository;
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
        return repository.findAll().stream().map(categoria -> mapper.map(categoria, CategoriaDTO.class)).toList();
    }
}
