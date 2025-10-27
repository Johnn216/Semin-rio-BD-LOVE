package repository;

import model.Funcionario;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class FuncionarioRepository {

    // Adiciona um novo funcionário no banco de dados
    public Funcionario adicionar(Funcionario funcionario) {
        // SQL para inserção. Colunas: nome, cargo, telefone
        String sql = "INSERT INTO funcionarios (nome, cargo, telefone) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            // 1. Define os parâmetros do INSERT
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo()); // O atributo cargo foi definido no seu SQL
            stmt.setString(3, funcionario.getTelefone());
            
            stmt.executeUpdate(); // Executa a inserção

            // 2. Obtém o ID que foi gerado
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

    // Lista todos os funcionários do banco de dados
    public ArrayList<Funcionario> listar() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(); // Executa o SELECT
            
            while (rs.next()) {
                // Mapeia cada linha (registro) do banco para um objeto Funcionario
                Funcionario f = new Funcionario(
                    rs.getInt("id_funcionario"), // Usa o construtor com ID
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

    // Busca um funcionário pelo ID
    public Funcionario buscarPorId(int id) {
        String sql = "SELECT * FROM funcionarios WHERE id_funcionario = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); // Define o ID para o WHERE
            rs = stmt.executeQuery();
            
            if (rs.next()) { // Se o registro for encontrado
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

    // Atualiza um funcionário no banco de dados
    public boolean atualizar(int id, Funcionario funcionarioComNovosDados) {
        String sql = "UPDATE funcionarios SET nome = ?, cargo = ?, telefone = ? WHERE id_funcionario = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            // 1. Define os novos dados
            stmt.setString(1, funcionarioComNovosDados.getNome());
            stmt.setString(2, funcionarioComNovosDados.getCargo());
            stmt.setString(3, funcionarioComNovosDados.getTelefone());
            stmt.setInt(4, id); // 2. Define o ID no WHERE

            int linhasAfetadas = stmt.executeUpdate(); 
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    // Remove um funcionário pelo ID
    public boolean removerPorId(int id) {
        String sql = "DELETE FROM funcionarios WHERE id_funcionario = ?";
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
