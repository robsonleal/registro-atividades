package br.com.desbugando.registroatividades.service;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.model.Atividade;
import br.com.desbugando.registroatividades.model.Estado;
import br.com.desbugando.registroatividades.repository.AtividadeRepository;
import br.com.desbugando.registroatividades.service.exception.DataBaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class AtividadeService {

    private final AtividadeRepository repository;
    private final ModelMapper mapper;
    private PDFReportGenerator pdfReportGenerator;

    public List<AtividadeDTO> getNaoConcluidas() {
        List<Atividade> atividades = repository.findByEstadoNot(Estado.CONCLUIDO);

        return atividades.stream().map(atividade -> mapper.map(atividade, AtividadeDTO.class))
            .toList();
    }

    @Transactional
    public String[] insert(AtividadeDTO dto) {
        dto.setEstado(Estado.ATIVO);

        String[] alertaResultado;

        try {
            repository.save(mapper.map(dto, Atividade.class));
            alertaResultado = new String[] {"mensagemSucesso", "Atividade cadastrada com sucesso!"};
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getLocalizedMessage());
        }

        return alertaResultado;
    }

    public List<AtividadeDTO> getConcluidasNoPeriodo(Instant dataInicial, Instant dataFinal) {
        List<Atividade> atividades =
            repository.findByEstadoAndAtualizadoEmBetween(Estado.CONCLUIDO, dataInicial, dataFinal);

        return atividades.stream().map(atividade -> mapper.map(atividade, AtividadeDTO.class))
            .toList();
    }

    public AtividadeDTO findById(Long id) {
        return mapper.map(repository.findById(id), AtividadeDTO.class);
    }

    @Transactional
    public void concluir(Long id) {
        Atividade atividade = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada!"));

        atividade.setEstado(Estado.CONCLUIDO);
        repository.save(atividade);
    }

    @Transactional
    public void ativar(Long id) {
        Atividade atividade = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada!"));

        atividade.setEstado(Estado.ATIVO);
        repository.save(atividade);
    }

    @Transactional
    public AtividadeDTO editar(AtividadeDTO atividadeDTO) {
        Atividade model;

        try {
            model = repository.save(mapper.map(atividadeDTO, Atividade.class));
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }

        return mapper.map(model, AtividadeDTO.class);
    }

    public List<AtividadeDTO> getPorCategoriaId(Long id) {
        List<Atividade> atividades = repository.findByCategoria_Id(id);

        return atividades.stream().map(atividade -> mapper.map(atividade, AtividadeDTO.class))
            .toList();
    }

    public List<AtividadeDTO> getPorTagId(Long id) {
        List<Atividade> atividades = repository.findByTagsId(id);

        return atividades.stream().map(atividade -> mapper.map(atividade, AtividadeDTO.class))
            .toList();
    }

    public byte[] gerarRelatorioAtividadesDeXDias(int peridoEmDias) {
        Instant periodo = Instant.now().minus(peridoEmDias, ChronoUnit.DAYS);

        List<AtividadeDTO> atividades = repository.findByCriadoEmAfterAndEstadoNot(periodo, Estado.ATIVO)
            .stream().map(atividade -> mapper.map(atividade, AtividadeDTO.class)).toList();

        return pdfReportGenerator.generateAtividadesReport(atividades,
            String.format("Últimos %d dias", peridoEmDias));
    }
}
