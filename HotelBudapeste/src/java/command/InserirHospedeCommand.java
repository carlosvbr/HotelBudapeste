package command;

import modelo.Hospede;
import service.IHospedeService;

public class InserirHospedeCommand implements HospedeCommand {
    private IHospedeService service;
    private Hospede hospede;

    public InserirHospedeCommand(IHospedeService service, Hospede hospede) {
        this.service = service;
        this.hospede = hospede;
    }

    @Override
    public Boolean executar() {
        return service.inserir(hospede);
    }
}
