
package modelo;

import java.io.Serializable;
import java.util.Objects;


public class Quarto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String numero;           
    private String tipo;             
    private int ocupacaoMaxima;
    private int andar;
    private double valorDiaria;
    private String status;           
    private boolean wifiDisponivel;
    private boolean temCamaExtra;

    public Quarto() { }

    public Quarto(int id, String numero, String tipo, int ocupacaoMaxima, int andar,
                  double valorDiaria, String status,
                  boolean wifiDisponivel, boolean temCamaExtra) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.ocupacaoMaxima = ocupacaoMaxima;
        this.andar = andar;
        this.valorDiaria = valorDiaria;
        this.status = status;
        this.wifiDisponivel = wifiDisponivel;
        this.temCamaExtra = temCamaExtra;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getOcupacaoMaxima() { return ocupacaoMaxima; }
    public void setOcupacaoMaxima(int ocupacaoMaxima) { this.ocupacaoMaxima = ocupacaoMaxima; }

    public int getAndar() { return andar; }
    public void setAndar(int andar) { this.andar = andar; }

    public double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(double valorDiaria) { this.valorDiaria = valorDiaria; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isWifiDisponivel() { return wifiDisponivel; }
    public void setWifiDisponivel(boolean wifiDisponivel) { this.wifiDisponivel = wifiDisponivel; }

    public boolean isTemCamaExtra() { return temCamaExtra; }
    public void setTemCamaExtra(boolean temCamaExtra) { this.temCamaExtra = temCamaExtra; }

    @Override
    public String toString() {
        return "Quarto{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                ", ocupacaoMaxima=" + ocupacaoMaxima +
                ", andar=" + andar +
                ", valorDiaria=" + valorDiaria +
                ", status='" + status + '\'' +
                ", wifiDisponivel=" + wifiDisponivel +
                ", temCamaExtra=" + temCamaExtra +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quarto quarto = (Quarto) o;
        return id == quarto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

