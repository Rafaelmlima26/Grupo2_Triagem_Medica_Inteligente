package br.com.faculdadedonaduzzi.triagem_medica.dto;

// DTO que representa a resposta que vem do servico de ML em python
public record MlResponse(
    String prioridade,
    String descricao,
    int codigo
) {}
