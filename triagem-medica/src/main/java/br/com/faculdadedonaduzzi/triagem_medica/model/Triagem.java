package br.com.faculdadedonaduzzi.triagem_medica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

// entidade que representa uma triagem no banco de dados
@Entity
@Table(name = "triagens")
public class Triagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do paciente eh obrigatorio")
    private String nomePaciente;

    @Min(0)
    @Max(150)
    private int idade;

    // sinais vitais do paciente
    private double temperatura;
    private double freqCardiaca;
    private double pressaoSistolica;
    private double saturacaoO2;

    @Min(0)
    @Max(10)
    private int nivelDor;

    // resultado da classificacao feita pelo modelo de ML
    private String prioridade;
    private String descricaoPrioridade;
    private int codigoPrioridade;

    // data e hora que a triagem foi registrada
    private LocalDateTime dataTriagem;

    @PrePersist
    public void prePersist() {
        this.dataTriagem = LocalDateTime.now();
    }

    // getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getFreqCardiaca() {
        return freqCardiaca;
    }

    public void setFreqCardiaca(double freqCardiaca) {
        this.freqCardiaca = freqCardiaca;
    }

    public double getPressaoSistolica() {
        return pressaoSistolica;
    }

    public void setPressaoSistolica(double pressaoSistolica) {
        this.pressaoSistolica = pressaoSistolica;
    }

    public double getSaturacaoO2() {
        return saturacaoO2;
    }

    public void setSaturacaoO2(double saturacaoO2) {
        this.saturacaoO2 = saturacaoO2;
    }

    public int getNivelDor() {
        return nivelDor;
    }

    public void setNivelDor(int nivelDor) {
        this.nivelDor = nivelDor;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getDescricaoPrioridade() {
        return descricaoPrioridade;
    }

    public void setDescricaoPrioridade(String descricaoPrioridade) {
        this.descricaoPrioridade = descricaoPrioridade;
    }

    public int getCodigoPrioridade() {
        return codigoPrioridade;
    }

    public void setCodigoPrioridade(int codigoPrioridade) {
        this.codigoPrioridade = codigoPrioridade;
    }

    public LocalDateTime getDataTriagem() {
        return dataTriagem;
    }

    public void setDataTriagem(LocalDateTime dataTriagem) {
        this.dataTriagem = dataTriagem;
    }
}
