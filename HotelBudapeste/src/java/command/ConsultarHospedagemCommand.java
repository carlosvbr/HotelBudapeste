
package command;

import dao.HospedagemDAO;


public class ConsultarHospedagemCommand implements HospedagemCommand {
    private HospedagemDAO dao;
    private int id;

    public ConsultarHospedagemCommand(HospedagemDAO dao, int id) {
        this.dao = dao;
        this.id = id;
    }

    @Override
    public Object executar() {
        return dao.buscarPorId(id);
    }
}
