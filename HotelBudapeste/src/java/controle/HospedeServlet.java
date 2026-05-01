package controle;

import builder.HospedeBuilder;
import command.HospedeCommand;
import factory.HospedeFactory;
import modelo.Hospede;
import modelo.HospedeVip;
import service.HospedeService;
import service.IHospedeService;
import dao.HospedeDAO;
import dao.IHospedeDAO;
import decorator.HospedeLogger;
import util.ConexaoPostgres;
import util.IConexao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/hospede")
public class HospedeServlet extends HttpServlet {

    private IHospedeService hospedeService;

    @Override
    public void init() throws ServletException {
        super.init();
        IConexao conexao = new ConexaoPostgres();
        IHospedeDAO hospedeDAO = new HospedeLogger(new HospedeDAO(conexao));
        hospedeService = new HospedeService(hospedeDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");
        String status = request.getParameter("status");
        String message = request.getParameter("msg");
        String destino = "/lista_hospedes.jsp";

        request.setAttribute("status", status);
        request.setAttribute("msg", message);

        try {
            if (acao == null || acao.equals("list")) {
                List<Hospede> lista = hospedeService.buscarTodos();
                request.setAttribute("lista", lista);

            } else if (acao.equals("insert")) {
                destino = "/hospede.jsp"; // Nome padrão do formulário de cadastro

            } else if (acao.equals("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Hospede hospedeParaEditar = hospedeService.buscarPorId(id);

                if (hospedeParaEditar != null) {
                    request.setAttribute("hospede", hospedeParaEditar);
                    request.setAttribute("vip", hospedeParaEditar instanceof HospedeVip);
                    destino = "/editar_hospede.jsp";
                } else {
                    forwardError(request, response, "Hóspede não encontrado para edição.", "/lista_hospedes.jsp");
                    return;
                }
            } else if (acao.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean sucesso = hospedeService.deletar(id);
                String msg = sucesso ? "Hóspede excluído com sucesso." : "Falha ao excluir hóspede.";
                String stat = sucesso ? "success" : "error";
                response.sendRedirect("hospede?status=" + stat + "&msg=" + java.net.URLEncoder.encode(msg, "UTF-8"));
                return;
            }

        } catch (Exception e) {
            forwardError(request, response, "Erro interno: " + e.getMessage(), "/lista_hospedes.jsp");
            return;
        }

        request.getRequestDispatcher(destino).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");
        
        try {
            if ("delete".equals(acao)) {
                String idParam = request.getParameter("id");
                if (idParam == null || idParam.isEmpty()) throw new Exception("ID não fornecido.");
                
                int id = Integer.parseInt(idParam);
                Hospede h = new Hospede();
                h.setId(id);
                HospedeCommand comando = HospedeFactory.criarPorAcao(acao, hospedeService, h, id);
                comando.executar();

                response.sendRedirect("hospede?status=success&msg=" + java.net.URLEncoder.encode("Hóspede excluído com sucesso.", "UTF-8"));
                return;
            }

            boolean isVip = request.getParameter("vip") != null;
            Hospede h;
            int idHospede = (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) 
                            ? Integer.parseInt(request.getParameter("id")) : 0;

            if (isVip) {
                h = new HospedeVip(
                        idHospede,
                        request.getParameter("nome"),
                        request.getParameter("sobrenome"),
                        request.getParameter("cpf"),
                        request.getParameter("rg"),
                        (request.getParameter("dataNascimento") != null && !request.getParameter("dataNascimento").isEmpty()) 
                            ? LocalDate.parse(request.getParameter("dataNascimento")) : null,
                        request.getParameter("email"),
                        request.getParameter("telefone"),
                        request.getParameter("nacionalidade"),
                        request.getParameter("endereco")
                );
            } else {
                h = new HospedeBuilder()
                        .comId(idHospede)
                        .comNome(request.getParameter("nome"))
                        .comSobrenome(request.getParameter("sobrenome"))
                        .comCpf(request.getParameter("cpf"))
                        .comRg(request.getParameter("rg"))
                        .comDataNascimento((request.getParameter("dataNascimento") != null && !request.getParameter("dataNascimento").isEmpty()) 
                            ? LocalDate.parse(request.getParameter("dataNascimento")) : null)
                        .comEmail(request.getParameter("email"))
                        .comTelefone(request.getParameter("telefone"))
                        .comNacionalidade(request.getParameter("nacionalidade"))
                        .comEndereco(request.getParameter("endereco"))
                        .build();
            }

            HospedeCommand comando = HospedeFactory.criarPorAcao(acao, hospedeService, h, idHospede > 0 ? idHospede : null);
            Object resultado = comando.executar();
            boolean sucesso = resultado instanceof Boolean ? (Boolean) resultado : resultado != null;

            if (sucesso) {
                String msg = "Operação realizada com sucesso!";
                response.sendRedirect("hospede?status=success&msg=" + java.net.URLEncoder.encode(msg, "UTF-8"));
            } else {
                String destinoErro = (idHospede > 0) ? "editar_hospede.jsp" : "hospede.jsp";
                response.sendRedirect(destinoErro + "?status=error&msg=" + java.net.URLEncoder.encode("Erro ao realizar operação.", "UTF-8"));
            }

        } catch (Exception e) {
            String msgErro = (e.getMessage() != null) ? e.getMessage() : "Erro desconhecido.";
            String destinoCatch = (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) ? "editar_hospede.jsp" : "hospede.jsp";
            response.sendRedirect(destinoCatch + "?status=error&msg=" + java.net.URLEncoder.encode("Erro: " + msgErro, "UTF-8"));
        }
    }

    private void forwardError(HttpServletRequest request, HttpServletResponse response, String mensagemErro, String destino) throws ServletException, IOException {
        request.setAttribute("status", "error");
        request.setAttribute("msg", mensagemErro);
        if (destino.contains("lista")) {
            request.setAttribute("lista", hospedeService.buscarTodos());
        }
        request.getRequestDispatcher(destino).forward(request, response);
    }
}
