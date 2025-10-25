// src/repository/ServicoRepository.java
package repository;

import model.Servico;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class ServicoRepository {

    private Servico extrairServico(ResultSet rs) throws SQLException {
        Servico s = new Servico(
            rs.getString("descricao"),
            rs.getInt("tempo_estimado"),
            rs.getBigDecimal("preco_base")
        );
        s.setId(rs.getInt("id_servico"));
        return s;
    }

    // CREATE
    public Servico adicionar(Servico servico) {
        String sql = "INSERT INTO Servico (descricao, tempo_estimado, preco_base) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, servico.getDescricao());
            stmt.setInt(2, servico.getTempoEstimado());
            stmt.setBigDecimal(3, servico.getPrecoBase()); // setBigDecimal para tipo DECIMAL

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        servico.setId(generatedKeys.getInt(1));
                    }
                }
                return servico;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar serviço: " + e.getMessage());
        }
        return null;
    }
    
    // READ ALL
    public ArrayList<Servico> listar() {
        ArrayList<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM Servico";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                servicos.add(extrairServico(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar serviços: " + e.getMessage());
        }
        return servicos;
    }
    
    // READ BY ID
    public Servico buscarPorId(int id) {
        String sql = "SELECT * FROM Servico WHERE id_servico = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairServico(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar serviço: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean atualizar(int id, Servico servicoComNovosDados) {
        String sql = "UPDATE Servico SET descricao = ?, tempo_estimado = ?, preco_base = ? WHERE id_servico = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servicoComNovosDados.getDescricao());
            stmt.setInt(2, servicoComNovosDados.getTempoEstimado());
            stmt.setBigDecimal(3, servicoComNovosDados.getPrecoBase());
            stmt.setInt(4, id); 

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar serviço: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean removerPorId(int id) {
        String sql = "DELETE FROM Servico WHERE id_servico = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover serviço: " + e.getMessage());
            return false;
        }
    }
}
