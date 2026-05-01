package command;

import service.IHospedeService;
import java.util.List;
import modelo.Hospede;

public class ListarHospedeCommand implements HospedeCommand {
    private IHospedeService service;

    public ListarHospedeCommand(IHospedeService service) {
        this.service = service;
    }

    @Override
    public List<Hospede> executar() {
        return service.buscarTodos();
    }
}
