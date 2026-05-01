package controle;

import builder.QuartoBuilder;
import command.QuartoCommand;
import dao.QuartoDAO;
import factory.QuartoFactory;
import modelo.Quarto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/quarto")
public class QuartoServlet extends HttpServlet {

    private QuartoDAO quartoDAO = new QuartoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        request.setAttribute("status", request.getParameter("status"));
        request.setAttribute("msg", request.getParameter("msg"));

        String idParam = request.getParameter("id");
        String acao = request.getParameter("acao");

        if (idParam != null && "edit".equalsIgnoreCase(acao)) {
            try {
                int id = Integer.parseInt(idParam);
                QuartoCommand comando = QuartoFactory.criarConsultarPorId(quartoDAO, id);
                Quarto q = (Quarto) comando.executar();
                if (q != null) {
                    request.setAttribute("quarto", q);
                    request.getRequestDispatcher("editar_quarto.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) { }
        }

        QuartoCommand comandoListar = QuartoFactory.criarListar(quartoDAO);
        List<Quarto> lista = (List<Quarto>) comandoListar.executar();
        request.setAttribute("lista", lista);
        request.getRequestDispatcher("lista_quartos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");
        
        try {
            QuartoCommand comando;
            String mensagemSucesso = "";

            if ("delete".equalsIgnoreCase(acao)) {
                int idParaDeletar = Integer.parseInt(idStr);
                comando = QuartoFactory.criarDelete(quartoDAO, idParaDeletar);
                mensagemSucesso = "Quarto excluído com sucesso!";
            } else {
                Quarto q = new QuartoBuilder()
                        .id(idStr != null && !idStr.isEmpty() ? Integer.parseInt(idStr) : 0)
                        .numero(request.getParameter("numero"))
                        .tipo(request.getParameter("tipo"))
                        .ocupacaoMaxima(Integer.parseInt(request.getParameter("ocupacaoMaxima")))
                        .andar(Integer.parseInt(request.getParameter("andar")))
                        .valorDiaria(Double.parseDouble(request.getParameter("valorDiaria")))
                        .status(request.getParameter("status"))
                        .wifiDisponivel(request.getParameter("wifiDisponivel") != null)
                        .temCamaExtra(request.getParameter("temCamaExtra") != null)
                        .build();

                if ("insert".equalsIgnoreCase(acao) || "inserir".equalsIgnoreCase(acao)) {
                    
                    if (quartoDAO.existeNumero(q.getNumero())) {
                        String msgErro = "O quarto " + q.getNumero() + " já está cadastrado!";
                        response.sendRedirect("quarto.jsp?status=error&msg=" + URLEncoder.encode(msgErro, "UTF-8"));
                        return; // Para a execução aqui
                    }

                    comando = QuartoFactory.criarInsert(quartoDAO, q);
                    mensagemSucesso = "Quarto cadastrado com sucesso!";
                } else {
                    comando = QuartoFactory.criarUpdate(quartoDAO, q);
                    mensagemSucesso = "Dados do quarto atualizados!";
                }
            }

            boolean sucesso = (boolean) comando.executar();
            String status = sucesso ? "success" : "error";
            String msg = sucesso ? mensagemSucesso : "Não foi possível completar a operação.";

            response.sendRedirect("quarto?status=" + status + "&msg=" + URLEncoder.encode(msg, "UTF-8"));

        } catch (Exception e) {
            e.printStackTrace(); 
            response.sendRedirect("quarto?status=error&msg=" + URLEncoder.encode("Erro: " + e.getMessage(), "UTF-8"));
        }
    }
}