package com.sistema.notas.model;

import java.util.Arrays;

public class Aluno {
    private Long id;
    private String nome;
    private double[] notas;
    private double frequencia;

    // Construtor vazio (necessário para Spring)
    public Aluno() {
        this.notas = new double[5];
    }

    // Construtor completo
    public Aluno(Long id, String nome, double[] notas, double frequencia) {
        this.id = id;
        this.nome = nome;
        this.notas = notas != null ? notas : new double[5];
        this.frequencia = frequencia;
    }

    // Calcula a média das notas do aluno
    public double calcularMedia() {
        if (notas == null || notas.length == 0) {
            return 0.0;
        }
        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.length;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double[] getNotas() {
        return notas;
    }

    public void setNotas(double[] notas) {
        this.notas = notas;
    }

    public double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(double frequencia) {
        this.frequencia = frequencia;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", notas=" + Arrays.toString(notas) +
                ", frequencia=" + frequencia +
                ", media=" + calcularMedia() +
                '}';
    }
}