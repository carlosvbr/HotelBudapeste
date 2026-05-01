
package command;

import dao.HospedagemDAO;
import modelo.Hospedagem;
        
public class AtualizarHospedagemCommand implements HospedagemCommand {
    private HospedagemDAO dao;
    private Hospedagem hospedagem;

    public AtualizarHospedagemCommand(HospedagemDAO dao, Hospedagem hospedagem) {
        this.dao = dao;
        this.hospedagem = hospedagem;
    }

    @Override
    public Object executar() {
        return dao.atualizar(hospedagem);
    }
}