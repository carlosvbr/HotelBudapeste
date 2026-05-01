
package command;

import dao.HospedagemDAO;

public class DeletarHospedagemCommand implements HospedagemCommand {
    private HospedagemDAO dao;
    private int id;

    public DeletarHospedagemCommand(HospedagemDAO dao, int id) {
        this.dao = dao;
        this.id = id;
    }

    @Override
    public Object executar() {
        return dao.deletar(id);
    }
}