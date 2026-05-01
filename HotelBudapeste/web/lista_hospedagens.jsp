<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="modelo.Hospedagem, modelo.Hospede, java.util.List, java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Hospedagens - Hotel Budapeste</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap');
        
        body {
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 0;
            min-height: 100vh;
            background-image: url('hotel.png'); 
            background-size: cover; 
            background-position: center;
            background-attachment: fixed;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        /* Camada de escurecimento sutil para o fundo */
        .overlay-bg {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.2);
            z-index: -1;
        }

        /* Efeito de Vidro (Glassmorphism) no Card da Tabela */
        .glass-card {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(15px);
            -webkit-backdrop-filter: blur(15px);
            border: 1px solid rgba(255, 255, 255, 0.2);
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.5);
        }

        /* Ajuste das cores da tabela para o tema escuro/vidro */
        th {
            background-color: rgba(255, 255, 255, 0.1) !important;
            color: rgba(255, 255, 255, 0.8) !important;
        }
        
        td {
            color: rgba(255, 255, 255, 0.9) !important;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1) !important;
        }

        tr:hover {
            background-color: rgba(255, 255, 255, 0.05) !important;
        }

        .btn-delete {
            background: none;
            border: none;
            cursor: pointer;
            padding: 0;
            color: #ff6b6b; 
            transition: color 150ms ease-in-out;
        }
        .btn-delete:hover {
            color: #ff0000; 
            text-decoration: underline;
        }

        /* Custom Scrollbar para a tabela */
        .overflow-x-auto::-webkit-scrollbar { height: 6px; }
        .overflow-x-auto::-webkit-scrollbar-thumb { background: rgba(255, 255, 255, 0.2); border-radius: 10px; }
    </style>
</head>
<body class="p-4 sm:p-8">

    <div class="overlay-bg"></div>

<%
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    String status = (String) request.getAttribute("status");
    if (status == null) status = request.getParameter("status");

    String message = (String) request.getAttribute("msg");
    if (message == null) message = request.getParameter("msg");

    List<Hospedagem> lista = (List<Hospedagem>) request.getAttribute("lista");
%>

    <% if (status != null && message != null) { 
        String toastBg = "success".equals(status) ? "bg-green-600" : "bg-red-600";
        String toastIconColor = "success".equals(status) ? "text-green-200 bg-green-800" : "text-red-200 bg-red-800";
    %>
        <div id="toast-message" class="fixed bottom-5 right-5 z-50">
            <div class="flex items-center w-full max-w-xs p-4 mb-4 text-white <%= toastBg %> rounded-lg shadow-xl" role="alert">
                <div class="inline-flex items-center justify-center flex-shrink-0 w-8 h-8 <%= toastIconColor %> rounded-lg">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="<%= "success".equals(status) ? "M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" : "M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" %>" clip-rule="evenodd"></path>
                    </svg>
                </div>
                <div class="ml-3 text-sm font-normal text-white"><%= message %></div>
                <button type="button" class="ml-auto -mx-1.5 -my-1.5 p-1.5 text-white hover:opacity-80 inline-flex" onclick="document.getElementById('toast-message').remove()">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                </button>
            </div>
        </div>
    <% } %>

    <div class="w-full max-w-6xl mx-auto glass-card p-6 sm:p-10 rounded-xl">
        <div class="flex justify-between items-center mb-6">
            <h2 class="text-3xl font-extrabold text-white drop-shadow-lg">📋 Lista de Hospedagens</h2>
            <a href="hospedagem.jsp" class="text-base font-bold text-white bg-white/10 hover:bg-white/20 border border-white/20 transition duration-150 py-2 px-6 rounded-lg shadow-md backdrop-blur-md">
                + Nova Hospedagem
            </a>
        </div>

        <% if (lista != null && !lista.isEmpty()) { %>
            <div class="overflow-x-auto border border-white/10 rounded-lg">
                <table class="min-w-full divide-y divide-white/10">
                    <thead>
                        <tr>
                            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider">ID</th>
                            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider">Quarto</th>
                            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider">Hóspedes</th>
                            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-center">Entrada</th>
                            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-center">Saída</th>
                            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider">Status</th>
                            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-center">Ações</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-white/10">
                        <% for (Hospedagem h : lista) { %>
                        <tr>
                            <td class="px-4 py-4 whitespace-nowrap text-sm font-medium"><%= h.getId() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm font-bold text-white">
                                <%= h.getQuarto() != null ? h.getQuarto().getNumero() : "N/A" %>
                            </td>
                            <td class="px-4 py-4 text-sm">
                                <% if (h.getHospedes() != null) {
                                    for (Hospede hosp : h.getHospedes()) { %>
                                        <span class="block text-xs">• <%= hosp.getNome() %> <%= hosp.getSobrenome() %></span>
                                <%  } 
                                   } %>
                            </td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm text-center">
                                <%= h.getDataEntrada() != null ? h.getDataEntrada().format(dtf) : "-" %>
                            </td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm text-center">
                                <%= h.getDataSaida() != null ? h.getDataSaida().format(dtf) : "-" %>
                            </td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm">
                                <span class="<%= "Confirmada".equals(h.getStatus()) ? "text-green-400 font-bold" : "text-white/60" %>">
                                    <%= h.getStatus() %>
                                </span>
                            </td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm font-medium flex justify-center items-center gap-4">
                                <a href="hospedagem?acao=edit&id=<%= h.getId() %>" class="text-yellow-400 hover:text-yellow-200 transition">Editar</a>
                                <form action="hospedagem" method="post" onsubmit="return confirm('Excluir esta hospedagem?')">
                                    <input type="hidden" name="acao" value="delete">
                                    <input type="hidden" name="id" value="<%= h.getId() %>">
                                    <button type="submit" class="btn-delete">Excluir</button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <% } else { %>
            <div class="p-12 text-center border-2 border-dashed border-white/20 rounded-lg bg-white/5">
                <p class="text-lg text-white/60 font-semibold">Nenhuma hospedagem encontrada.</p>
                <a href="hospedagem.jsp" class="text-blue-400 hover:text-blue-300 text-sm mt-2 block">Crie a primeira hospedagem agora</a>
            </div>
        <% } %>

        <div class="mt-8 text-center border-t border-white/10 pt-6">
            <a href="index.jsp" class="text-white/60 hover:text-white font-medium transition duration-150">Voltar ao Início</a>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const toast = document.getElementById('toast-message');
            if (toast) {
                setTimeout(() => { toast.remove(); }, 5000);

                if (window.history.replaceState) {
                    const url = new URL(window.location.href);
                    url.searchParams.delete('status');
                    url.searchParams.delete('msg');
                    window.history.replaceState(null, '', url.toString());
                }
            }
        });
    </script>
</body>
</html>