package br.com.leadersofts.leadcapture.lead.record;

import java.time.LocalDate;

public record LeadRecord (
        Long id,String nome,String primeiroContato,String ultimoContato,String dataNascimento, String celular,
        String telefone,String uf,String cidade,String carroInteresse1,String carroInteresse2,String carroInteresse3,
        String carroAtual1,String carroAtual2,String carroAtual3,String vendedor,String status,String opcaoVeiculo, String observacoes

) {

}
