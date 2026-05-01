package factory;

import command.*;
import dao.QuartoDAO;
import modelo.Quarto;
import java.util.Locale;

public class QuartoFactory {

    public static QuartoCommand criarInsert(QuartoDAO dao, Quarto quarto) {
        return new InserirQuartoCommand(dao, quarto);
    }

    public static QuartoCommand criarUpdate(QuartoDAO dao, Quarto quarto) {
        return new AtualizarQuartoCommand(dao, quarto);
    }

    public static QuartoCommand criarDelete(QuartoDAO dao, int id) {
        return new DeletarQuartoCommand(dao, id);
    }

    public static QuartoCommand criarConsultarPorId(QuartoDAO dao, int id) {
        return new ConsultarQuartoCommand(dao, id);
    }

    public static QuartoCommand criarListar(QuartoDAO dao) {
        return new ListarQuartosCommand(dao);
    }

    public static QuartoCommand criarPorAcao(String acao, QuartoDAO dao, Quarto quarto, Integer id) {
        String a = acao == null ? "" : acao.toLowerCase(Locale.ROOT);
        switch (a) {
            case "insert":
            case "inserir":
                if (quarto == null) throw new IllegalArgumentException("Quarto necessário para insert");
                return criarInsert(dao, quarto);
            case "update":
            case "atualizar":
                if (quarto == null) throw new IllegalArgumentException("Quarto necessário para update");
                return criarUpdate(dao, quarto);
            case "delete":
            case "deletar":
                if (id == null) throw new IllegalArgumentException("id necessário para delete");
                return criarDelete(dao, id);
            case "get":
            case "consultar":
                if (id == null) throw new IllegalArgumentException("id necessário para consultar");
                return criarConsultarPorId(dao, id);
            case "list":
            case "listar":
                return criarListar(dao);
            default:
                throw new IllegalArgumentException("Ação desconhecida: " + acao);
        }
    }
}

