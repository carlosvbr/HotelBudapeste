package util;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConexao {
    Connection getConnection() throws SQLException;
}
