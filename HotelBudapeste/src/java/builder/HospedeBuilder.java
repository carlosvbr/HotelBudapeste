package builder;

import modelo.Hospede;
import java.time.LocalDate;

public class HospedeBuilder {
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

    public HospedeBuilder() { }

    public HospedeBuilder comId(int id) {
        this.id = id;
        return this;
    }

    public HospedeBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public HospedeBuilder comSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
        return this;
    }

    public HospedeBuilder comCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public HospedeBuilder comRg(String rg) {
        this.rg = rg;
        return this;
    }

    public HospedeBuilder comDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public HospedeBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public HospedeBuilder comTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public HospedeBuilder comNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
        return this;
    }

    public HospedeBuilder comEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public Hospede build() {
        return new Hospede(id, nome, sobrenome, cpf, rg, dataNascimento, email, telefone, nacionalidade, endereco);
    }
}
