package command;

import dao.QuartoDAO;
import modelo.Quarto;

public class InserirQuartoCommand implements QuartoCommand {
    private final QuartoDAO dao;
    private final Quarto quarto;

    public InserirQuartoCommand(QuartoDAO dao, Quarto quarto) {
        this.dao = dao;
        this.quarto = quarto;
    }

    @Override
    public Object executar() {
        return dao.inserir(quarto);
    }
}
