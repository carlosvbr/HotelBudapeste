package teste;

import command.QuartoCommand;
import dao.QuartoDAO;
import factory.QuartoFactory;
import modelo.Quarto;

import java.util.List;

public class TesteQuarto {
    public static void main(String[] args) {
        QuartoDAO dao = new QuartoDAO();

        Quarto q = new Quarto();
        q.setNumero("101A");
        q.setTipo("SINGLE");
        q.setOcupacaoMaxima(2);
        q.setAndar(1);
        q.setValorDiaria(250.0);
        q.setStatus("LIVRE");
        q.setWifiDisponivel(true);
        q.setTemCamaExtra(false);

        QuartoCommand inserirCmd = QuartoFactory.criarInsert(dao, q);
        boolean inserido = (Boolean) inserirCmd.executar();
        System.out.println("Inserido? " + inserido);

        QuartoCommand listarCmd = QuartoFactory.criarListar(dao);
        List<Quarto> quartos = (List<Quarto>) listarCmd.executar();
        quartos.forEach(System.out::println);

        q.setValorDiaria(300.0);
        q.setStatus("OCUPADO");
        QuartoCommand atualizarCmd = QuartoFactory.criarUpdate(dao, q);
        boolean atualizado = (Boolean) atualizarCmd.executar();
        System.out.println("Atualizado? " + atualizado);

        QuartoCommand consultarCmd = QuartoFactory.criarConsultarPorId(dao, q.getId());
        Quarto consultado = (Quarto) consultarCmd.executar();
        System.out.println("Consultado por ID: " + consultado);

        QuartoCommand deletarCmd = QuartoFactory.criarDelete(dao, q.getId());
        boolean deletado = (Boolean) deletarCmd.executar();
        System.out.println("Deletado? " + deletado);
    }
}
