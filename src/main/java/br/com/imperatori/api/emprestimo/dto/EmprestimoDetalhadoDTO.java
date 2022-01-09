package br.com.imperatori.api.emprestimo.dto;

import br.com.imperatori.api.emprestimo.model.ClienteModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmprestimoDetalhadoDTO {

    private Integer id;

    private Double valorTotal;

    private String numeroPrestacoes;

    private Date vectoPrimeiraParcela;

    private String email;

    private String renda;

    private ClienteModel cliente;

    public EmprestimoDetalhadoDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getNumeroPrestacoes() {
        return numeroPrestacoes;
    }

    public void setNumeroPrestacoes(String numeroPrestacoes) {
        this.numeroPrestacoes = numeroPrestacoes;
    }

    public Date getVectoPrimeiraParcela() {
        return vectoPrimeiraParcela;
    }

    public void setVectoPrimeiraParcela(Date vectoPrimeiraParcela) {
        this.vectoPrimeiraParcela = vectoPrimeiraParcela;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRenda() {
        return renda;
    }

    public void setRenda(String renda) {
        this.renda = renda;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }
}
