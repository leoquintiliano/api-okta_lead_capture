package br.com.leadersofts.leadcapture.utils;

import br.com.leadersofts.leadcapture.lead.record.LeadRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UtilMethods {
    public List<LocalDate> getConvertedAndFormattedDates(String... date) {
        return Stream.of(date).map(dateValue -> {
            if(Objects.nonNull(dateValue) && !dateValue.equals("-")) {
//                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                var contextParamsDate = LocalDate.parse(dateValue.replace("-","/"),dateTimeFormatter);
                return contextParamsDate;
            }
            return LocalDate.now();
        }).collect(Collectors.toList());
    }

    public LeadRecord verifyEmptyStringsAndConvertToNull(String nome, String primeiroContato, String ultimoContato, String dataNascimento, String celular, String telefone, String uf, String cidade, String carroInteresse1, String carroInteresse2, String carroInteresse3, String carroAtual1, String carroAtual2, String carroAtual3, String vendedor, String status, String opcaoVeiculo, String observacoes) {
        nome = nome.equals("-") ? null : nome;
        primeiroContato = primeiroContato.equals("-") ? null : primeiroContato;
        ultimoContato = ultimoContato.equals("-") ? null : ultimoContato;
        dataNascimento = dataNascimento.equals("-") ? null : dataNascimento;
        celular = celular.equals("-") ? null : celular;
        telefone = telefone.equals("-") ? null : telefone;
        uf = uf.equals("-") ? null : uf;
        cidade = cidade.equals("-") ? null : cidade;
        carroInteresse1 = carroInteresse1.equals("-") ? null : carroInteresse1;
        carroInteresse2 = carroInteresse2.equals("-") ? null : carroInteresse2;
        carroInteresse3 = carroInteresse3.equals("-") ? null : carroInteresse3;
        carroAtual1 = carroAtual1.equals("-") ? null : carroAtual1;
        carroAtual2 = carroAtual2.equals("-") ? null : carroAtual2;
        carroAtual3 = carroAtual3.equals("-") ? null : carroAtual3;
        vendedor = vendedor.equals("-") ? null : vendedor;
        status = status.equals("-") ? null : status;
        opcaoVeiculo = opcaoVeiculo.equals("-") ? null : opcaoVeiculo;
        observacoes = observacoes.equals("-") ? null : observacoes;

        return new LeadRecord(0L,nome,primeiroContato,ultimoContato,null,celular,telefone,uf,cidade,carroInteresse1,carroInteresse2,carroInteresse3,carroAtual1,carroAtual2,carroAtual3,vendedor,status,opcaoVeiculo,observacoes);

    }


    public LocalDate getConvertedDateToString(String dataNascimento) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var contextParamsDate = LocalDate.parse(dataNascimento.replace("-","/"),dateTimeFormatter);
        return contextParamsDate;
    }

    public LocalDate getStandardFormattedDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var contextParamsDate = LocalDate.parse("".replace("-","/"),dateTimeFormatter);
        return contextParamsDate;
    }

}
