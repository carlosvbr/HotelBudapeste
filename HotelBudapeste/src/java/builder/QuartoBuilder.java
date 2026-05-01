package builder;

import modelo.Quarto;

public class QuartoBuilder {
    private int id;
    private String numero;
    private String tipo;
    private int ocupacaoMaxima;
    private int andar;
    private double valorDiaria;
    private String status;
    private boolean wifiDisponivel;
    private boolean temCamaExtra;

    public QuartoBuilder() { }

    public QuartoBuilder id(int id) {
        this.id = id;
        return this;
    }

    public QuartoBuilder numero(String numero) {
        this.numero = numero;
        return this;
    }

    public QuartoBuilder tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public QuartoBuilder ocupacaoMaxima(int ocupacaoMaxima) {
        this.ocupacaoMaxima = ocupacaoMaxima;
        return this;
    }

    public QuartoBuilder andar(int andar) {
        this.andar = andar;
        return this;
    }

    public QuartoBuilder valorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
        return this;
    }

    public QuartoBuilder status(String status) {
        this.status = status;
        return this;
    }

    public QuartoBuilder wifiDisponivel(boolean wifiDisponivel) {
        this.wifiDisponivel = wifiDisponivel;
        return this;
    }

    public QuartoBuilder temCamaExtra(boolean temCamaExtra) {
        this.temCamaExtra = temCamaExtra;
        return this;
    }

    public Quarto build() {
        return new Quarto(id, numero, tipo, ocupacaoMaxima, andar, valorDiaria,
                          status, wifiDisponivel, temCamaExtra);
    }
}
