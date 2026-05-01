package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/HotelBudapeste"; 
        String user = "seu_usuario"; 
        String password = "sua_senha"; 

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            if (con != null) {
                System.out.println("Conexão realizada com sucesso!");
            } else {
                System.out.println("Falha na conexão.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
