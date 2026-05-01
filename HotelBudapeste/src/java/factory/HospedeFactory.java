package factory;

import command.*;
import modelo.Hospede;
import service.IHospedeService;

import java.util.Locale;

public class HospedeFactory {

    
    public static HospedeCommand criarInsert(IHospedeService service, Hospede hospede) {
        return new InserirHospedeCommand(service, hospede);
    }

    public static HospedeCommand criarUpdate(IHospedeService service, Hospede hospede) {
        return new AtualizarHospedeCommand(service, hospede);
    }

    public static HospedeCommand criarDelete(IHospedeService service, int id) {
        return new DeletarHospedeCommand(service, id);
    }

    public static HospedeCommand criarConsultarPorId(IHospedeService service, int id) {
        return new ConsultarHospedeCommand(service, id);
    }

    public static HospedeCommand criarListar(IHospedeService service) {
        return new ListarHospedeCommand(service);
    }

    
    public static HospedeCommand criarPorAcao(String acao, IHospedeService service, Hospede hospede, Integer id) {
        String a = acao == null ? "" : acao.toLowerCase(Locale.ROOT);

        switch (a) {
            case "insert":
            case "inserir":
                return criarInsert(service, hospede);

            case "update":
            case "atualizar":
                return criarUpdate(service, hospede);

            case "delete":
            case "deletar":
                return criarDelete(service, id);

            case "get":
            case "consultar":
                return criarConsultarPorId(service, id);

            case "list":
            case "listar":
                return criarListar(service);

            default:
                throw new IllegalArgumentException("Ação desconhecida: " + acao);
        }
    }
}
