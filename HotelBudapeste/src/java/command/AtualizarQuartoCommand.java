package command;

import dao.QuartoDAO;
import modelo.Quarto;

public class AtualizarQuartoCommand implements QuartoCommand {
    private final QuartoDAO dao;
    private final Quarto quarto;

    public AtualizarQuartoCommand(QuartoDAO dao, Quarto quarto) {
        this.dao = dao;
        this.quarto = quarto;
    }

    @Override
    public Object executar() {
        return dao.atualizar(quarto); 
    }
}
