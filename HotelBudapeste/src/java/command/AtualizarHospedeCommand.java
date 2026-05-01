package command;

import modelo.Hospede;
import service.IHospedeService;

public class AtualizarHospedeCommand implements HospedeCommand {

    private IHospedeService service;
    private Hospede hospede;

    public AtualizarHospedeCommand(IHospedeService service, Hospede hospede) {
        this.service = service;
        this.hospede = hospede;
    }

    @Override
    public Boolean executar() {
        return service.atualizar(hospede);
    }
}
