package teste;

import dao.HospedagemDAO;
import dao.QuartoDAO;
import dao.HospedeDAO;
import modelo.Hospedagem;
import modelo.Quarto;
import modelo.Hospede;
import factory.HospedagemFactory;
import command.HospedagemCommand;
import util.ConexaoPostgres;
import util.IConexao;

import java.time.LocalDate;
import java.util.List;

public class TesteHospedagem {
    public static void main(String[] args) {
        IConexao conexao = new ConexaoPostgres();
        HospedeDAO hospedeDAO = new HospedeDAO(conexao);

        QuartoDAO quartoDAO = new QuartoDAO();
        HospedagemDAO hospedagemDAO = new HospedagemDAO();

        Quarto quarto = new Quarto();
        quarto.setNumero("102B");
        quarto.setTipo("DOUBLE");
        quarto.setOcupacaoMaxima(2);
        quarto.setAndar(1);
        quarto.setValorDiaria(300.0);
        quarto.setStatus("LIVRE");
        quarto.setWifiDisponivel(true);
        quarto.setTemCamaExtra(false);
        if (quartoDAO.inserir(quarto)) {
            System.out.println("Quarto inserido com ID: " + quarto.getId());
        }

        Hospede hospede = new Hospede();
        hospede.setNome("Ana");
        hospede.setSobrenome("Silva");
        hospede.setCpf("98765432100");
        hospede.setTelefone("11988887777");
        if (hospedeDAO.inserir(hospede)) {
            System.out.println("Hóspede inserido com ID: " + hospede.getId());
        }

        Hospedagem hospedagem = new Hospedagem();
        hospedagem.setQuarto(quarto);
        hospedagem.addHospede(hospede);
        hospedagem.setDataEntrada(LocalDate.now());
        hospedagem.setDataSaida(LocalDate.now().plusDays(3));
        hospedagem.setValorTotal(900.0);
        hospedagem.setStatus("CONFIRMADA");
        hospedagem.setFormaPagamento("Cartão");

        try {
            HospedagemCommand inserirCmd = HospedagemFactory.criarPorAcao("insert", hospedagemDAO, hospedagem, 0);
            boolean inserido = (boolean) inserirCmd.executar();
            System.out.println("Hospedagem inserida? " + inserido);

            List<Hospedagem> todas = hospedagemDAO.buscarTodos();
            System.out.println("\n--- Todas as Hospedagens ---");
            todas.forEach(System.out::println);

            hospedagem.setStatus("FINALIZADA");
            hospedagem.setValorTotal(950.0);
            HospedagemCommand atualizarCmd = HospedagemFactory.criarPorAcao("update", hospedagemDAO, hospedagem, hospedagem.getId());
            boolean atualizado = (boolean) atualizarCmd.executar();
            System.out.println("\nHospedagem atualizada? " + atualizado);

            Hospedagem consultado = hospedagemDAO.buscarPorId(hospedagem.getId());
            System.out.println("\nHospedagem consultada por ID: " + consultado);

            HospedagemCommand deletarCmd = HospedagemFactory.criarPorAcao("delete", hospedagemDAO, hospedagem, hospedagem.getId());
            boolean deletado = (boolean) deletarCmd.executar();
            System.out.println("\nHospedagem deletada? " + deletado);

        } catch (IllegalArgumentException e) {
            System.err.println("Erro no comando de hospedagem: " + e.getMessage());
        }
    }
}
