package br.com.imperatori.api.emprestimo.controller;

import br.com.imperatori.api.emprestimo.dto.EmprestimoDTO;
import br.com.imperatori.api.emprestimo.service.CredencialService;
import br.com.imperatori.api.emprestimo.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    CredencialService credencialService;

    @PostMapping(path = "/solicitar-emprestimo")
    public ResponseEntity<?> solicitarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO, HttpServletRequest request){
        if (emprestimoDTO == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        if(!credencialService.credencialValida(request)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O token informando não foi reconhecido como" +
                    " válido para acessar os recursos disponíveis. Faça um novo login.");
        }

        return ResponseEntity.ok().body(emprestimoService.solicitarEmprestimo(emprestimoDTO,request));
    }

    @GetMapping(path = "/consultar-emprestimo/{id}")
    public ResponseEntity<?> getEmprestimo(@PathVariable("id")Integer id, HttpServletRequest request){
        if (id <= 0){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        if(!credencialService.credencialValida(request)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O token informando não foi reconhecido como" +
                    " válido para acessar os recursos disponíveis. Faça um novo login.");
        }

        return ResponseEntity.ok().body(emprestimoService.consultarEmprestimo(id));
    }

    @GetMapping(path = "/meus-emprestimos/{id}")
    public ResponseEntity<?> listaEmprestimos(@PathVariable("id") Integer id, HttpServletRequest request){
        if (id <= 0){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        if(!credencialService.credencialValida(request)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O token informando não foi reconhecido como" +
                    " válido para acessar os recursos disponíveis. Faça um novo login.");
        }

        return ResponseEntity.ok().body(emprestimoService.consultarTodosEmprestimos(id));
    }
}
