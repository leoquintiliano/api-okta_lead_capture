package br.com.leadersofts.leadcapture.lead.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@Builder
public class Lead {

    @Id
    @SequenceGenerator(name = "seq_lead", sequenceName = "seq_lead", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lead")
    @Column(name = "id", nullable = false)
    private Long id;

    private String nome;

    private String primeiroContato;

    private String ultimoContato;

    private String dataNascimento;

    private String celular;

    private String telefone;

    private String uf;

    private String cidade;

    private String carroInteresse1;

    private String carroInteresse2;

    private String carroInteresse3;

    private String carroAtual1;

    private String carroAtual2;

    private String carroAtual3;

    private String vendedor;

    private String status;

    private String opcaoVeiculo; // TODOS, 0KM, SEMI-NOVO

    private String observacoes;

}


