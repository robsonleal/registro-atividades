package br.com.desbugando.registroatividades.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_MOVIMENTOS")
public class Movimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OID_MOVIMENTO")
    private Long id;

    @Column(name = "TXT_DESCRICAO")
    private String descricao;

    @Column(name = "DAT_INICIO")
    private Instant registroInicio;

    @Column(name = "DAT_FIM")
    private Instant registroFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OID_ATIVIDADE")
    private Atividade atividade;
}
