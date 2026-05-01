<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar Hóspede - Hotel Budapeste</title>
    
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap');
        
        body {
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 0;
            height: 100vh;
            width: 100vw;
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
            position: absolute;
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

        input::placeholder {
            color: rgba(255, 255, 255, 0.6);
        }

        input:focus {
            background: rgba(255, 255, 255, 0.25) !important;
            border-color: rgba(255, 255, 255, 0.5) !important;
            outline: none;
            box-shadow: 0 0 0 2px rgba(255, 255, 255, 0.2);
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
        <h2 class="text-3xl font-bold text-center text-white drop-shadow-md">Cadastrar Hóspede</h2>
        
        <form action="hospede" method="post" class="space-y-4">
            <input type="hidden" name="acao" value="insert">

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label for="nome" class="block text-sm font-medium text-white mb-1">Nome:</label>
                    <input type="text" id="nome" name="nome" required>
                </div>
                <div>
                    <label for="sobrenome" class="block text-sm font-medium text-white mb-1">Sobrenome:</label>
                    <input type="text" id="sobrenome" name="sobrenome" required>
                </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label for="cpf" class="block text-sm font-medium text-white mb-1">CPF:</label>
                    <input type="text" id="cpf" name="cpf" required>
                </div>
                <div>
                    <label for="rg" class="block text-sm font-medium text-white mb-1">RG (Opcional):</label>
                    <input type="text" id="rg" name="rg">
                </div>
            </div>

            <div>
                <label for="dataNascimento" class="block text-sm font-medium text-white mb-1">Data de Nascimento:</label>
                <input type="date" id="dataNascimento" name="dataNascimento" required>
            </div>

            <div>
                <label for="email" class="block text-sm font-medium text-white mb-1">Email:</label>
                <input type="email" id="email" name="email">
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label for="telefone" class="block text-sm font-medium text-white mb-1">Telefone:</label>
                    <input type="text" id="telefone" name="telefone">
                </div>
                <div>
                    <label for="nacionalidade" class="block text-sm font-medium text-white mb-1">Nacionalidade:</label>
                    <input type="text" id="nacionalidade" name="nacionalidade">
                </div>
            </div>

            <div>
                <label for="endereco" class="block text-sm font-medium text-white mb-1">Endereço:</label>
                <input type="text" id="endereco" name="endereco">
            </div>

            <div class="flex items-center">
                <input type="checkbox" id="vip" name="vip" class="w-5 h-5 rounded border-gray-300 text-blue-600 focus:ring-blue-500">
                <label for="vip" class="ml-2 text-sm font-medium text-white">Cliente VIP</label>
            </div>
            
            <button type="submit" class="w-full flex justify-center py-3 px-4 border border-white/20 rounded-xl shadow-lg text-base font-bold text-white bg-white/10 hover:bg-white/20 backdrop-blur-md transition duration-200 mt-6">
                Confirmar Cadastro
            </button>
        </form>

        <div class="mt-4 flex flex-col items-center space-y-2 border-t border-white/10 pt-4">
            <a href="hospede?acao=list" class="w-full text-center py-2 px-4 rounded-xl text-sm font-medium text-white bg-white/5 hover:bg-white/15 border border-white/10 transition duration-150">
                Ver lista de hóspedes
            </a>
            
            <a href="index.jsp" class="text-white/70 hover:text-white text-sm font-medium transition duration-150 underline-offset-4 hover:underline">
                Voltar ao Início
            </a>
        </div>
    </div>

    <script>
        function showToast(status, message) {
            const toastElement = document.getElementById('toast-message');
            const toastText = document.getElementById('toast-text');
            const toastDiv = toastElement.querySelector('div:first-child');
            const iconPath = document.getElementById('icon-path');

            toastText.textContent = message;
            
            let baseColor, pathD;
            if (status === 'success') {
                baseColor = 'bg-green-600';
                pathD = "M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z";
            } else {
                baseColor = 'bg-red-600';
                pathD = "M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z";
            }

            toastDiv.className = toastDiv.className.split(' ').filter(c => !c.startsWith('bg-')).join(' ');
            toastDiv.classList.add(baseColor);
            iconPath.setAttribute('d', pathD);
            
            toastElement.classList.remove('hidden');
            setTimeout(() => { toastElement.classList.add('hidden'); }, 5000);
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