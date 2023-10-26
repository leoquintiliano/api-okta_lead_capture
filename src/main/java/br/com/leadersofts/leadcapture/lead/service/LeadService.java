package br.com.leadersofts.leadcapture.lead.service;

import br.com.leadersofts.leadcapture.lead.converter.LeadConverter;
import br.com.leadersofts.leadcapture.lead.domain.Lead;
import br.com.leadersofts.leadcapture.lead.record.LeadRecord;
import br.com.leadersofts.leadcapture.lead.repository.LeadQueryRepository;
import br.com.leadersofts.leadcapture.lead.repository.LeadRepository;
import br.com.leadersofts.leadcapture.utils.UtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private LeadConverter mapper;

    @Autowired
    private LeadQueryRepository customRepository;

    @Autowired
    private UtilMethods utilMethods;

    public LeadRecord save(LeadRecord leadDTO) {
        var entity = this.mapper.getLead(leadDTO);
        var response = this.leadRepository.save(entity);
        return this.mapper.getLeadRecord(response);
    }

    public List<LeadRecord> findAll() {
        var leads = this.leadRepository.findAll();
        List<LeadRecord> leadRecordList = new ArrayList<>();
        leads.stream().map( accumulator -> leadRecordList.add(this.mapper.getLeadRecord(accumulator))).collect(Collectors.toList());
        return Objects.isNull(leadRecordList) ? new ArrayList<>() : leadRecordList;
    }

    public Optional<List<LeadRecord>> findFilteredLead(String nome, LocalDate primeiroContato,LocalDate ultimoContato, LocalDate dataNascimento,String celular,String telefone,
                                                       String uf,String cidade,String carroInteresse1,String carroInteresse2,String carroInteresse3,
                                                       String carroAtual1, String carroAtual2, String carroAtual3,String vendedor,String status,String opcaoVeiculo, String observacoes) {

        List<LeadRecord> leadRecords;

        var response = this.customRepository.customQuery(nome,primeiroContato,ultimoContato,dataNascimento,celular,telefone,uf,cidade,
                carroInteresse1,carroInteresse2,carroInteresse3,carroAtual1,carroAtual2,carroAtual3,vendedor,status,opcaoVeiculo,observacoes);

        leadRecords = response.stream().map(LeadConverter::getLeadRecord).collect(Collectors.toList());

        return Optional.of(leadRecords);
    }

    public List<LeadRecord> prepareParametersToFilerQuery(String nome, String primeiroContato, String ultimoContato, String dataNascimento, String celular, String telefone, String uf, String cidade, String carroInteresse1, String carroInteresse2, String carroInteresse3, String carroAtual1, String carroAtual2, String carroAtual3, String vendedor, String status, String opcaoVeiculo, String observacoes) throws ChangeSetPersister.NotFoundException {

        var treatedLeadDTO =  this.utilMethods.verifyEmptyStringsAndConvertToNull(nome,primeiroContato,ultimoContato,dataNascimento,celular,telefone,uf,cidade,carroInteresse1,carroInteresse2,carroInteresse3,
                carroAtual1,carroAtual2,carroAtual3,vendedor,status,opcaoVeiculo,observacoes);

        var birthDate =  dataNascimento.contains("/") && dataNascimento.length() > 1 ? this.utilMethods.getConvertedDateToString(dataNascimento) : null;

        var dateCollection = birthDate != null ? this.utilMethods.getConvertedAndFormattedDates(treatedLeadDTO.primeiroContato(),treatedLeadDTO.ultimoContato(), (Objects.nonNull(treatedLeadDTO.dataNascimento()) ? treatedLeadDTO.dataNascimento().toString() : birthDate.toString())) :
                this.utilMethods.getConvertedAndFormattedDates(treatedLeadDTO.primeiroContato(),treatedLeadDTO.ultimoContato());

        var primeiroContatoDate = dateCollection.size() > 0 && Objects.nonNull(dateCollection.get(0)) ? dateCollection.get(0) : LocalDate.now();
        var ultimoContatoDate = dateCollection.size() > 1 && Objects.nonNull(dateCollection.get(1)) ? dateCollection.get(1) : LocalDate.now();
        var dataNascimentoDate = dateCollection.size() > 2 && Objects.nonNull(dateCollection.get(2)) ? dateCollection.get(2) : LocalDate.now();

        primeiroContatoDate = primeiroContato.equals("-") ? null : primeiroContatoDate;
        ultimoContatoDate = ultimoContato.equals("-") ? null : ultimoContatoDate;
        dataNascimentoDate = dataNascimento.equals("-") ? null : dataNascimentoDate;

        var response = this.findFilteredLead(treatedLeadDTO.nome() ,primeiroContatoDate,ultimoContatoDate,dataNascimentoDate,treatedLeadDTO.celular(),treatedLeadDTO.telefone(),treatedLeadDTO.uf(),treatedLeadDTO.cidade(),
                treatedLeadDTO.carroInteresse1(),treatedLeadDTO.carroInteresse2(),treatedLeadDTO.carroInteresse3(),
                treatedLeadDTO.carroAtual1(),treatedLeadDTO.carroAtual2(),treatedLeadDTO.carroAtual3(),treatedLeadDTO.vendedor(),treatedLeadDTO.status(),treatedLeadDTO.opcaoVeiculo(), treatedLeadDTO.observacoes()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        return response;

    }


    public List<LeadRecord> findByName(String nome) {
//        var lead = this.leadRepository.findByNome(nome).orElseThrow( () -> new RuntimeException("None lead could be found!") );
        var leadList = this.leadRepository.findByNome(nome);

        List<LeadRecord> leadRecords = new ArrayList<>();
        leadRecords = leadList.stream().map(LeadConverter::getLeadRecord).collect(Collectors.toList());

        return leadRecords;
    }

    public void delete(Long id) {
        var leadToBeDeleted = this.leadRepository.findById(id).orElseThrow( () -> new IllegalStateException("None [Lead] was found!") );
        this.leadRepository.delete(leadToBeDeleted);
    }

    public Lead getLead(LeadRecord leadRecord) {
        return this.mapper.getLead(leadRecord);
    }

    public LeadRecord update(Lead lead) {
        var leadToUpdate = this.leadRepository.findById(lead.getId()).orElseThrow( () -> new RuntimeException("Não foi possível atualizar os dados do Lead para o item atual") );
        leadToUpdate.setId(lead.getId());
        leadToUpdate.setCelular(lead.getCelular());
        leadToUpdate.setTelefone(lead.getTelefone());
        leadToUpdate.setCarroInteresse1(lead.getCarroInteresse1());
        leadToUpdate.setUf(lead.getUf());
        leadToUpdate.setCidade(lead.getCidade());
        var res = this.leadRepository.save(leadToUpdate);
        return LeadConverter.getLeadRecord(res);
    }

    public List<LeadRecord> findByIds(List<Long> filteredIds) {
        var response = this.leadRepository.findAllById(filteredIds);
        return response.stream().map(LeadConverter::getLeadRecord).collect(Collectors.toList());
    }
}
