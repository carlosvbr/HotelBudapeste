  
package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class Hospede implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private String nacionalidade;
    private String endereco;

    public Hospede() { }

    public Hospede(int id, String nome, String sobrenome, String cpf, String rg,
                   LocalDate dataNascimento, String email, String telefone,
                   String nacionalidade, String endereco) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
        this.nacionalidade = nacionalidade;
        this.endereco = endereco;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    @Override
    public String toString() {
        return "Hospede{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", nacionalidade='" + nacionalidade + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hospede hospede = (Hospede) o;
        return id == hospede.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

