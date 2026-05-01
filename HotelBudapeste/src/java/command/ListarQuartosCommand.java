package command;

import dao.QuartoDAO;

public class ListarQuartosCommand implements QuartoCommand {
    private final QuartoDAO dao;

    public ListarQuartosCommand(QuartoDAO dao) {
        this.dao = dao;
    }

    @Override
    public Object executar() {
        return dao.buscarTodos();
    }
}
