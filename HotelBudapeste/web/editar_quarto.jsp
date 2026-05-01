<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Quarto - Hotel Budapeste</title>
    
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
            max-width: 480px;
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

    <div class="glass-card p-6 sm:p-8 space-y-6">
        <h2 class="text-3xl font-bold text-center text-white drop-shadow-md">Editar Quarto</h2>
        
        <form action="quarto" method="post" class="space-y-4">
            <input type="hidden" name="acao" value="update">
            <input type="hidden" name="id" value="${quarto.id}">

            <div class="grid grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Número:</label>
                    <input type="text" name="numero" value="${quarto.numero}" required>
                </div>
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Tipo:</label>
                    <input type="text" name="tipo" value="${quarto.tipo}" required>
                </div>
            </div>

            <div class="grid grid-cols-3 gap-4">
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Ocupação:</label>
                    <input type="number" name="ocupacaoMaxima" value="${quarto.ocupacaoMaxima}" required>
                </div>
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Andar:</label>
                    <input type="number" name="andar" value="${quarto.andar}">
                </div>
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Diária:</label>
                    <input type="number" step="0.01" name="valorDiaria" value="${quarto.valorDiaria}" required>
                </div>
            </div>

            <div>
                <label class="block text-sm font-medium text-white/80 mb-1">Status:</label>
                <select name="status">
                    <option value="Disponível" ${quarto.status == 'Disponível' ? 'selected' : ''}>Disponível</option>
                    <option value="Ocupado" ${quarto.status == 'Ocupado' ? 'selected' : ''}>Ocupado</option>
                    <option value="Manutenção" ${quarto.status == 'Manutenção' ? 'selected' : ''}>Manutenção</option>
                    <option value="Limpeza" ${quarto.status == 'Limpeza' ? 'selected' : ''}>Limpeza</option>
                </select>
            </div>

            <div class="flex items-center space-x-6 py-1">
                <label class="inline-flex items-center">
                    <input type="checkbox" name="wifiDisponivel" value="true" ${quarto.wifiDisponivel ? "checked" : ""} class="h-5 w-5 rounded bg-white/10 border-white/20 text-blue-500">
                    <span class="ml-2 text-white/90 text-sm">WiFi</span>
                </label>
                <label class="inline-flex items-center">
                    <input type="checkbox" name="temCamaExtra" value="true" ${quarto.temCamaExtra ? "checked" : ""} class="h-5 w-5 rounded bg-white/10 border-white/20 text-blue-500">
                    <span class="ml-2 text-white/90 text-sm">Cama Extra</span>
                </label>
            </div>
            
            <button type="submit" class="w-full flex justify-center py-3 px-4 border border-white/20 rounded-xl shadow-lg text-base font-bold text-white bg-white/10 hover:bg-white/20 backdrop-blur-md transition duration-200 mt-4">
                Salvar Alterações
            </button>
        </form>

        <div class="mt-4 flex flex-col items-center space-y-2 border-t border-white/10 pt-4">
            <a href="quarto" class="text-white/70 hover:text-white text-sm font-medium transition duration-150 hover:underline">
                Voltar para lista de quartos
            </a>
            
            <a href="index.jsp" class="text-white/70 hover:text-white text-sm font-medium transition duration-150 hover:underline">
                Voltar para o início
            </a>
        </div>
    </div>
</body>
</html>