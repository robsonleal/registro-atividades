package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.MovimentoDTO;
import br.com.desbugando.registroatividades.dto.MovimentoPatchDTO;
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

    public String[] criar(long id, MovimentoDTO movimentoDTO) {
        Atividade atividade = atividadeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada!"));
        movimentoDTO.setRegistroFim(Instant.now());
        Movimento movimento = mapper.map(movimentoDTO, Movimento.class);
        movimento.setAtividade(atividade);
        movimentoRepository.save(movimento);

        return new String[] {"mensagemSucesso", "Movimento criado com sucesso!"};
    }

    public MovimentoDTO patchDescricao(MovimentoPatchDTO movimentoPatchDTO, long id) {
        Movimento movimento = movimentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Movimento não encontrado!"));

        if (movimento.getDescricao() != null)
            movimento.setDescricao(movimentoPatchDTO.getDescricao());

        return mapper.map(movimentoRepository.save(movimento), MovimentoDTO.class);
    }
}
