<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar Quarto - Hotel Budapeste</title>
   
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
            backdrop-filter: blur(20px);
            -webkit-backdrop-filter: blur(20px);
            border: 1px solid rgba(255, 255, 255, 0.2);
            box-shadow: 0 20px 50px rgba(0, 0, 0, 0.3);
            z-index: 10;
            width: 100%;
            max-width: 480px;
            border-radius: 2rem;
        }

        input:not([type="submit"]):not([type="button"]):not([type="checkbox"]):not([type="radio"]), 
        select {
            background: rgba(255, 255, 255, 0.1) !important;
            border: 1px solid rgba(255, 255, 255, 0.2) !important;
            color: white !important;
            padding: 0.8rem !important;
            border-radius: 1rem !important;
            width: 100%;
            outline: none;
        }

        select {
            appearance: none;
            -webkit-appearance: none;
            cursor: pointer;
            background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%23ffffff' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='M6 8l4 4 4-4'/%3e%3c/svg%3e") !important;
            background-repeat: no-repeat !important;
            background-position: right 1rem center !important;
            background-size: 1.2em 1.2em !important;
        }

        select option {
            background-color: #2a2a2a !important;
            color: white !important;
            padding: 10px;
        }

        .btn-main {
            background: rgba(255, 255, 255, 0.2);
            border: 1px solid rgba(255, 255, 255, 0.3);
            border-radius: 1rem;
            color: white;
            font-weight: 700;
            padding: 1rem;
            transition: all 0.3s;
            cursor: pointer;
        }

        .btn-main:hover {
            transform: scale(1.02);
            background: rgba(255, 255, 255, 0.25);
        }

        .toast-container { z-index: 1000; }
    </style>
</head>
<body class="p-4">

    <div class="overlay-bg"></div>

    <div id="toast-message" class="toast-container fixed bottom-5 right-5 hidden">
        <div class="flex items-center w-full max-w-xs p-4 mb-4 text-white rounded-lg shadow-xl" role="alert">
            <div id="toast-div" class="inline-flex items-center justify-center flex-shrink-0 w-8 h-8 rounded-lg">
                <svg id="toast-icon" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"><path d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"/></svg>
            </div>
            <div class="ml-3 text-sm font-normal" id="toast-text"></div>
        </div>
    </div>
    
    <div class="glass-card p-8 space-y-6">
        <h2 class="text-3xl font-bold text-center text-white">Cadastrar Novo Quarto</h2>
        
        <form action="quarto" method="post" class="space-y-4">
            <input type="hidden" name="acao" value="insert">

            <div class="grid grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Número do Quarto:</label>
                    <input type="text" name="numero" required>
                </div>
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Tipo:</label>
                    <input type="text" name="tipo" required placeholder="Ex: Suíte">
                </div>
            </div>
            
            <div class="grid grid-cols-3 gap-4">
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Ocupação:</label>
                    <input type="number" name="ocupacaoMaxima" required min="1">
                </div>
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Andar:</label>
                    <input type="number" name="andar" value="0">
                </div>
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Diária:</label>
                    <input type="number" step="0.01" name="valorDiaria" required>
                </div>
            </div>

            <div>
                <label class="block text-sm font-medium text-white/80 mb-1">Status:</label>
                <select name="status" required>
                    <option value="Disponível">Disponível</option>
                    <option value="Ocupado">Ocupado</option>
                    <option value="Manutenção">Manutenção</option>
                    <option value="Limpeza">Limpeza</option>
                </select>
            </div>
            
            <div class="flex items-center space-x-6 py-2">
                <div class="flex items-center">
                    <input type="checkbox" id="wifi" name="wifiDisponivel" value="true" class="h-4 w-4 rounded bg-white/10 border-white/20">
                    <label for="wifi" class="ml-2 text-sm text-white/90">WiFi Disponível</label>
                </div>
                <div class="flex items-center">
                    <input type="checkbox" id="cama" name="temCamaExtra" value="true" class="h-4 w-4 rounded bg-white/10 border-white/20">
                    <label for="cama" class="ml-2 text-sm text-white/90">Tem Cama Extra</label>
                </div>
            </div>
            
            <button type="submit" class="w-full btn-main mt-4">
                Confirmar Cadastro
            </button>
        </form>

        <div class="mt-4 flex flex-col items-center space-y-2 border-t border-white/10 pt-4">
            <a href="quarto" class="w-full text-center py-2 px-4 rounded-lg text-sm font-medium text-white bg-white/5 hover:bg-white/15 border border-white/10 transition duration-150">
                Ver lista de quartos
            </a>
            
            <a href="index.jsp" class="text-white/70 hover:text-white text-sm font-medium transition duration-150">
                Voltar ao Início
            </a>
        </div>            
    </div>

    <script>
        function showToast(status, message) {
            const toast = document.getElementById('toast-message');
            document.getElementById('toast-text').textContent = message;
            const div = document.getElementById('toast-div');
            div.classList.add(status === 'success' ? 'bg-green-600' : 'bg-red-600');
            toast.classList.remove('hidden');
            setTimeout(() => toast.classList.add('hidden'), 5000);
        }

        document.addEventListener('DOMContentLoaded', () => {
            const params = new URLSearchParams(window.location.search);
            if (params.get('status')) showToast(params.get('status'), decodeURIComponent(params.get('msg')));
        });
    </script>
</body>
</html>