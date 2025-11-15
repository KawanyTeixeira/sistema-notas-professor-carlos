package com.sistema.notas.service;

import com.sistema.notas.model.Aluno;
import com.sistema.notas.dto.EstatisticasDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    // Armazena os alunos em memória (simula um banco de dados)
    private final Map<Long, Aluno> alunos = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    /**
     * Adiciona um novo aluno à turma
     */
    public Aluno adicionarAluno(Aluno aluno) {
        aluno.setId(idCounter.getAndIncrement());
        alunos.put(aluno.getId(), aluno);
        System.out.println("Aluno adicionado: " + aluno);
        return aluno;
    }

    /**
     * Lista todos os alunos cadastrados
     */
    public List<Aluno> listarAlunos() {
        return new ArrayList<>(alunos.values());
    }

    /**
     * Busca um aluno específico pelo ID
     */
    public Aluno buscarAluno(Long id) {
        return alunos.get(id);
    }

    /**
     * Remove um aluno da turma
     */
    public boolean removerAluno(Long id) {
        Aluno removido = alunos.remove(id);
        if (removido != null) {
            System.out.println("Aluno removido: " + removido);
            return true;
        }
        return false;
    }

    /**
     * Calcula a média da turma em cada disciplina
     */
    public double[] calcularMediaPorDisciplina() {
        if (alunos.isEmpty()) {
            return new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        }

        double[] medias = new double[5];
        for (int i = 0; i < 5; i++) {
            final int disciplina = i;
            double soma = alunos.values().stream()
                    .mapToDouble(aluno -> aluno.getNotas()[disciplina])
                    .sum();
            medias[i] = soma / alunos.size();
        }
        return medias;
    }

    /**
     * Calcula a média geral da turma
     */
    public double calcularMediaGeralTurma() {
        if (alunos.isEmpty()) {
            return 0.0;
        }

        double soma = alunos.values().stream()
                .mapToDouble(Aluno::calcularMedia)
                .sum();
        return soma / alunos.size();
    }

    /**
     * Retorna lista de alunos com média acima da média da turma
     */
    public List<Aluno> getAlunosAcimaDaMedia() {
        double mediaTurma = calcularMediaGeralTurma();
        return alunos.values().stream()
                .filter(aluno -> aluno.calcularMedia() > mediaTurma)
                .collect(Collectors.toList());
    }

    /**
     * Retorna lista de alunos com frequência abaixo de 75%
     */
    public List<Aluno> getAlunosFrequenciaBaixa() {
        return alunos.values().stream()
                .filter(aluno -> aluno.getFrequencia() < 75.0)
                .collect(Collectors.toList());
    }

    /**
     * Retorna todas as estatísticas da turma
     */
    public EstatisticasDTO getEstatisticas() {
        return new EstatisticasDTO(
                listarAlunos(),
                calcularMediaPorDisciplina(),
                calcularMediaGeralTurma(),
                getAlunosAcimaDaMedia(),
                getAlunosFrequenciaBaixa()
        );
    }
}