package br.com.desbugando.registroatividades.config;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.dto.MovimentoDTO;
import br.com.desbugando.registroatividades.model.Atividade;
import br.com.desbugando.registroatividades.model.Categoria;
import br.com.desbugando.registroatividades.model.Movimento;
import br.com.desbugando.registroatividades.model.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class MapperConfig {

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
            mapper.using(context -> new Categoria(context.getSource().toString()))
                .map(AtividadeDTO::getCategoria, Atividade::setCategoria);
            mapper.using(context -> {
                Set<String> sourceTags = (Set<String>) context.getSource();
                return sourceTags.stream().map(Tag::new).collect(Collectors.toSet());
            }).map(AtividadeDTO::getTags, Atividade::setTags);
        });

        TypeMap<Movimento, MovimentoDTO> movimentoToDTOTypeMap =
            modelMapper.createTypeMap(Movimento.class, MovimentoDTO.class);
        TypeMap<MovimentoDTO, Movimento> dTOToMovimentoTypeMap =
            modelMapper.createTypeMap(MovimentoDTO.class, Movimento.class);

        return modelMapper;
    }
}
