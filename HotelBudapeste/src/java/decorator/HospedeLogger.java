package decorator;

import dao.IHospedeDAO;
import modelo.Hospede;
import java.util.List;

public class HospedeLogger extends HospedeDecorator {

    public HospedeLogger(IHospedeDAO hospedeDAO) {
        super(hospedeDAO);
    }

    @Override
    public boolean inserir(Hospede hospede) {
        System.out.println("[LOG] Inserindo hóspede: " + hospede.getNome());
        boolean resultado = super.inserir(hospede);
        System.out.println("[LOG] Resultado da inserção: " + resultado);
        return resultado;
    }

    @Override
    public boolean atualizar(Hospede hospede) {
        System.out.println("[LOG] Atualizando hóspede ID: " + hospede.getId());
        boolean resultado = super.atualizar(hospede);
        System.out.println("[LOG] Resultado da atualização: " + resultado);
        return resultado;
    }

    @Override
    public boolean deletar(int id) {
        System.out.println("[LOG] Deletando hóspede ID: " + id);
        boolean resultado = super.deletar(id);
        System.out.println("[LOG] Resultado do delete: " + resultado);
        return resultado;
    }

    @Override
    public Hospede buscarPorId(int id) {
        System.out.println("[LOG] Buscando hóspede ID: " + id);
        Hospede hospede = super.buscarPorId(id);
        System.out.println("[LOG] Resultado da busca: " + (hospede != null ? "Encontrado" : "Não encontrado"));
        return hospede;
    }

    @Override
    public List<Hospede> buscarTodos() {
        System.out.println("[LOG] Buscando todos os hóspedes...");
        List<Hospede> lista = super.buscarTodos();
        System.out.println("[LOG] Total encontrado: " + lista.size());
        return lista;
    }
}
