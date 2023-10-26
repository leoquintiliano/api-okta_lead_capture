package br.com.leadersofts.leadcapture.lead.converter;

import br.com.leadersofts.leadcapture.lead.domain.Lead;
import br.com.leadersofts.leadcapture.lead.record.LeadRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class LeadConverter {

    public Lead getLead(LeadRecord leadRecord) {
        return Lead.builder()
                .id(Objects.nonNull(leadRecord.id()) ? leadRecord.id() : 0L)
                .nome(Objects.nonNull(leadRecord.nome()) ? leadRecord.nome() : "" )
                .telefone(Objects.nonNull(leadRecord.telefone()) ? leadRecord.telefone() : "")
                .dataNascimento(Objects.nonNull(leadRecord.dataNascimento()) ? leadRecord.dataNascimento() : "")
                .primeiroContato(Objects.nonNull(leadRecord.primeiroContato()) ? leadRecord.primeiroContato() : "")
                .ultimoContato(Objects.nonNull(leadRecord.ultimoContato()) ? leadRecord.ultimoContato() : "")
                .carroAtual1(Objects.nonNull(leadRecord.carroAtual1()) ? leadRecord.carroAtual1() : "")
                .carroAtual2(Objects.nonNull(leadRecord.carroAtual2()) ? leadRecord.carroAtual2() : "")
                .carroAtual3(Objects.nonNull(leadRecord.carroAtual3()) ? leadRecord.carroAtual3() : "")
                .carroInteresse1( Objects.nonNull(leadRecord.carroInteresse1()) ? leadRecord.carroInteresse1() : "")
                .carroInteresse2( Objects.nonNull(leadRecord.carroInteresse2()) ? leadRecord.carroInteresse2() : "")
                .carroInteresse3( Objects.nonNull(leadRecord.carroInteresse3()) ? leadRecord.carroInteresse3() : "")
                .celular( Objects.nonNull(leadRecord.celular()) ? leadRecord.celular() : "")
                .uf( Objects.nonNull(leadRecord.uf()) ? leadRecord.uf() : "")
                .cidade( Objects.nonNull(leadRecord.cidade()) ? leadRecord.cidade() : "")
                .vendedor( Objects.nonNull(leadRecord.vendedor()) ? leadRecord.vendedor() : "")
                .status( Objects.nonNull(leadRecord.status()) ? leadRecord.status() : "")
                .opcaoVeiculo(Objects.nonNull(leadRecord.opcaoVeiculo()) ? leadRecord.opcaoVeiculo() : "")
                .observacoes(Objects.nonNull(leadRecord.observacoes()) ? leadRecord.observacoes() : "")
                .build();
    }

    public static LeadRecord getLeadRecord(Lead lead) {

        var primeiroContato = formatDate(lead.getPrimeiroContato());
        var ultimoContato =   formatDate(lead.getUltimoContato());
        var dataNascimento = formatDate(lead.getDataNascimento());

        return new LeadRecord(lead.getId(), lead.getNome(),primeiroContato, ultimoContato,
                dataNascimento,lead.getCelular(), lead.getTelefone(),lead.getUf(),lead.getCidade(),
                lead.getCarroInteresse1(),lead.getCarroInteresse2(),lead.getCarroInteresse3(),
                lead.getCarroAtual1(),lead.getCarroAtual2(),lead.getCarroAtual3(),
                lead.getVendedor(),lead.getStatus(),lead.getOpcaoVeiculo(), lead.getObservacoes());
    }

    private static String formatDate(String date) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(Objects.nonNull(date) && (date.indexOf("-") == 2 || date.indexOf("-") == 5 || date.indexOf("/") == 2 || date.indexOf("-") == 5) ) {
            var year = date.substring(0,2);
            var month = date.substring(3,5);
            var day = date.substring(6,date.length());
            date = day.concat("-").concat(month).concat("-").concat(year).replace("-","/");
            return date;
        }
        if(Objects.nonNull(date) && date.length() > 4) { // 16-10-2023  2023-10-16
            var year = date.substring(0,4);
            var month = date.substring(5,7);
            var day = date.substring(8,date.length());
            date = day.concat("-").concat(month).concat("-").concat(year).replace("-","/");
            return date;
        }
        return "";
//        var contextParamsDate = LocalDate.parse(date.replace("-","/"),dateTimeFormatter); return contextParamsDate.toString();
    }

}
