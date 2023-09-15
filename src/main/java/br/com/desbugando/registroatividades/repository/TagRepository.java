package br.com.desbugando.registroatividades.repository;

import br.com.desbugando.registroatividades.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByNome(String nome);
}
