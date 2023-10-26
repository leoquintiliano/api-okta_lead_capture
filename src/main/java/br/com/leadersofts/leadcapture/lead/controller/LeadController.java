package br.com.leadersofts.leadcapture.lead.controller;

import br.com.leadersofts.leadcapture.lead.record.LeadRecord;
import br.com.leadersofts.leadcapture.lead.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("lead")
public class LeadController {

    @Autowired
    private LeadService leadService;

    @PostMapping("save")
    public ResponseEntity<LeadRecord> save (@RequestBody LeadRecord leadRecord) {
        var persistedLead =  this.leadService.save(leadRecord);
        return ResponseEntity.ok(persistedLead);
    }

    @GetMapping("/list")
    public ResponseEntity<List<LeadRecord>> list() {
        return new ResponseEntity(this.leadService.findAll(), HttpStatus.OK);
    }

    @GetMapping("findFiltered/nome/primeiroContato/ultimoContato/dataNascimento/celular/telefone/uf/cidade/carroInteresse1/carroInteresse2/carroInteresse3/carroAtual1/carroAtual2/carroAtual3/vendedor/status/opcaoVeiculo/observacoes" +
            "/{nome}/{primeiroContato}/{ultimoContato}/{dataNascimento}/{celular}/{telefone}/{uf}/{cidade}/{carroInteresse1}/{carroInteresse2}/{carroInteresse3}/{carroAtual1}/{carroAtual2}/{carroAtual3}/{vendedor}/{status}/{opcaoVeiculo}/{observacoes}")
    public ResponseEntity<List<LeadRecord>> findWithFilter(@PathVariable("nome") String nome, @PathVariable("primeiroContato") String primeiroContato, @PathVariable("ultimoContato") String ultimoContato,
                                                           @PathVariable("dataNascimento") String dataNascimento, @PathVariable("celular") String celular,@PathVariable("telefone") String telefone,
                                                           @PathVariable("uf") String uf, @PathVariable("cidade") String cidade,
                                                           @PathVariable("carroInteresse1") String carroInteresse1, @PathVariable("carroInteresse2") String carroInteresse2, @PathVariable("carroInteresse3") String carroInteresse3,
                                                           @PathVariable("carroAtual1") String carroAtual1, @PathVariable("carroAtual2") String carroAtual2, @PathVariable("carroAtual3") String carroAtual3,
                                                           @PathVariable("vendedor") String vendedor, @PathVariable("status") String status, @PathVariable("opcaoVeiculo") String opcaoVeiculo, @PathVariable("observacoes") String observacoes) {

        List<LeadRecord> response = new ArrayList<>();
        try {
            response = this.leadService.prepareParametersToFilerQuery(nome,primeiroContato,ultimoContato,dataNascimento,celular,telefone,uf,cidade,carroInteresse1,carroInteresse2,carroInteresse3,
                    carroAtual1,carroAtual2,carroAtual3,vendedor,status,opcaoVeiculo,observacoes);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/findAfterFiltered/{filteredIds}")
    public ResponseEntity<?> findAllById(@PathVariable("filteredIds") List<Long> filteredIds) {
        var lead = this.leadService.findByIds(filteredIds);
        return new ResponseEntity<>(lead,HttpStatus.OK);
    }

    @GetMapping("/findByName/{nome}")
    public ResponseEntity<?> findByName(@PathVariable("nome") String nome) {
        var lead = this.leadService.findByName(nome);
        return new ResponseEntity<>(lead,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.leadService.delete(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LeadRecord> update(@PathVariable("id") Long id, @RequestBody LeadRecord leadRecord) {
        var storage = this.leadService.getLead(leadRecord);
        var response = this.leadService.update(storage);
        return ResponseEntity.ok(response);
    }

}
