<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotel Budapeste</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #000; 
            height: 100vh;
            width: 100vw;
            display: flex;
            justify-content: center;
            align-items: center;
            overflow: hidden;
            position: relative;
        }

        .bg-container {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 0;
        }

        .bg-container img {
            width: 100%;
            height: 100%;
            object-fit: cover; 
            object-position: center;
            filter: brightness(1.1) contrast(1.05);
        }

        .overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.1); 
            z-index: 1;
        }

        .content {
            position: relative;
            text-align: center;
            z-index: 2;
        }

        h1 {
            color: #333;
            background-color: rgba(255, 255, 255, 0.9);
            display: inline-block;
            padding: 15px 45px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            font-size: 3em;
            margin-bottom: 30px;
            backdrop-filter: blur(5px);
        }

        .menu {
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .menu a {
            display: inline-block;
            padding: 18px 30px;
            text-decoration: none;
            background-color: rgba(255, 255, 255, 0.9);
            color: #333;
            border-radius: 10px;
            font-weight: bold;
            box-shadow: 0 4px 15px rgba(0,0,0,0.4);
            transition: all 0.3s ease;
            backdrop-filter: blur(5px);
        }

        .menu a:hover {
            background-color: rgba(255, 255, 255, 1);
            transform: translateY(-5px);
            box-shadow: 0 12px 25px rgba(0,0,0,0.6);
        }
    </style>
</head>
<body>

    <div class="bg-container">
        <img src="hotel.png" alt="Hotel Budapeste">
    </div>
    
    <div class="overlay"></div>

    <div class="content">
        <h1>Hotel Budapeste</h1>

        <div class="menu">
            <a href="hospede.jsp">Cadastrar Hóspede</a>
            <a href="quarto.jsp">Cadastrar Quarto</a>
            <a href="hospedagem.jsp">Cadastrar Hospedagem</a>
        </div>
    </div>

</body>
</html>