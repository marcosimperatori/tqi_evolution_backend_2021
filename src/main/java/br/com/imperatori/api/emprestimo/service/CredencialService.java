package br.com.imperatori.api.emprestimo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CredencialService {
    public static final String HEADER_ATRIBUTO = "Authorization";
    public static final String ATRIBUTO_PREFIXO = "Bearer ";

    @Autowired
    TokenService tokenService;

    @Autowired
    ClienteService clienteService;


    /* Este método trata de verificar se o usuário enviou um token válido na sua requisição. */
    public Boolean credencialValida(HttpServletRequest request){
        // obtendo um atributo que veio no cabeçalho da requisição
        String atributo = request.getHeader(HEADER_ATRIBUTO);

        if((atributo == null) || (!atributo.startsWith(ATRIBUTO_PREFIXO))){
            return false;
        }

        String token = atributo.replace(ATRIBUTO_PREFIXO,"");

        if (!tokenService.tokenValido(token)){
            return false;
        }

        if(!clienteService.clienteJaExiste(tokenService.getUsuario(token))){
            return false;
        }
        return true;
    }

    /* Este método é responsável por recuperar o token que consta no cabeçalho da requisão, pois a partir dele
    *  será possível recuperar o nome do usuário e com isso recuperar o usuário que já está registrado no banco de
    *  dados. Como este método só será chamado quando o usuário já possuir um token válido, basta apenas extrair
    *  o nome do usuário do payload do token e retorná-lo. */
    public String getUsuarioLogado(HttpServletRequest request){
        // obtendo um atributo que veio no cabeçalho da requisição
        String atributo = request.getHeader(HEADER_ATRIBUTO);

        String token = atributo.replace(ATRIBUTO_PREFIXO,"");

        return (tokenService.getUsuario(token));
    }
}
