package dao;

import modelo.Quarto;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuartoDAO {

    public boolean existeNumero(String numero) {
        String sql = "SELECT COUNT(*) FROM quarto WHERE numero = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar duplicidade: " + e.getMessage());
        }
        return false;
    }

    public boolean inserir(Quarto quarto) {
        if (existeNumero(quarto.getNumero())) {
            System.err.println("Tentativa de cadastro duplicado: Quarto " + quarto.getNumero());
            return false;
        }

        String sql = "INSERT INTO quarto (numero, tipo, ocupacao_maxima, andar, valor_diaria, status, wifi_disponivel, tem_cama_extra) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, quarto.getNumero());
            ps.setString(2, quarto.getTipo());
            ps.setInt(3, quarto.getOcupacaoMaxima());
            ps.setInt(4, quarto.getAndar());
            ps.setDouble(5, quarto.getValorDiaria());
            ps.setString(6, quarto.getStatus());
            ps.setBoolean(7, quarto.isWifiDisponivel());
            ps.setBoolean(8, quarto.isTemCamaExtra());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        quarto.setId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir quarto: " + e.getMessage());
        }
        return false;
    }

    public boolean atualizar(Quarto quarto) {

        String sql = "UPDATE quarto SET numero=?, tipo=?, ocupacao_maxima=?, andar=?, valor_diaria=?, status=?, wifi_disponivel=?, tem_cama_extra=? "
                   + "WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, quarto.getNumero());
            ps.setString(2, quarto.getTipo());
            ps.setInt(3, quarto.getOcupacaoMaxima());
            ps.setInt(4, quarto.getAndar());
            ps.setDouble(5, quarto.getValorDiaria());
            ps.setString(6, quarto.getStatus());
            ps.setBoolean(7, quarto.isWifiDisponivel());
            ps.setBoolean(8, quarto.isTemCamaExtra());
            ps.setInt(9, quarto.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar quarto: " + e.getMessage());
        }
        return false;
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM quarto WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar quarto: " + e.getMessage());
        }
        return false;
    }

    public Quarto buscarPorId(int id) {
        String sql = "SELECT * FROM quarto WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearQuarto(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar quarto: " + e.getMessage());
        }
        return null;
    }

    public List<Quarto> buscarTodos() {
        List<Quarto> lista = new ArrayList<>();
        String sql = "SELECT * FROM quarto";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearQuarto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar quartos: " + e.getMessage());
        }
        return lista;
    }

    private Quarto mapearQuarto(ResultSet rs) throws SQLException {
        Quarto q = new Quarto();
        q.setId(rs.getInt("id"));
        q.setNumero(rs.getString("numero"));
        q.setTipo(rs.getString("tipo"));
        q.setOcupacaoMaxima(rs.getInt("ocupacao_maxima"));
        q.setAndar(rs.getInt("andar"));
        q.setValorDiaria(rs.getDouble("valor_diaria"));
        q.setStatus(rs.getString("status"));
        q.setWifiDisponivel(rs.getBoolean("wifi_disponivel"));
        q.setTemCamaExtra(rs.getBoolean("tem_cama_extra"));
        return q;
    }
}
