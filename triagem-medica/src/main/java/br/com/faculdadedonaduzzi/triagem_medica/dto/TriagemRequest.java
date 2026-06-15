package br.com.faculdadedonaduzzi.triagem_medica.dto;

// DTO pra receber os dados do paciente na requisicao
// separado da entidade pra nao expor campos internos tipo id e data
public record TriagemRequest(
    String nomePaciente,
    int idade,
    double temperatura,
    double freqCardiaca,
    double pressaoSistolica,
    double saturacaoO2,
    int nivelDor
) {}
