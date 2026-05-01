<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Hóspede - Hotel Budapeste</title>
    
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
        }

        input:not([type="submit"]):not([type="button"]):not([type="checkbox"]):not([type="radio"]) {
            background: rgba(255, 255, 255, 0.15) !important;
            border: 1px solid rgba(255, 255, 255, 0.3) !important;
            color: white !important;
            padding: 0.75rem;
            border-radius: 1rem; 
            width: 100%;
            transition: all 0.3s ease;
        }

        .glass-card::-webkit-scrollbar { width: 6px; }
        .glass-card::-webkit-scrollbar-thumb { background: rgba(255, 255, 255, 0.3); border-radius: 10px; }
        
        .toast-container { z-index: 1000; }
    </style>
</head>
<body class="p-4">

    <div class="overlay-bg"></div>

    <div id="toast-message" class="toast-container fixed bottom-5 right-5 hidden">
        <div class="flex items-center w-full max-w-xs p-4 mb-4 text-white rounded-lg shadow-xl" role="alert">
            <div class="inline-flex items-center justify-center flex-shrink-0 w-8 h-8 rounded-lg">
                <svg id="toast-icon" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                    <path id="icon-path" fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"></path>
                </svg>
            </div>
            <div class="ml-3 text-sm font-normal" id="toast-text"></div>
            <button type="button" class="ml-auto -mx-1.5 -my-1.5 rounded-lg p-1.5 inline-flex h-8 w-8 text-white opacity-70 hover:opacity-100" onclick="document.getElementById('toast-message').classList.add('hidden')">
                <span class="sr-only">Fechar</span>
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
            </button>
        </div>
    </div>
    
    <div class="w-full max-w-lg glass-card p-6 sm:p-8 space-y-6">
        <h2 class="text-3xl font-bold text-center text-white drop-shadow-md">Editar Hóspede</h2>
        
        <form action="hospede" method="post" class="space-y-4">
            <input type="hidden" name="acao" value="update">
            <input type="hidden" name="id" value="${hospede.id}">

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label for="nome" class="block text-sm font-medium text-white mb-1">Nome:</label>
                    <input type="text" id="nome" name="nome" value="${hospede.nome}" required>
                </div>
                <div>
                    <label for="sobrenome" class="block text-sm font-medium text-white mb-1">Sobrenome:</label>
                    <input type="text" id="sobrenome" name="sobrenome" value="${hospede.sobrenome}" required>
                </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label for="cpf" class="block text-sm font-medium text-white mb-1">CPF:</label>
                    <input type="text" id="cpf" name="cpf" value="${hospede.cpf}" required>
                </div>
                <div>
                    <label for="rg" class="block text-sm font-medium text-white mb-1">RG (Opcional):</label>
                    <input type="text" id="rg" name="rg" value="${hospede.rg}">
                </div>
            </div>

            <div>
                <label for="dataNascimento" class="block text-sm font-medium text-white mb-1">Data de Nascimento:</label>
                <input type="date" id="dataNascimento" name="dataNascimento" value="${hospede.dataNascimento}" required>
            </div>

            <div>
                <label for="email" class="block text-sm font-medium text-white mb-1">Email:</label>
                <input type="email" id="email" name="email" value="${hospede.email}">
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label for="telefone" class="block text-sm font-medium text-white mb-1">Telefone:</label>
                    <input type="text" id="telefone" name="telefone" value="${hospede.telefone}">
                </div>
                <div>
                    <label for="nacionalidade" class="block text-sm font-medium text-white mb-1">Nacionalidade:</label>
                    <input type="text" id="nacionalidade" name="nacionalidade" value="${hospede.nacionalidade}">
                </div>
            </div>

            <div>
                <label for="endereco" class="block text-sm font-medium text-white mb-1">Endereço:</label>
                <input type="text" id="endereco" name="endereco" value="${hospede.endereco}">
            </div>

            <div class="flex items-center">
                <input type="checkbox" id="vip" name="vip" class="w-5 h-5 rounded border-gray-300 text-blue-600 focus:ring-blue-500" ${vip ? "checked" : ""}>
                <label for="vip" class="ml-2 text-sm font-medium text-white">Cliente VIP</label>
            </div>
            
            <button type="submit" class="w-full flex justify-center py-3 px-4 border border-white/20 rounded-xl shadow-lg text-base font-bold text-white bg-white/10 hover:bg-white/20 backdrop-blur-md transition duration-200 mt-6">
                Salvar Alterações
            </button>
        </form>

        <div class="mt-4 flex flex-col items-center space-y-2 border-t border-white/10 pt-4">
            <a href="hospede" class="text-white/70 hover:text-white text-sm font-medium transition duration-150 underline-offset-4 hover:underline">
                Voltar para lista de hóspedes
            </a>
            
            <a href="index.jsp" class="text-white/70 hover:text-white text-sm font-medium transition duration-150 underline-offset-4 hover:underline">
                Voltar para o início
            </a>
        </div>
    </div>

    <script>
        function showToast(status, message) {
            const toastElement = document.getElementById('toast-message');
            const toastText = document.getElementById('toast-text');
            const toastDiv = toastElement.querySelector('div:first-child');
            toastText.textContent = message;
            let baseColor = status === 'success' ? 'bg-green-600' : 'bg-red-600';
            toastDiv.className = toastDiv.className.split(' ').filter(c => !c.startsWith('bg-')).join(' ');
            toastDiv.classList.add(baseColor);
            toastElement.classList.remove('hidden');
            setTimeout(() => toastElement.classList.add('hidden'), 5000);
        }

        document.addEventListener('DOMContentLoaded', () => {
            const params = new URLSearchParams(window.location.search);
            const status = params.get('status');
            const message = params.get('msg');
            if (status && message) {
                showToast(status, decodeURIComponent(message));
                if (history.replaceState) {
                    const url = new URL(window.location.href);
                    url.searchParams.delete('status');
                    url.searchParams.delete('msg');
                    history.replaceState(null, '', url.toString());
                }
            }
        });
    </script>
</body>
</html>