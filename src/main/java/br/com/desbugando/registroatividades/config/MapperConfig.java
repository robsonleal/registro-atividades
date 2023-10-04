package br.com.desbugando.registroatividades.config;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.dto.CategoriaDTO;
import br.com.desbugando.registroatividades.dto.MovimentoDTO;
import br.com.desbugando.registroatividades.dto.TagDTO;
import br.com.desbugando.registroatividades.model.Atividade;
import br.com.desbugando.registroatividades.model.Categoria;
import br.com.desbugando.registroatividades.model.Movimento;
import br.com.desbugando.registroatividades.model.Tag;
import br.com.desbugando.registroatividades.repository.CategoriaRepository;
import br.com.desbugando.registroatividades.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class MapperConfig {

    private final CategoriaRepository categoriaRepository;
    private final TagRepository tagRepository;

    @Bean
    @SuppressWarnings("unchecked")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<AtividadeDTO, Atividade> dTOToAtividadeTypeMap =
            modelMapper.createTypeMap(AtividadeDTO.class, Atividade.class);

        TypeMap<Atividade, AtividadeDTO> atividadeToDTOTypeMap =
            modelMapper.createTypeMap(Atividade.class, AtividadeDTO.class);

        atividadeToDTOTypeMap.addMappings(mapper -> {
            mapper.<String>map(source -> source.getCategoria().getNome(),
                AtividadeDTO::setCategoria);
            mapper.using(context -> {
                Set<Tag> sourceTags = (Set<Tag>) context.getSource();
                return sourceTags != null ?
                    sourceTags.stream().map(Tag::getNome).collect(Collectors.toSet()) :
                    new HashSet<>();
            }).map(Atividade::getTags, AtividadeDTO::setTags);
        });

        dTOToAtividadeTypeMap.addMappings(mapper -> {
            mapper.using(context -> {
                String categoriaNome = context.getSource().toString();
                return categoriaRepository.findByNome(categoriaNome)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
            }).map(AtividadeDTO::getCategoria, Atividade::setCategoria);

            mapper.using(context -> {
                Set<String> sourceTags = (Set<String>) context.getSource();
                return sourceTags.stream().map(nome -> tagRepository.findByNome(nome)
                        .orElseThrow(() -> new EntityNotFoundException("Tag não encontrada")))
                    .collect(Collectors.toSet());
            }).map(AtividadeDTO::getTags, Atividade::setTags);
        });

        TypeMap<Movimento, MovimentoDTO> movimentoToDTOTypeMap =
            modelMapper.createTypeMap(Movimento.class, MovimentoDTO.class);
        TypeMap<MovimentoDTO, Movimento> dTOToMovimentoTypeMap =
            modelMapper.createTypeMap(MovimentoDTO.class, Movimento.class);

        TypeMap<Tag, TagDTO> tagToDTOTypeMap =
            modelMapper.createTypeMap(Tag.class, TagDTO.class);
        TypeMap<TagDTO, Tag> dTOToTagTypeMap =
            modelMapper.createTypeMap(TagDTO.class, Tag.class);

        TypeMap<Categoria, CategoriaDTO> categoriaToDTOTypeMap =
            modelMapper.createTypeMap(Categoria.class, CategoriaDTO.class);
        TypeMap<CategoriaDTO, Categoria> dTOToCategoriaTypeMap =
            modelMapper.createTypeMap(CategoriaDTO.class, Categoria.class);

        return modelMapper;
    }
}
