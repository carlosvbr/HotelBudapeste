package command;

import dao.QuartoDAO;

public class DeletarQuartoCommand implements QuartoCommand {
    private final QuartoDAO dao;
    private final int id;

    public DeletarQuartoCommand(QuartoDAO dao, int id) {
        this.dao = dao;
        this.id = id;
    }

    @Override
    public Object executar() {
        return dao.deletar(id);
    }
}
