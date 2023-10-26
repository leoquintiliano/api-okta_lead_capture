package br.com.leadersofts.leadcapture.lead.repository;

import br.com.leadersofts.leadcapture.lead.domain.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

    @Query("SELECT l FROM Lead l WHERE l.nome LIKE %:nome%")
    List<Lead> findByNome(@Param("nome") String nome);

    @Query("SELECT l FROM Lead l WHERE l.id = :id")
    Optional<Lead> findById(@Param("id") Long id);

}
