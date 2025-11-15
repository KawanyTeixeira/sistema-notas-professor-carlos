package com.sistema.notas.controller;

import com.sistema.notas.model.Aluno;
import com.sistema.notas.service.TurmaService;
import com.sistema.notas.dto.EstatisticasDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "http://localhost:3000")
public class AlunoController {

    @Autowired
    private TurmaService turmaService;

    /**
     * POST /api/alunos - Adiciona um novo aluno
     */
    @PostMapping
    public ResponseEntity<Aluno> adicionarAluno(@RequestBody Aluno aluno) {
        try {
            // Validações
            if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            if (aluno.getNotas() == null || aluno.getNotas().length != 5) {
                return ResponseEntity.badRequest().build();
            }

            for (double nota : aluno.getNotas()) {
                if (nota < 0 || nota > 10) {
                    return ResponseEntity.badRequest().build();
                }
            }

            if (aluno.getFrequencia() < 0 || aluno.getFrequencia() > 100) {
                return ResponseEntity.badRequest().build();
            }

            Aluno novoAluno = turmaService.adicionarAluno(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);

        } catch (Exception e) {
            System.err.println("Erro ao adicionar aluno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/alunos - Lista todos os alunos
     */
    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos() {
        try {
            List<Aluno> alunos = turmaService.listarAlunos();
            return ResponseEntity.ok(alunos);
        } catch (Exception e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/alunos/{id} - Busca um aluno específico
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarAluno(@PathVariable Long id) {
        try {
            Aluno aluno = turmaService.buscarAluno(id);
            if (aluno == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(aluno);
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /api/alunos/{id} - Remove um aluno
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAluno(@PathVariable Long id) {
        try {
            boolean removido = turmaService.removerAluno(id);
            if (removido) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Erro ao remover aluno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/alunos/estatisticas - Retorna estatísticas completas da turma
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<EstatisticasDTO> getEstatisticas() {
        try {
            EstatisticasDTO estatisticas = turmaService.getEstatisticas();
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            System.err.println("Erro ao obter estatísticas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/alunos/test - Endpoint de teste
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API funcionando! Total de alunos: " + turmaService.listarAlunos().size());
    }
}