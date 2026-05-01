package factory;

import command.*;
import dao.HospedagemDAO;
import modelo.Hospedagem;

import java.util.Locale;

public class HospedagemFactory {

    public static HospedagemCommand criarInsert(HospedagemDAO dao, Hospedagem h) {
        return new InserirHospedagemCommand(dao, h);
    }

    public static HospedagemCommand criarUpdate(HospedagemDAO dao, Hospedagem h) {
        return new AtualizarHospedagemCommand(dao, h);
    }

    public static HospedagemCommand criarDelete(HospedagemDAO dao, int id) {
        return new DeletarHospedagemCommand(dao, id);
    }

    public static HospedagemCommand criarConsultarPorId(HospedagemDAO dao, int id) {
        return new ConsultarHospedagemCommand(dao, id);
    }

    public static HospedagemCommand criarListar(HospedagemDAO dao) {
        return new ListarHospedagemCommand(dao);
    }

    public static HospedagemCommand criarPorAcao(String acao, HospedagemDAO dao, Hospedagem h, Integer id) {
        String a = acao == null ? "" : acao.toLowerCase(Locale.ROOT);
        switch (a) {
            case "insert":
            case "inserir":
                if (h == null) throw new IllegalArgumentException("Hospedagem necessária para insert");
                return criarInsert(dao, h);
            case "update":
            case "atualizar":
                if (h == null) throw new IllegalArgumentException("Hospedagem necessária para update");
                return criarUpdate(dao, h);
            case "delete":
            case "deletar":
                if (id == null) throw new IllegalArgumentException("ID necessário para delete");
                return criarDelete(dao, id);
            case "get":
            case "consultar":
                if (id == null) throw new IllegalArgumentException("ID necessário para consultar");
                return criarConsultarPorId(dao, id);
            case "list":
            case "listar":
                return criarListar(dao);
            default:
                throw new IllegalArgumentException("Ação desconhecida: " + acao);
        }
    }
}
