package com.muttley.muttley.model;

import jakarta.persistence.Entity; // <-- CERTIFIQUE-SE QUE É JAKARTA E NÃO JAVAX
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Essa anotação avisa o Spring que a classe é "gerenciada" pelo JPA
@Table(name = "tb_alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String curso;

    // Construtores, Getters e Setters
}