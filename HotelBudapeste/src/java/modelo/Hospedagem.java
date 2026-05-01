
package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *  - 1:1 com Quarto 
 *  - 1:N com Hospede 
 */
public class Hospedagem implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Quarto quarto;                 // 1:1
    private List<Hospede> hospedes;        // 1:N
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private double valorTotal;
    private String status;                 
    private String formaPagamento;

    public Hospedagem() {
        this.hospedes = new ArrayList<>();
    }

    public Hospedagem(int id, Quarto quarto, List<Hospede> hospedes,
                      LocalDate dataEntrada, LocalDate dataSaida,
                      double valorTotal, String status, String formaPagamento
                      ) {
        this.id = id;
        this.quarto = quarto;
        this.hospedes = hospedes != null ? hospedes : new ArrayList<>();
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.valorTotal = valorTotal;
        this.status = status;
        this.formaPagamento = formaPagamento;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Quarto getQuarto() { return quarto; }
    public void setQuarto(Quarto quarto) { this.quarto = quarto; }

    public List<Hospede> getHospedes() { return hospedes; }
    public void setHospedes(List<Hospede> hospedes) { this.hospedes = hospedes; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }


    public void addHospede(Hospede hospede) {
        if (hospede != null) {
            this.hospedes.add(hospede);
        }
    }

    public boolean removeHospedeById(int hospedeId) {
        return this.hospedes.removeIf(h -> h.getId() == hospedeId);
    }


    public boolean isPeriodoSobreposto(LocalDate start, LocalDate end) {
        if (dataEntrada == null || dataSaida == null || start == null || end == null) {
            return false;
        }

        boolean naoSobrepoe = end.isBefore(this.dataEntrada) || start.isAfter(this.dataSaida);
        return !naoSobrepoe;
    }

    @Override
    public String toString() {
        return "Hospedagem{" +
                "id=" + id +
                ", quarto=" + quarto +
                ", hospedes=" + hospedes +
                ", dataEntrada=" + dataEntrada +
                ", dataSaida=" + dataSaida +
                ", valorTotal=" + valorTotal +
                ", status='" + status + '\'' +
                ", formaPagamento='" + formaPagamento + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hospedagem that = (Hospedagem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

