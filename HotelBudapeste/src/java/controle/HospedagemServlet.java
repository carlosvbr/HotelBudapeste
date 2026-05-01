package controle;

import dao.HospedagemDAO;
import dao.QuartoDAO;
import dao.HospedeDAO;
import modelo.Hospedagem;
import modelo.Quarto;
import modelo.Hospede;
import builder.HospedagemBuilder;
import factory.HospedagemFactory;
import command.HospedagemCommand;
import util.IConexao;
import util.ConexaoPostgres;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/hospedagem")
public class HospedagemServlet extends HttpServlet {

    private HospedagemDAO hospedagemDAO = new HospedagemDAO();
    private QuartoDAO quartoDAO = new QuartoDAO();
    private IConexao conexao = new ConexaoPostgres();
    private HospedeDAO hospedeDAO = new HospedeDAO(conexao);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("status", request.getParameter("status"));
        request.setAttribute("msg", request.getParameter("msg"));

        String idParam = request.getParameter("id");
        String acao = request.getParameter("acao");

        if (!"edit".equalsIgnoreCase(acao)) {
            exibirListagem(request, response);
            return;
        }

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Hospedagem h = hospedagemDAO.buscarPorId(id);
                if (h != null) {
                    if (h.getQuarto() != null) {
                        h.setQuarto(quartoDAO.buscarPorId(h.getQuarto().getId()));
                    }
                    request.setAttribute("hospedagem", h);
                    request.getRequestDispatcher("editar_hospedagem.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) { }
        }
        exibirListagem(request, response);
    }

    private void exibirListagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Hospedagem> lista = hospedagemDAO.buscarTodos();
        for (Hospedagem hosp : lista) {
            if (hosp.getQuarto() != null) {
                hosp.setQuarto(quartoDAO.buscarPorId(hosp.getQuarto().getId()));
            }
        }
        request.setAttribute("lista", lista);
        request.getRequestDispatcher("lista_hospedagens.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");
        
        try {
            HospedagemCommand comando;
            String mensagemFinal = "";
            String destino = "hospedagem?acao=listar"; 

            if ("delete".equalsIgnoreCase(acao)) {
                if (idStr == null || idStr.isEmpty()) throw new Exception("ID inválido para exclusão.");
                
                int id = Integer.parseInt(idStr);
                comando = HospedagemFactory.criarPorAcao(acao, hospedagemDAO, null, id);
                mensagemFinal = "Hospedagem excluída com sucesso!";
            } else {
                String idQuartoStr = request.getParameter("idQuarto");
                if (idQuartoStr == null || idQuartoStr.isEmpty()) throw new Exception("Selecione um quarto válido.");

                int idQuarto = Integer.parseInt(idQuartoStr);
                Quarto q = quartoDAO.buscarPorId(idQuarto);
                if (q == null) throw new Exception("Quarto não encontrado no sistema.");

                String hospedesInput = request.getParameter("hospedes");
                List<Hospede> hospedesList = new ArrayList<>();
                if (hospedesInput != null && !hospedesInput.isEmpty()) {
                    String[] ids = hospedesInput.split(",");
                    for (String idH : ids) {
                        Hospede hosp = hospedeDAO.buscarPorId(Integer.parseInt(idH.trim()));
                        if (hosp != null) hospedesList.add(hosp);
                    }
                }
                
                if (hospedesList.isEmpty()) throw new Exception("Pelo menos um hóspede deve ser selecionado.");

                Hospedagem h = new HospedagemBuilder()
                        .id(idStr != null && !idStr.isEmpty() ? Integer.parseInt(idStr) : 0)
                        .quarto(q)
                        .hospedes(hospedesList)
                        .dataEntrada(LocalDate.parse(request.getParameter("dataEntrada")))
                        .dataSaida(LocalDate.parse(request.getParameter("dataSaida")))
                        .valorTotal(Double.parseDouble(request.getParameter("valorTotal")))
                        .status(request.getParameter("status"))
                        .formaPagamento(request.getParameter("formaPagamento"))
                        .build();

                comando = HospedagemFactory.criarPorAcao(acao, hospedagemDAO, h, h.getId());
                
                if ("update".equalsIgnoreCase(acao)) {
                    mensagemFinal = "Hospedagem atualizada com sucesso!";
                } else {
                    mensagemFinal = "Sua hospedagem foi criada com sucesso";
                }
            }

            boolean sucesso = (boolean) comando.executar();
            String status = sucesso ? "success" : "error";
            String msg = sucesso ? mensagemFinal : "A operação não pôde ser concluída no banco de dados.";

            String urlRedirecionamento = sucesso ? destino : "hospedagem.jsp";
            response.sendRedirect(urlRedirecionamento + (urlRedirecionamento.contains("?") ? "&" : "?") + "status=" + status + "&msg=" + URLEncoder.encode(msg, "UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
            String erroMensagem = (e.getMessage() != null && !e.getMessage().isEmpty()) ? e.getMessage() : "Erro desconhecido ao processar hospedagem.";
            response.sendRedirect("hospedagem.jsp?status=error&msg=" + URLEncoder.encode("Erro: " + erroMensagem, "UTF-8"));
        }
    }
}