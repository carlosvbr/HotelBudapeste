package modelo;

import java.time.LocalDate;

public class HospedeVip extends Hospede {

    public HospedeVip() {
        super();
    }

    public HospedeVip(int id, String nome, String sobrenome, String cpf, String rg,
                      LocalDate dataNascimento, String email, String telefone,
                      String nacionalidade, String endereco) {
        super(id, nome, sobrenome, cpf, rg, dataNascimento, email, telefone, nacionalidade, endereco);
    }

    public void mostrarBeneficio() {
        System.out.println("Hóspede VIP: " + getNome() + " " + getSobrenome() + " tem direito a 5% de cashback!");
    }

    @Override
    public String toString() {
        return super.toString() + " [VIP]";
    }
}

