package br.com.faculdadedonaduzzi.triagem_medica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

// DTO que representa os dados do paciente recebidos na requisição
// separado da entidade para não expor campos internos como id e dataTriagem
@Schema(description = "Dados do paciente para realizar a triagem")
public record TriagemRequest(

    @Schema(description = "Nome completo do paciente", example = "João Silva")
    @NotBlank(message = "Nome do paciente é obrigatório")
    String nomePaciente,

    @Schema(description = "Idade do paciente em anos", example = "45")
    @Min(value = 0, message = "Idade não pode ser negativa")
    @Max(value = 150, message = "Idade inválida")
    int idade,

    @Schema(description = "Temperatura corporal em °C", example = "38.5")
    @DecimalMin(value = "30.0", message = "Temperatura muito baixa")
    @DecimalMax(value = "45.0", message = "Temperatura muito alta")
    double temperatura,

    @Schema(description = "Frequência cardíaca em bpm", example = "90")
    @DecimalMin(value = "20.0", message = "Frequência cardíaca muito baixa")
    @DecimalMax(value = "250.0", message = "Frequência cardíaca muito alta")
    double freqCardiaca,

    @Schema(description = "Pressão arterial sistólica em mmHg", example = "120")
    @DecimalMin(value = "50.0", message = "Pressão sistólica muito baixa")
    @DecimalMax(value = "250.0", message = "Pressão sistólica muito alta")
    double pressaoSistolica,

    @Schema(description = "Saturação de oxigênio SpO2 em %", example = "97")
    @DecimalMin(value = "50.0", message = "Saturação O2 inválida")
    @DecimalMax(value = "100.0", message = "Saturação O2 não pode exceder 100%")
    double saturacaoO2,

    @Schema(description = "Nível de dor de 0 (sem dor) a 10 (dor máxima)", example = "5")
    @Min(value = 0, message = "Nível de dor mínimo é 0")
    @Max(value = 10, message = "Nível de dor máximo é 10")
    int nivelDor

) {}
