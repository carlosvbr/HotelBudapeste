package command;

import dao.QuartoDAO;
import modelo.Quarto;

public class ConsultarQuartoCommand implements QuartoCommand {
    private final QuartoDAO dao;
    private final int id;

    public ConsultarQuartoCommand(QuartoDAO dao, int id) {
        this.dao = dao;
        this.id = id;
    }

    @Override
    public Object executar() {
        return dao.buscarPorId(id);
    }
}
