// src/repository/FuncionarioRepository.java
package repository;

import model.Funcionario;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class FuncionarioRepository {

    private Funcionario extrairFuncionario(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario(
            rs.getString("nome"),
            rs.getString("cargo"),
            rs.getString("telefone")
        );
        f.setId(rs.getInt("id_funcionario")); 
        return f;
    }

    // CREATE
    public Funcionario adicionar(Funcionario funcionario) {
        String sql = "INSERT INTO Funcionario (nome, cargo, telefone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getTelefone());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        funcionario.setId(generatedKeys.getInt(1));
                    }
                }
                return funcionario;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar funcionário: " + e.getMessage());
        }
        return null;
    }
    
    // READ ALL
    public ArrayList<Funcionario> listar() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                funcionarios.add(extrairFuncionario(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return funcionarios;
    }

    // READ BY ID
    public Funcionario buscarPorId(int id) {
        String sql = "SELECT * FROM Funcionario WHERE id_funcionario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairFuncionario(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean atualizar(int id, Funcionario funcionarioComNovosDados) {
        String sql = "UPDATE Funcionario SET nome = ?, cargo = ?, telefone = ? WHERE id_funcionario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionarioComNovosDados.getNome());
            stmt.setString(2, funcionarioComNovosDados.getCargo());
            stmt.setString(3, funcionarioComNovosDados.getTelefone());
            stmt.setInt(4, id); 

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean removerPorId(int id) {
        String sql = "DELETE FROM Funcionario WHERE id_funcionario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover funcionário: " + e.getMessage());
            return false;
        }
    }
}
