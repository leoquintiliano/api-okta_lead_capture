package br.com.leadersofts.leadcapture.lead.repository;

import br.com.leadersofts.leadcapture.lead.domain.Lead;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class LeadQueryRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Lead> customQuery(String nome, LocalDate primeiroContato, LocalDate ultimoContato, LocalDate dataNascimento,
                                  String celular, String telefone, String uf,
                                  String cidade, String carroInteresse1, String carroInteresse2, String carroInteresse3,
                                  String carroAtual1, String carroAtual2, String carroAtual3, String vendedor, String status,
                                  String opcaoVeiculo, String observacoes) {

        StringBuilder query = new StringBuilder("FROM Lead L ");

        var clause = this.getClause(nome,primeiroContato,ultimoContato,dataNascimento,celular,telefone,uf,cidade,carroInteresse1,carroInteresse2,
                    carroInteresse3,carroAtual1,carroAtual2,carroAtual3,vendedor,status,opcaoVeiculo,observacoes
        );

        if(!clause.isBlank() && clause.equals(" WHERE ")) {
            query.append(clause);
            this.appendBasicLeadFields(nome,primeiroContato,ultimoContato,dataNascimento,celular,telefone,uf,cidade,carroInteresse1,carroInteresse2,
                    carroInteresse3,carroAtual1,carroAtual2,carroAtual3,vendedor,status,opcaoVeiculo,observacoes,query);
       }

        TypedQuery<Lead> typedQuery = em.createQuery(query.toString(), Lead.class);
        this.setBasicParams(nome,primeiroContato,ultimoContato,dataNascimento,celular,telefone,uf,cidade,carroInteresse1,carroInteresse2,
                carroInteresse3,carroAtual1,carroAtual2,carroAtual3,vendedor,status,opcaoVeiculo,observacoes,typedQuery);

        return typedQuery.getResultList();
    }

    private String getClause(String nome, LocalDate primeiroContato, LocalDate ultimoContato, LocalDate dataNascimento,
                             String celular, String telefone, String uf,
                             String cidade, String carroInteresse1, String carroInteresse2, String carroInteresse3,
                             String carroAtual1, String carroAtual2, String carroAtual3, String vendedor, String status,
                             String opcaoVeiculo, String observacoes) {

        return  (Objects.nonNull(nome) || Objects.nonNull(primeiroContato) || Objects.nonNull(ultimoContato) || Objects.nonNull(dataNascimento) || Objects.nonNull(celular) ||
                Objects.nonNull(telefone) || Objects.nonNull(uf) || Objects.nonNull(cidade) || Objects.nonNull(carroInteresse1) || Objects.nonNull(carroInteresse2) ||
                Objects.nonNull(carroInteresse3) || Objects.nonNull(carroAtual1) || Objects.nonNull(carroAtual2) || Objects.nonNull(carroAtual3) ||
                Objects.nonNull(vendedor) || Objects.nonNull(status) || Objects.nonNull(opcaoVeiculo) || Objects.nonNull(observacoes)
        ) ? " WHERE " : " ";

    }


    private void appendBasicLeadFields(String nome, LocalDate primeiroContato, LocalDate ultimoContato, LocalDate dataNascimento,
                                       String celular, String telefone, String uf,
                                       String cidade, String carroInteresse1, String carroInteresse2, String carroInteresse3,
                                       String carroAtual1, String carroAtual2, String carroAtual3, String vendedor, String status,
                                       String opcaoVeiculo,String observacoes, StringBuilder query) {

        if(nome != null) {
            var nameField = query.toString().contains(" WHERE ") ? " L.nome LIKE :nome  " : " L.nome LIKE :nome ";
            query.append(nameField);
        }
        if(primeiroContato != null) {
            var contact = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.primeiroContato = :primeiroContato  " : " L.primeiroContato = :primeiroContato ";
            query.append(contact);
        }
        if(ultimoContato != null) {
            var contact = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query))  ? " AND L.ultimoContato = :ultimoContato " : " L.ultimoContato = :ultimoContato ";
            query.append(contact);
        }
        if(dataNascimento != null) {
            var birthDate = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.dataNascimento = :dataNascimento " : " L.ultimoContato = :ultimoContato ";
            query.append(birthDate);
        }
        if(celular != null) {
            var contactNumber = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.celular LIKE %:celular% " : " L.celular LIKE %:celular% ";
            query.append(contactNumber);
        }
        if(telefone != null) {
            var contactNumber = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.telefone LIKE %:telefone% " : " L.telefone LIKE %:telefone% ";
            query.append(contactNumber);
        }
        if(uf != null) {
            var contactNumber = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.uf = :uf " : " L.uf = :uf ";
            query.append(contactNumber);
        }
        if(cidade != null) {
            var city = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.cidade LIKE %:cidade% " : " L.cidade = %:cidade% ";
            query.append(city);
        }
        if(carroInteresse1 != null) {
            var car = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.carroInteresse1 LIKE %:carroInteresse1% " : " L.carroInteresse1 = %:carroInteresse1% ";
            query.append(car);
        }

        if(carroAtual1 != null) {
            var car = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.carroAtual1 LIKE %:carroAtual1% " : " L.carroAtual1 LIKE %:carroAtual1% ";
            query.append(car);
        }
        if(vendedor != null) {
            var seller = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.vendedor LIKE %:vendedor% " : " L.vendedor = %:vendedor% ";
            query.append(seller);
        }
        if(opcaoVeiculo != null) {
            var option = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.opcaoVeiculo LIKE %:opcaoVeiculo% " : " L.opcaoVeiculo LIKE %:opcaoVeiculo% ";
            query.append(option);
        }
        if(observacoes != null) {
            var notes = (this.hasNameClause(query)) || (!this.doesItHaveAtLeastOneClause(query)) ? " AND L.observacoes LIKE %:observacoes% " : " L.observacoes LIKE %:observacoes% ";
            query.append(notes);
        }

    }

    private boolean doesItHaveAtLeastOneClause(StringBuilder query) {
        return query.toString().endsWith("WHERE ");
    }

    private boolean hasNameClause(StringBuilder query) {
        return (query.toString().contains(" WHERE ") && query.toString().contains(" L.nome = :nome  "));
    }


    private static void setBasicParams(String nome, LocalDate primeiroContato, LocalDate ultimoContato, LocalDate dataNascimento,
                                       String celular, String telefone, String uf,
                                       String cidade, String carroInteresse1, String carroInteresse2, String carroInteresse3,
                                       String carroAtual1, String carroAtual2, String carroAtual3, String vendedor, String status,
                                       String opcaoVeiculo,String observacoes, TypedQuery<Lead> typedQuery) {
        if(nome != null) {
            typedQuery.setParameter("nome", "%".concat(nome).concat("%"));
        }
        if(primeiroContato != null) {
            typedQuery.setParameter("primeiroContato", primeiroContato.toString());
        }
        if(ultimoContato != null) {
            typedQuery.setParameter("ultimoContato", ultimoContato.toString());
        }
        if(dataNascimento != null) {
            typedQuery.setParameter("dataNascimento", dataNascimento);
        }
        if(celular != null) {
            typedQuery.setParameter("celular", celular);
        }
        if(telefone != null) {
            typedQuery.setParameter("telefone", telefone);
        }
        if(uf != null) {
            typedQuery.setParameter("uf", uf);
        }
        if(cidade != null) {
            typedQuery.setParameter("cidade", cidade);
        }
        if(carroInteresse1 != null) {
            typedQuery.setParameter("carroInteresse1", carroInteresse1);
        }
        if(carroAtual1 != null) {
            typedQuery.setParameter("carroAtual1", carroAtual1);
        }
        if(vendedor != null) {
            typedQuery.setParameter("vendedor", vendedor);
        }
        if(status != null) {
            typedQuery.setParameter("status", status);
        }
        if(opcaoVeiculo != null) {
            typedQuery.setParameter("opcaoVeiculo", opcaoVeiculo);
        }
        if(observacoes != null) {
            typedQuery.setParameter("observacoes", observacoes);
        }

    }


}
