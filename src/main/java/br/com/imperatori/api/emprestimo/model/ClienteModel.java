package br.com.imperatori.api.emprestimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Cliente")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nome;
    private String senha;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String cpf;
    private String rg;
    private Double renda;
    private Boolean ativo;
    private Boolean bloqueado;

    @OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY)
    private List<EmprestimoModel> emprestimos;

    public ClienteModel(Integer id, String nome, String senha, String email, String cpf, String rg, Double renda, Boolean ativo, Boolean bloqueado) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.cpf = cpf;
        this.rg = rg;
        this.renda = renda;
        this.ativo = ativo;
        this.bloqueado = bloqueado;
    }

    public ClienteModel() {
    }

    @Override
    public String toString() {
        return "Dados do cliente {" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Double getRenda() {
        return renda;
    }

    public void setRenda(Double renda) {
        this.renda = renda;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public List<EmprestimoModel> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<EmprestimoModel> emprestimos) {
        this.emprestimos = emprestimos;
    }
}
