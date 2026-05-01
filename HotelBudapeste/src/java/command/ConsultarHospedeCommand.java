package command;

import service.IHospedeService;
import modelo.Hospede;

public class ConsultarHospedeCommand implements HospedeCommand {
    private IHospedeService service;
    private int id;

    public ConsultarHospedeCommand(IHospedeService service, int id) {
        this.service = service;
        this.id = id;
    }

    @Override
    public Hospede executar() {
        return service.buscarPorId(id);
    }
}

