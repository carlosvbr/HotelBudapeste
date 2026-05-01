<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="modelo.Hospedagem, modelo.Hospede, java.util.List, java.util.stream.Collectors" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Hospedagem - Hotel Budapeste</title>
    
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
            overflow: hidden;
        }

        .overlay-bg {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.2);
            z-index: 0;
        }

        .glass-card {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(15px);
            -webkit-backdrop-filter: blur(15px);
            border: 1px solid rgba(255, 255, 255, 0.2);
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.5);
            z-index: 10;
            max-height: 90vh;
            overflow-y: auto;
            border-radius: 2rem;
            width: 100%;
            max-width: 580px;
        }

        input:not([type="submit"]):not([type="button"]):not([type="checkbox"]):not([type="radio"]), 
        select {
            background: rgba(255, 255, 255, 0.15) !important;
            border: 1px solid rgba(255, 255, 255, 0.3) !important;
            color: white !important;
            padding: 0.85rem 1rem !important;
            border-radius: 1rem !important;
            width: 100%;
            transition: all 0.3s ease;
            outline: none;
            min-height: 3.2rem;
        }

        select {
            appearance: none;
            -webkit-appearance: none;
            background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%23ffffff' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='M6 8l4 4 4-4'/%3e%3c/svg%3e") !important;
            background-repeat: no-repeat !important;
            background-position: right 1rem center !important;
            background-size: 1.2em 1.2em !important;
            padding-right: 2.5rem !important;
        }

        select option {
            background-color: #2a2a2a !important;
            color: white !important;
        }

        .glass-card::-webkit-scrollbar { width: 6px; }
        .glass-card::-webkit-scrollbar-thumb { background: rgba(255, 255, 255, 0.3); border-radius: 10px; }
    </style>
</head>
<body class="p-4">

    <div class="overlay-bg"></div>

    <div class="w-full max-w-lg glass-card p-6 sm:p-8 space-y-6">
        <h2 class="text-3xl font-bold text-center text-white drop-shadow-md">Editar Hospedagem</h2>
        
        <form action="hospedagem" method="post" class="space-y-4">
            <input type="hidden" name="acao" value="update">
            <input type="hidden" name="id" value="${hospedagem.id}">

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">ID do Quarto:</label>
                    <input type="number" name="idQuarto" value="${hospedagem.quarto.id}" required>
                </div>
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Valor Total (R$):</label>
                    <input type="number" step="0.01" name="valorTotal" value="${hospedagem.valorTotal}" required>
                </div>
            </div>

            <div>
                <label class="block text-sm font-medium text-white/80 mb-1">IDs dos Hóspedes:</label>
                <% 
                    Hospedagem h = (Hospedagem) request.getAttribute("hospedagem");
                    String idsFormatados = "";
                    if (h != null && h.getHospedes() != null) {
                        idsFormatados = h.getHospedes().stream()
                                         .map(hp -> String.valueOf(hp.getId()))
                                         .collect(Collectors.joining(", "));
                    }
                %>
                <input type="text" name="hospedes" value="<%= idsFormatados %>" required>
            </div>

            <div class="grid grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Data de Entrada:</label>
                    <input type="date" name="dataEntrada" value="${hospedagem.dataEntrada}" required>
                </div>
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Data de Saída:</label>
                    <input type="date" name="dataSaida" value="${hospedagem.dataSaida}" required>
                </div>
            </div>

            <div>
                <label class="block text-sm font-medium text-white/80 mb-1">Status:</label>
                <select name="status">
                    <option value="Confirmada" ${hospedagem.status == 'Confirmada' ? 'selected' : ''}>Confirmada</option>
                    <option value="Check-in" ${hospedagem.status == 'Check-in' ? 'selected' : ''}>Check-in</option>
                    <option value="Check-out" ${hospedagem.status == 'Check-out' ? 'selected' : ''}>Check-out</option>
                    <option value="Cancelada" ${hospedagem.status == 'Cancelada' ? 'selected' : ''}>Cancelada</option>
                </select>
            </div>

            <button type="submit" class="w-full flex justify-center py-3 px-4 border border-white/20 rounded-xl shadow-lg text-base font-bold text-white bg-white/10 hover:bg-white/20 backdrop-blur-md transition duration-200 mt-4">
                Salvar Alterações
            </button>
        </form>

        <div class="mt-4 flex flex-col items-center space-y-2 border-t border-white/10 pt-4">
            <a href="hospedagem?acao=listar" class="text-white/70 hover:text-white text-sm font-medium transition duration-150 underline-offset-4 hover:underline">
                Voltar para lista de hospedagens
            </a>
            
            <a href="index.jsp" class="text-white/70 hover:text-white text-sm font-medium transition duration-150 underline-offset-4 hover:underline">
                Voltar para o início
            </a>
        </div>
    </div>
</body>
</html>