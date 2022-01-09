package br.com.imperatori.api.emprestimo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Esta classe destina-se a implementar todas as regras envolvidas na criação e verificação de tokens gerados e
 * manipulados pela aplicação.
 */

@Service
public class TokenService {
    public static final int EXPIRA_EM = 600000;
    private static final String SENHA = "fd53a8b6-6498-4843-86b0-dcb3fc899226";

    /* Este método é responsável por gerar um token que representará a senha do usuário. Ele será criado com base
     *  na: SENHA + NOME + EMAIL, informados no momento do cadastro do usuário. */
    public String gerarSenha(String senha) {
        String token = Jwts.builder()
                .setSubject(senha)
                .signWith(SignatureAlgorithm.HS512, SENHA)
                .compact();

        return token;
    }

    /* Método responsável por gerar um token assim que o usuário consegue se autenticar na aplicação, será
     * utilizado o nome do usuário no payload do token. Este token será verificado em cada endpoint, antes
     * de qualquer operação. */
    public String gerarTokenLogin(String nomeUsuarioLogado) {
        String token = Jwts.builder()
                .setSubject(nomeUsuarioLogado)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRA_EM))
                .signWith(SignatureAlgorithm.HS512, SENHA)
                .compact();

        return token;
    }

    public Boolean tokenExpirado(String token){
        Date expiraEm = Jwts.parser().setSigningKey(SENHA).parseClaimsJws(token).getBody().getExpiration();
        return expiraEm.before(new Date(System.currentTimeMillis()));
    }

    public String getUsuario(String token){
        return     Jwts.parser().setSigningKey(SENHA).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean tokenValido(String token){
        try {
            Jwts.parser().setSigningKey(SENHA).parseClaimsJws(token).getBody();
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
