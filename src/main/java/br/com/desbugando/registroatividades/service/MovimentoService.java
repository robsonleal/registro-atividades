package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.MovimentoDTO;
import br.com.desbugando.registroatividades.model.Atividade;
import br.com.desbugando.registroatividades.model.Movimento;
import br.com.desbugando.registroatividades.repository.AtividadeRepository;
import br.com.desbugando.registroatividades.repository.MovimentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class MovimentoService {
    private MovimentoRepository movimentoRepository;
    private AtividadeRepository atividadeRepository;
    private ModelMapper mapper;

    public MovimentoDTO criar(long id, MovimentoDTO movimentoDTO) {
        Atividade atividade = atividadeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada!"));
        movimentoDTO.setRegistroFim(Instant.now());
        Movimento movimento = mapper.map(movimentoDTO, Movimento.class);
        movimento.setAtividade(atividade);
        movimento = movimentoRepository.save(movimento);

        return mapper.map(movimento, MovimentoDTO.class);
    }
}
