package decorator;

import dao.IHospedeDAO;
import modelo.Hospede;
import java.util.List;

public abstract class HospedeDecorator implements IHospedeDAO {

    protected IHospedeDAO hospedeDAO;

    public HospedeDecorator(IHospedeDAO hospedeDAO) {
        this.hospedeDAO = hospedeDAO;
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
