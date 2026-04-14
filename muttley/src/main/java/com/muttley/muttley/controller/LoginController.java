package com.muttley.muttley.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.muttley.muttley.repository.AlunoRepository;

@Controller
public class LoginController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("/")
    public String telaLogin() {
        return "index"; // Abre o login.html (ou index.html)
    }

    @PostMapping("/login")
    public String realizarLogin(@RequestParam("nome") String nome, @RequestParam("id") Long id) {
        
        // Verifica se o aluno existe no banco
        boolean existe = alunoRepository.existsById(id);

        if (existe) {
            return "redirect:/home"; // Se logou, vai para a home
        } else {
            return "redirect:/?erro=usuario_nao_encontrado"; // Se falhou, volta para o login
        }
    }
}