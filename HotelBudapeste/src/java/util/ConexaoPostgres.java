package util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoPostgres implements IConexao {

    @Override
    public Connection getConnection() throws SQLException {
        return Conexao.getConnection(); 
    }
}
