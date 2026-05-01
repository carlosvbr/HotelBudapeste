package dao;

import modelo.Hospede;
import modelo.HospedeVip;
import builder.HospedeBuilder;
import util.IConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospedeDAO implements IHospedeDAO {

    private final IConexao conexao;

    public HospedeDAO(IConexao conexao) {
        this.conexao = conexao;
    }

    @Override
    public boolean inserir(Hospede hospede) {
        String sql = "INSERT INTO hospede (nome, sobrenome, cpf, rg, data_nascimento, email, telefone, nacionalidade, endereco, vip) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, hospede.getNome());
            ps.setString(2, hospede.getSobrenome());
            ps.setString(3, hospede.getCpf());
            ps.setString(4, hospede.getRg());
            ps.setDate(5, hospede.getDataNascimento() != null ? Date.valueOf(hospede.getDataNascimento()) : null);
            ps.setString(6, hospede.getEmail());
            ps.setString(7, hospede.getTelefone());
            ps.setString(8, hospede.getNacionalidade());
            ps.setString(9, hospede.getEndereco());
            ps.setBoolean(10, hospede instanceof HospedeVip); // true se VIP

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        hospede.setId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir hóspede: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean atualizar(Hospede hospede) {
        String sql = "UPDATE hospede SET nome=?, sobrenome=?, cpf=?, rg=?, data_nascimento=?, email=?, telefone=?, nacionalidade=?, endereco=?, vip=? "
                   + "WHERE id=?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hospede.getNome());
            ps.setString(2, hospede.getSobrenome());
            ps.setString(3, hospede.getCpf());
            ps.setString(4, hospede.getRg());
            ps.setDate(5, hospede.getDataNascimento() != null ? Date.valueOf(hospede.getDataNascimento()) : null);
            ps.setString(6, hospede.getEmail());
            ps.setString(7, hospede.getTelefone());
            ps.setString(8, hospede.getNacionalidade());
            ps.setString(9, hospede.getEndereco());
            ps.setBoolean(10, hospede instanceof HospedeVip); // VIP
            ps.setInt(11, hospede.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar hóspede: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deletar(int id) {
        String sql = "DELETE FROM hospede WHERE id=?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar hóspede: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Hospede buscarPorId(int id) {
        String sql = "SELECT * FROM hospede WHERE id=?";
        try (Connection conn = conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearHospede(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar hóspede: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Hospede> buscarTodos() {
        List<Hospede> lista = new ArrayList<>();
        String sql = "SELECT * FROM hospede";
        try (Connection conn = conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearHospede(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar hóspedes: " + e.getMessage());
        }
        return lista;
    }

    private Hospede mapearHospede(ResultSet rs) throws SQLException {
        Hospede hospede;
        boolean isVip = rs.getBoolean("vip");
        if (isVip) {
            hospede = new HospedeVip(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("sobrenome"),
                rs.getString("cpf"),
                rs.getString("rg"),
                rs.getDate("data_nascimento") != null ? rs.getDate("data_nascimento").toLocalDate() : null,
                rs.getString("email"),
                rs.getString("telefone"),
                rs.getString("nacionalidade"),
                rs.getString("endereco")
            );
        } else {
            hospede = new HospedeBuilder()
                .comId(rs.getInt("id"))
                .comNome(rs.getString("nome"))
                .comSobrenome(rs.getString("sobrenome"))
                .comCpf(rs.getString("cpf"))
                .comRg(rs.getString("rg"))
                .comDataNascimento(rs.getDate("data_nascimento") != null ? rs.getDate("data_nascimento").toLocalDate() : null)
                .comEmail(rs.getString("email"))
                .comTelefone(rs.getString("telefone"))
                .comNacionalidade(rs.getString("nacionalidade"))
                .comEndereco(rs.getString("endereco"))
                .build();
        }
        return hospede;
    }
}
