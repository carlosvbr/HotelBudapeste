package dao;

import java.util.List;
import modelo.Hospede;

public interface IHospedeDAO {

    boolean inserir(Hospede hospede);

    boolean atualizar(Hospede hospede);

    boolean deletar(int id);

    Hospede buscarPorId(int id);

    List<Hospede> buscarTodos();
}
