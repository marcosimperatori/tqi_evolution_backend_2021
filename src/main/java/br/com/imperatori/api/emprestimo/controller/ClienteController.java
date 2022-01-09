package br.com.imperatori.api.emprestimo.controller;

import br.com.imperatori.api.emprestimo.dto.ClienteDTO;
import br.com.imperatori.api.emprestimo.service.ClienteService;
import br.com.imperatori.api.emprestimo.service.CredencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/* Esta classe tem como responsabilidade expor os endpoints da api.
 * Todos os seus métodos, com exceção do ADICIONAR, tem como primeiro comportamento obter um atributo do cabeçalho de cada requisição
 *  recebida. Tal atributo traz um token jwt*/

@RestController
@RequestMapping(path = ("/cliente"))
public class ClienteController {


    @Autowired
    private ClienteService clienteService;

    @Autowired
    CredencialService credencialService;

    @PostMapping(path = ("/add"))
    public ResponseEntity<ClienteDTO> adicionar(@RequestBody ClienteDTO cliente, HttpServletRequest request){
        if (cliente == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        try{
            ClienteDTO clienteDTO = clienteService.salvar(cliente);
            if(clienteDTO != null) {
                return ResponseEntity.status(HttpStatus.OK).body(clienteDTO);
            } else{
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @PutMapping(path = ("/edit"))
    public ResponseEntity<?> atualizar(@RequestBody ClienteDTO cliente, HttpServletRequest request){
        if (cliente == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        if(!credencialService.credencialValida(request)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O token informando não foi reconhecido como" +
                    " válido para acessar os recursos disponíveis. Faça um novo login.");
        }

        try{
            ClienteDTO clienteDTO = clienteService.editar(cliente);

            if(clienteDTO != null) {
                return ResponseEntity.status(HttpStatus.OK).body(clienteDTO);
            }else{
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente não foi localizado.");
            }
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Erro.");
        }
    }

    @DeleteMapping(path = ("/{id}"))
    public ResponseEntity<?> deletar(@PathVariable("id") Integer id, HttpServletRequest request){
        if (id <= 0){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        if(!credencialService.credencialValida(request)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O token informando não foi reconhecido como" +
                    " válido para acessar os recursos disponíveis. Faça um novo login.");
        }

        try{
            clienteService.excluir(id);
            return ResponseEntity.ok().body("Excluído com sucesso.");
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
