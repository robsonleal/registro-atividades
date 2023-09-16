package br.com.desbugando.registroatividades.repository;

import br.com.desbugando.registroatividades.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNome(String nome);
    Optional<Categoria> findByNome(String nome);
}
