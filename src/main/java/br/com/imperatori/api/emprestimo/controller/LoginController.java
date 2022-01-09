package br.com.imperatori.api.emprestimo.controller;

import br.com.imperatori.api.emprestimo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;


    @PostMapping(path = "/logar")
    public ResponseEntity<String>  conectar(@RequestParam("email") String email, @RequestParam("senha") String senha){
        if((email == null) || (senha == null)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        String token = loginService.logar(email,senha);

        if(token != null){
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
