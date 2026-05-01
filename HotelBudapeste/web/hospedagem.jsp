<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agendar Hospedagem - Hotel Budapeste</title>
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
            overflow-x: hidden;
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
            max-width: 580px;
            border-radius: 2rem;
            max-height: 90vh;
            overflow-y: auto;
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
            transition: all 0.3s ease;
        }

        select {
            appearance: none;
            -webkit-appearance: none;
            background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%23ffffff' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='M6 8l4 4 4-4'/%3e%3c/svg%3e") !important;
            background-repeat: no-repeat !important;
            background-position: right 1rem center !important;
            background-size: 1.2em 1.2em !important;
        }

        select option {
            background-color: #2a2a2a !important;
            color: white !important;
        }

        .glass-card::-webkit-scrollbar { width: 6px; }
        .glass-card::-webkit-scrollbar-thumb { background: rgba(255, 255, 255, 0.3); border-radius: 10px; }
        
        .toast-container { z-index: 1000; }
    </style>
</head>
<body class="p-4">

    <div class="overlay-bg"></div>

    <div id="toast-message" class="toast-container fixed bottom-5 right-5 hidden">
        <div class="flex items-center w-full max-w-xs p-4 mb-4 text-white rounded-lg shadow-xl transition-all duration-300">
            <div id="toast-div" class="inline-flex items-center justify-center flex-shrink-0 w-8 h-8 rounded-lg">
                <svg id="toast-icon" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"></svg>
            </div>
            <div class="ml-3 text-sm font-semibold" id="toast-text"></div>
        </div>
    </div>

    <div class="glass-card p-6 sm:p-8 space-y-6">
        <h2 class="text-3xl font-bold text-center text-white drop-shadow-md">Agendar Hospedagem</h2>
        
        <form action="hospedagem" method="post" class="space-y-4">
            <input type="hidden" name="acao" value="insert">

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">ID do Quarto:</label>
                    <input type="number" name="idQuarto" required placeholder="Ex: 101">
                </div>
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Valor Total (R$):</label>
                    <input type="number" step="0.01" name="valorTotal" required placeholder="Ex: 350.50">
                </div>
            </div>

            <div>
                <label class="block text-sm font-medium text-white/80 mb-1">IDs dos Hóspedes (Ex: 1, 5):</label>
                <input type="text" name="hospedes" required>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Data de Entrada:</label>
                    <input type="date" name="dataEntrada" required>
                </div>
                <div>
                    <label class="block text-sm font-medium text-white/80 mb-1">Data de Saída:</label>
                    <input type="date" name="dataSaida" required>
                </div>
            </div>

            <div>
                <label class="block text-sm font-medium text-white/80 mb-1">Status:</label>
                <select name="status" required>
                    <option value="Confirmada">Confirmada</option>
                    <option value="Check-in">Check-in</option>
                    <option value="Check-out">Check-out</option>
                    <option value="Cancelada">Cancelada</option>
                </select>
            </div>

            <div>
                <label class="block text-sm font-medium text-white/80 mb-1">Forma de Pagamento:</label>
                <input type="text" name="formaPagamento" placeholder="Ex: Pix, Cartão">
            </div>

            <!-- BOTÃO IGUAL AO DA OUTRA PÁGINA -->
            <button type="submit" class="w-full flex justify-center py-3 px-4 border border-white/20 rounded-lg shadow-lg text-base font-bold text-white bg-white/10 hover:bg-white/20 backdrop-blur-md transition duration-200 mt-6">
                Confirmar Agendamento
            </button>
        </form>
    
        <div class="mt-4 flex flex-col items-center space-y-2 border-t border-white/10 pt-4">
            
            <!-- BOTÃO IGUAL AO DA OUTRA PÁGINA -->
            <a href="hospedagem?acao=listar" class="w-full text-center py-2 px-4 rounded-lg text-sm font-medium text-white bg-white/5 hover:bg-white/15 border border-white/10 transition duration-150">
                Ver Lista de Hospedagens
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