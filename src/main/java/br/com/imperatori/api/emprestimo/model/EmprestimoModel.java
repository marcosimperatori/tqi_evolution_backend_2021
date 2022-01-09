package br.com.imperatori.api.emprestimo.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "Emprestimo")
public class EmprestimoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numeroPrestacoes;

    private Date vectoPrimeiraParcela;

    private Double valorTotal;

    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private ClienteModel cliente;

    public EmprestimoModel() {
    }

    public EmprestimoModel(Integer numeroPrestacoes, Date vectoPrimeiraParcela, Double valorTotal, String status, ClienteModel cliente) {
        this.numeroPrestacoes = numeroPrestacoes;
        this.vectoPrimeiraParcela = vectoPrimeiraParcela;
        this.valorTotal = valorTotal;
        this.status = status;
        this.cliente = cliente;
    }

    /* Este método melhora o formato da saída que será apresentada. */
    private String converterData(Date data){
        SimpleDateFormat formato  = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(data);
    }

    @Override
    public String toString() {
        return "Dados do empréstimo {" +
                "numero de Prestaçõess= " + numeroPrestacoes +
                ", vencimento da primeira parcela= " + converterData(vectoPrimeiraParcela) +
                ", valor Total= " + valorTotal +
                ", status= '" + status + '\'' +
                ", cliente= " + cliente.getNome() +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroPrestacoes() {
        return numeroPrestacoes;
    }

    public void setNumeroPrestacoes(Integer numeroPrestacoes) {
        this.numeroPrestacoes = numeroPrestacoes;
    }

    public Date getVectoPrimeiraParcela() {
        return vectoPrimeiraParcela;
    }

    public void setVectoPrimeiraParcela(Date vectoPrimeiraParcela) {
        this.vectoPrimeiraParcela = vectoPrimeiraParcela;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }
}
