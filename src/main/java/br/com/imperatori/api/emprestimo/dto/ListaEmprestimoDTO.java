package br.com.imperatori.api.emprestimo.dto;

public class ListaEmprestimoDTO {

    private String id;

    private String valorTotal;

    private String numeroPrestacoes;

    public ListaEmprestimoDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getNumeroPrestacoes() {
        return numeroPrestacoes;
    }

    public void setNumeroPrestacoes(String numeroPrestacoes) {
        this.numeroPrestacoes = numeroPrestacoes;
    }
}
