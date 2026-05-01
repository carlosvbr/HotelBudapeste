package service;

import modelo.Hospede;
import java.util.List;

public interface IHospedeService {
    boolean inserir(Hospede hospede);
    boolean atualizar(Hospede hospede);
    boolean deletar(int id);
    Hospede buscarPorId(int id);
    List<Hospede> buscarTodos();
}
