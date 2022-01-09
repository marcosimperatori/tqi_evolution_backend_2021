package br.com.imperatori.api.emprestimo.dto;

import java.util.Date;

public class EmprestimoDTO {
    private Integer id;

    private String numeroPrestacoes;

    private String vectoPrimeiraParcela;

    private String valorTotal;

    private String status;

    private String nomeCliente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroPrestacoes() {
        return numeroPrestacoes;
    }

    public void setNumeroPrestacoes(String numeroPrestacoes) {
        this.numeroPrestacoes = numeroPrestacoes;
    }

    public String getVectoPrimeiraParcela() {
        return vectoPrimeiraParcela;
    }

    public void setVectoPrimeiraParcela(String vectoPrimeiraParcela) {
        this.vectoPrimeiraParcela = vectoPrimeiraParcela;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
}
