package teste;

import command.HospedeCommand;
import factory.HospedeFactory;
import modelo.Hospede;
import builder.HospedeBuilder;
import service.HospedeService;
import service.IHospedeService;

import java.time.LocalDate;
import java.util.List;

public class TesteHospede {
    public static void main(String[] args) {

        IHospedeService service = new HospedeService();

        Hospede h = new HospedeBuilder()
                .comNome("Carlos")
                .comSobrenome("Silva")
                .comCpf("12345678900")
                .comRg("1234567")
                .comDataNascimento(LocalDate.of(1990, 1, 1))
                .comEmail("carlos@example.com")
                .comTelefone("11999999999")
                .comNacionalidade("Brasileiro")
                .comEndereco("Rua Exemplo, 123")
                .build();

        HospedeCommand inserir = HospedeFactory.criarPorAcao("insert", service, h, null);
        boolean inserido = (Boolean) inserir.executar();
        System.out.println("Inserido? " + inserido);

        HospedeCommand listar = HospedeFactory.criarPorAcao("listar", service, null, null);
        List<Hospede> hospedes = (List<Hospede>) listar.executar();
        System.out.println("\nLista de Hóspedes:");
        hospedes.forEach(hp -> System.out.println(hp.getNome() + " " + hp.getSobrenome()));

        h.setNome("Carlos Vinicius");
        HospedeCommand atualizar = HospedeFactory.criarPorAcao("update", service, h, h.getId());
        boolean atualizado = (Boolean) atualizar.executar();
        System.out.println("\nAtualizado? " + atualizado);

        HospedeCommand consultar = HospedeFactory.criarPorAcao("consultar", service, null, h.getId());
        Hospede consultado = (Hospede) consultar.executar();
        System.out.println("\nConsultado por ID: " + (consultado != null ? consultado.getNome() + " " + consultado.getSobrenome() : "Não encontrado"));

        HospedeCommand deletar = HospedeFactory.criarPorAcao("delete", service, null, h.getId());
        boolean deletado = (Boolean) deletar.executar();
        System.out.println("\nDeletado? " + deletado);
    }
}
