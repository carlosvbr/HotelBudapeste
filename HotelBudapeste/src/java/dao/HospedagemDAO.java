package dao;

import modelo.Hospedagem;
import modelo.Hospede;
import modelo.Quarto;
import util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HospedagemDAO {

    public boolean inserir(Hospedagem hospedagem) {
        if (verificarReservaConflito(hospedagem.getQuarto().getId(), hospedagem.getDataEntrada(), hospedagem.getDataSaida(), -1)) {
            System.err.println("Erro: Quarto já reservado nesse período!");
            return false;
        }

        String sqlHospedagem = "INSERT INTO hospedagem (id_quarto, data_entrada, data_saida, valor_total, status, forma_pagamento) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlHospedagemHospede = "INSERT INTO hospedagem_hospede (id_hospedagem, id_hospede) VALUES (?, ?)";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false); 

            try (PreparedStatement psHosp = conn.prepareStatement(sqlHospedagem, Statement.RETURN_GENERATED_KEYS)) {
                psHosp.setInt(1, hospedagem.getQuarto().getId());
                psHosp.setDate(2, Date.valueOf(hospedagem.getDataEntrada()));
                psHosp.setDate(3, Date.valueOf(hospedagem.getDataSaida()));
                psHosp.setDouble(4, hospedagem.getValorTotal());
                psHosp.setString(5, hospedagem.getStatus());
                psHosp.setString(6, hospedagem.getFormaPagamento());

                psHosp.executeUpdate();
                
                try (ResultSet rs = psHosp.getGeneratedKeys()) {
                    if (rs.next()) {
                        hospedagem.setId(rs.getInt(1));
                    }
                }
            }

            try (PreparedStatement psHospHospede = conn.prepareStatement(sqlHospedagemHospede)) {
                for (Hospede h : hospedagem.getHospedes()) {
                    psHospHospede.setInt(1, hospedagem.getId());
                    psHospHospede.setInt(2, h.getId());
                    psHospHospede.addBatch();
                }
                psHospHospede.executeBatch();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Hospedagem hospedagem) {
        if (verificarReservaConflito(hospedagem.getQuarto().getId(), hospedagem.getDataEntrada(), hospedagem.getDataSaida(), hospedagem.getId())) {
            System.err.println("Erro: Conflito de datas na atualização!");
            return false;
        }

        String sqlUpdate = "UPDATE hospedagem SET id_quarto=?, data_entrada=?, data_saida=?, valor_total=?, status=?, forma_pagamento=? WHERE id=?";
        String sqlDeleteHospedes = "DELETE FROM hospedagem_hospede WHERE id_hospedagem=?";
        String sqlInsertHospedes = "INSERT INTO hospedagem_hospede (id_hospedagem, id_hospede) VALUES (?, ?)";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setInt(1, hospedagem.getQuarto().getId());
                ps.setDate(2, Date.valueOf(hospedagem.getDataEntrada()));
                ps.setDate(3, Date.valueOf(hospedagem.getDataSaida()));
                ps.setDouble(4, hospedagem.getValorTotal());
                ps.setString(5, hospedagem.getStatus());
                ps.setString(6, hospedagem.getFormaPagamento());
                ps.setInt(7, hospedagem.getId());
                ps.executeUpdate();
            }

            try (PreparedStatement psDel = conn.prepareStatement(sqlDeleteHospedes)) {
                psDel.setInt(1, hospedagem.getId());
                psDel.executeUpdate();
            }

            try (PreparedStatement psIns = conn.prepareStatement(sqlInsertHospedes)) {
                for (Hospede h : hospedagem.getHospedes()) {
                    psIns.setInt(1, hospedagem.getId());
                    psIns.setInt(2, h.getId());
                    psIns.addBatch();
                }
                psIns.executeBatch();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        String sqlHospede = "DELETE FROM hospedagem_hospede WHERE id_hospedagem=?";
        String sqlHospedagem = "DELETE FROM hospedagem WHERE id=?";

        try (Connection conn = Conexao.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlHospede);
                 PreparedStatement ps2 = conn.prepareStatement(sqlHospedagem)) {
                
                ps1.setInt(1, id);
                ps1.executeUpdate();

                ps2.setInt(1, id);
                int rows = ps2.executeUpdate();

                conn.commit();
                return rows > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    public Hospedagem buscarPorId(int id) {
        String sql = "SELECT * FROM hospedagem WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Hospedagem h = mapearHospedagem(rs);
                    h.setHospedes(buscarHospedesPorHospedagem(h.getId()));
                    return h;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Hospedagem> buscarTodos() {
        List<Hospedagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM hospedagem";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Hospedagem h = mapearHospedagem(rs);
                h.setHospedes(buscarHospedesPorHospedagem(h.getId()));
                lista.add(h);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos: " + e.getMessage());
        }
        return lista;
    }

    public List<Hospede> buscarHospedesPorHospedagem(int idHospedagem) {
        List<Hospede> hospedes = new ArrayList<>();
        String sql = "SELECT h.* FROM hospede h JOIN hospedagem_hospede hh ON h.id = hh.id_hospede WHERE hh.id_hospedagem = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idHospedagem);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Hospede hospede = new Hospede();
                    hospede.setId(rs.getInt("id"));
                    hospede.setNome(rs.getString("nome"));
                    
                    hospede.setSobrenome(rs.getString("sobrenome"));
                    
                    hospedes.add(hospede);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar hospedes vinculados: " + e.getMessage());
        }
        return hospedes;
    }

    public boolean verificarReservaConflito(int idQuarto, LocalDate entrada, LocalDate saida, int idIgnorar) {
        String sql = "SELECT COUNT(*) FROM hospedagem " +
                     "WHERE id_quarto = ? " +
                     "AND id <> ? " + 
                     "AND status <> 'Cancelada' " +
                     "AND data_entrada < ? " + 
                     "AND data_saida > ?";   

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idQuarto);
            ps.setInt(2, idIgnorar);
            ps.setDate(3, Date.valueOf(saida));   
            ps.setDate(4, Date.valueOf(entrada)); 

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar conflito: " + e.getMessage());
        }
        return false; 
    }

    private Hospedagem mapearHospedagem(ResultSet rs) throws SQLException {
        Hospedagem h = new Hospedagem();
        h.setId(rs.getInt("id"));
        Quarto q = new Quarto();
        q.setId(rs.getInt("id_quarto"));
        h.setQuarto(q);
        h.setDataEntrada(rs.getDate("data_entrada").toLocalDate());
        h.setDataSaida(rs.getDate("data_saida").toLocalDate());
        h.setValorTotal(rs.getDouble("valor_total"));
        h.setStatus(rs.getString("status"));
        h.setFormaPagamento(rs.getString("forma_pagamento"));
        h.setHospedes(new ArrayList<>());
        return h;
    }
}