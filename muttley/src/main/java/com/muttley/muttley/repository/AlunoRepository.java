package com.muttley.muttley.repository;

import com.muttley.muttley.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    
    // O JpaRepository já traz métodos prontos:
    // save() -> Salva no banco
    // findAll() -> Lista todos
    // findById() -> Busca por ID
    // delete() -> Remove
}