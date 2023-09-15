package br.com.desbugando.registroatividades.repository;

import br.com.desbugando.registroatividades.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNome(String nome);
    Optional<Categoria> findByNome(String nome);
}
