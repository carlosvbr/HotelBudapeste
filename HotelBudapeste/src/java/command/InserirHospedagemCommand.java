package command;

import dao.HospedagemDAO;
import modelo.Hospedagem;

public class InserirHospedagemCommand implements HospedagemCommand {
    private HospedagemDAO dao;
    private Hospedagem hospedagem;

    public InserirHospedagemCommand(HospedagemDAO dao, Hospedagem hospedagem) {
        this.dao = dao;
        this.hospedagem = hospedagem;
    }

    @Override
    public Object executar() {
        return dao.inserir(hospedagem);
    }
}