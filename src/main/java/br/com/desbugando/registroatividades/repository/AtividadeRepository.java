package br.com.desbugando.registroatividades.repository;

import br.com.desbugando.registroatividades.model.Atividade;
import br.com.desbugando.registroatividades.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    List<Atividade> findByEstadoNot(Estado estado);
    List<Atividade> findByEstadoAndAtualizadoEmBetween(Estado estado, Instant dataInicial, Instant dataFinal);
}
