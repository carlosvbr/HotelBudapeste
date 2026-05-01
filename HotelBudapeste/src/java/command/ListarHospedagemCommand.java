
package command;

import dao.HospedagemDAO;


public class ListarHospedagemCommand implements HospedagemCommand {
    private HospedagemDAO dao;

    public ListarHospedagemCommand(HospedagemDAO dao) {
        this.dao = dao;
    }

    @Override
    public Object executar() {
        return dao.buscarTodos();
    }
}
