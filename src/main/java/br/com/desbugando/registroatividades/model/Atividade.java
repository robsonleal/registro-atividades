package br.com.desbugando.registroatividades.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TB_ATIVIDADES")
@EntityListeners(AuditingEntityListener.class)
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OID_ATIVIDADE")
    private Long id;

    @Column(name = "TXT_TITULO", unique = true)
    private String titulo;

    @Column(name = "TXT_DESCRICAO")
    private String descricao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OID_CATEGORIA")
    private Categoria categoria;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_ATIVIDADES_TAGS",
        joinColumns = @JoinColumn(name = "OID_ATIVIDADE"),
        inverseJoinColumns = @JoinColumn(name = "OID_TAG"))
    private Set<Tag> tags;

    @Column(name = "TIPO_ESTADO")
    private Estado estado;

    @CreatedDate
    @Column(name = "DAT_CRIACAO", nullable = false, updatable = false)
    private Instant criadoEm;

    @LastModifiedDate
    @Column(name = "DAT_ATUALIZACAO", nullable = false)
    private Instant atualizadoEm;

    @OneToMany
    @JoinColumn(name = "OID_ATIVIDADE")
    private Set<Movimento> movimentos;
}
