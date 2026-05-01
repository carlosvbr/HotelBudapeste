    package command;

import service.IHospedeService;

public class DeletarHospedeCommand implements HospedeCommand {
    private IHospedeService service;
    private int id;

    public DeletarHospedeCommand(IHospedeService service, int id) {
        this.service = service;
        this.id = id;
    }

    @Override
    public Boolean executar() {
        return service.deletar(id);
    }
}
