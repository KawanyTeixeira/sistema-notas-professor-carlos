package com.sistema.notas.dto;

import com.sistema.notas.model.Aluno;
import java.util.List;

public class EstatisticasDTO {
    private List<Aluno> alunos;
    private double[] mediasPorDisciplina;
    private double mediaGeralTurma;
    private List<Aluno> alunosAcimaDaMedia;
    private List<Aluno> alunosFrequenciaBaixa;

    // Construtor vazio
    public EstatisticasDTO() {
    }

    // Construtor completo
    public EstatisticasDTO(List<Aluno> alunos, double[] mediasPorDisciplina,
                           double mediaGeralTurma, List<Aluno> alunosAcimaDaMedia,
                           List<Aluno> alunosFrequenciaBaixa) {
        this.alunos = alunos;
        this.mediasPorDisciplina = mediasPorDisciplina;
        this.mediaGeralTurma = mediaGeralTurma;
        this.alunosAcimaDaMedia = alunosAcimaDaMedia;
        this.alunosFrequenciaBaixa = alunosFrequenciaBaixa;
    }

    // Getters e Setters
    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public double[] getMediasPorDisciplina() {
        return mediasPorDisciplina;
    }

    public void setMediasPorDisciplina(double[] mediasPorDisciplina) {
        this.mediasPorDisciplina = mediasPorDisciplina;
    }

    public double getMediaGeralTurma() {
        return mediaGeralTurma;
    }

    public void setMediaGeralTurma(double mediaGeralTurma) {
        this.mediaGeralTurma = mediaGeralTurma;
    }

    public List<Aluno> getAlunosAcimaDaMedia() {
        return alunosAcimaDaMedia;
    }

    public void setAlunosAcimaDaMedia(List<Aluno> alunosAcimaDaMedia) {
        this.alunosAcimaDaMedia = alunosAcimaDaMedia;
    }

    public List<Aluno> getAlunosFrequenciaBaixa() {
        return alunosFrequenciaBaixa;
    }

    public void setAlunosFrequenciaBaixa(List<Aluno> alunosFrequenciaBaixa) {
        this.alunosFrequenciaBaixa = alunosFrequenciaBaixa;
    }
}