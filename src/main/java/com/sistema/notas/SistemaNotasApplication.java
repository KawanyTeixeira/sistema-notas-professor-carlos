package com.sistema.notas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaNotasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaNotasApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("Sistema de Notas iniciado com sucesso!");
        System.out.println("API disponível em: http://localhost:8080");
        System.out.println("Teste a API em: http://localhost:8080/api/alunos/test");
        System.out.println("===========================================\n");
    }
}