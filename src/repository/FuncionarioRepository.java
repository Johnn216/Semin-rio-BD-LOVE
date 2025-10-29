package repository;

import model.Funcionario;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class FuncionarioRepository {

    public Funcionario adicionar(Funcionario funcionario) {
        String sql = "INSERT INTO Funcionario (nome, cargo, telefone) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getTelefone());
            
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                funcionario.setId(rs.getInt(1));
            }
            return funcionario;
            
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar funcionário: " + e.getMessage());
            return null;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    public ArrayList<Funcionario> listar() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Funcionario f = new Funcionario(
                    rs.getInt("id_funcionario"),
                    rs.getString("nome"),
                    rs.getString("cargo"),
                    rs.getString("telefone")
                );
                funcionarios.add(f);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return funcionarios;
    }

    /**
     * Busca um funcionário pelo ID (MÉTODO QUE FALTAVA)
     */
    public Funcionario buscarPorId(int id) {
        String sql = "SELECT * FROM Funcionario WHERE id_funcionario = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Funcionario(
                    rs.getInt("id_funcionario"),
                    rs.getString("nome"),
                    rs.getString("cargo"),
                    rs.getString("telefone")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por ID: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return null;
    }

    /**
     * Atualiza um funcionário (MÉTODO QUE FALTAVA)
     */
    public boolean atualizar(int id, Funcionario funcionarioComNovosDados) {
        String sql = "UPDATE Funcionario SET nome = ?, cargo = ?, telefone = ? WHERE id_funcionario = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, funcionarioComNovosDados.getNome());
            stmt.setString(2, funcionarioComNovosDados.getCargo());
            stmt.setString(3, funcionarioComNovosDados.getTelefone());
            stmt.setInt(4, id);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    /**
     * Remove um funcionário pelo ID (MÉTODO QUE FALTAVA)
     */
    public boolean removerPorId(int id) {
        String sql = "DELETE FROM Funcionario WHERE id_funcionario = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao remover funcionário: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }
}
