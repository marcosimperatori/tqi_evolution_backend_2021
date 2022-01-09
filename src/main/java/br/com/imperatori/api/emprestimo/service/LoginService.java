package br.com.imperatori.api.emprestimo.service;

import br.com.imperatori.api.emprestimo.model.ClienteModel;
import br.com.imperatori.api.emprestimo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    TokenService tokenService;

    private String nomeUsuarioLogado;

    /* Método responsável por indentificar se existe algum cliente registrado para o email e senha informados
     * pelo usuário ao tentar fazer o login.
     *
     * Caso as credenciais sejam válidas retornará um token que deverá ser enviado no cabeçalho das demais
     * requisições. */
    public String logar(String email, String senha){
        try {
            if (!autenticado(email, senha)) {
                return null;
            }
            //gerar um token para o usuário usar para consumir os endpoints da api
            return tokenService.gerarTokenLogin(getNomeUsuarioLogado());
        }catch (Exception e){
            return null;
        }
    }

    private Boolean autenticado(String email, String senha){
        ClienteModel cliente = clienteRepository.findByEmail(email);

        //verifica se encontrou o cliente
        if(cliente == null ){
            return false;
        }else{
            //já guarda o nome do cliente que será utilizando para gerar o token de acesso
            setNomeUsuarioLogado(cliente.getNome());
        }

        /* A partir da senha informada pelo cliente, vou gerar um token dela, no entanto os parâmetros devem
        *  ser passados na mesma ordem que foi utilizado no momento do cadastro, que é:
        *  SENHA + NOME + EMAIL assim é possível obter um token igual ao que foi gerado no cadastro.
        *
        *  Neste momento vou utilizar a senha que o usuário informou e o nome e mail vou considerar os que já que
        *  estavam no banco de dados. */
        String senhaCriptografa = tokenService.gerarSenha(senha + cliente.getNome() + cliente.getEmail());

        //agora basta comparar se o token gerado com a senha informada confere com o token armazenado para o usuário
        return (cliente.getSenha().equals(senhaCriptografa)) ? true : false;
    }

    public String getNomeUsuarioLogado() {
        return nomeUsuarioLogado;
    }

    public void setNomeUsuarioLogado(String nomeUsuarioLogado) {
        this.nomeUsuarioLogado = nomeUsuarioLogado;
    }
}
