package builder;

import modelo.Hospedagem;
import modelo.Quarto;
import modelo.Hospede;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HospedagemBuilder {
    private int id;
    private Quarto quarto;
    private List<Hospede> hospedes = new ArrayList<>();
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private double valorTotal;
    private String status;
    private String formaPagamento;

    public HospedagemBuilder() { }

    public HospedagemBuilder id(int id) {
        this.id = id;
        return this;
    }

    public HospedagemBuilder quarto(Quarto quarto) {
        this.quarto = quarto;
        return this;
    }

    public HospedagemBuilder hospedes(List<Hospede> hospedes) {
        if (hospedes != null) {
            this.hospedes = hospedes;
        }
        return this;
    }

    public HospedagemBuilder addHospede(Hospede hospede) {
        if (hospede != null) {
            this.hospedes.add(hospede);
        }
        return this;
    }

    public HospedagemBuilder dataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
        return this;
    }

    public HospedagemBuilder dataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
        return this;
    }

    public HospedagemBuilder valorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public HospedagemBuilder status(String status) {
        this.status = status;
        return this;
    }

    public HospedagemBuilder formaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
        return this;
    }

    public Hospedagem build() {
        Hospedagem h = new Hospedagem();
        h.setId(this.id);
        h.setQuarto(this.quarto);
        h.setHospedes(this.hospedes);
        h.setDataEntrada(this.dataEntrada);
        h.setDataSaida(this.dataSaida);
        h.setValorTotal(this.valorTotal);
        h.setStatus(this.status);
        h.setFormaPagamento(this.formaPagamento);
        return h;
    }
}
