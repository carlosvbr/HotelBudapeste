package service;

import dao.HospedeDAO;
import dao.IHospedeDAO;
import modelo.Hospede;
import util.ConexaoPostgres;
import util.IConexao;
import java.util.List;

public class HospedeService implements IHospedeService {

    private final IHospedeDAO hospedeDAO;

    public HospedeService(IHospedeDAO hospedeDAO) {
        this.hospedeDAO = hospedeDAO;
    }

    public HospedeService() {
        IConexao conexao = new ConexaoPostgres();
        this.hospedeDAO = new HospedeDAO(conexao);
    }

    @Override
    public boolean inserir(Hospede hospede) {
        return hospedeDAO.inserir(hospede);
    }

    @Override
    public boolean atualizar(Hospede hospede) {
        return hospedeDAO.atualizar(hospede);
    }

    @Override
    public boolean deletar(int id) {
        return hospedeDAO.deletar(id);
    }

    @Override
    public Hospede buscarPorId(int id) {
        return hospedeDAO.buscarPorId(id);
    }

    @Override
    public List<Hospede> buscarTodos() {
        return hospedeDAO.buscarTodos();
    }
}
