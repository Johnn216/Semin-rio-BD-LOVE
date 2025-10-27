package repository;

import model.Servico;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class ServicoRepository {

    // Adiciona um novo serviço no banco de dados
    public Servico adicionar(Servico servico) {
        String sql = "INSERT INTO servicos (descricao, tempo_estimado, preco_base) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            // 1. Define os parâmetros do INSERT
            stmt.setString(1, servico.getDescricao());
            stmt.setInt(2, servico.getTempoEstimado());
            stmt.setDouble(3, servico.getPrecoBase());
            
            stmt.executeUpdate(); // Executa a inserção

            // 2. Obtém o ID que foi gerado
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                servico.setId(rs.getInt(1));
            }
            return servico;
            
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar serviço: " + e.getMessage());
            return null;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    // Lista todos os serviços do banco de dados
    public ArrayList<Servico> listar() {
        ArrayList<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(); // Executa o SELECT
            
            while (rs.next()) {
                // Mapeia cada linha (registro) do banco para um objeto Servico
                Servico s = new Servico(
                    rs.getInt("id_servico"), // Usa o construtor com ID
                    rs.getString("descricao"),
                    rs.getInt("tempo_estimado"),
                    rs.getDouble("preco_base")
                );
                servicos.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar serviços: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return servicos;
    }

    // Busca um serviço pelo ID
    public Servico buscarPorId(int id) {
        String sql = "SELECT * FROM servicos WHERE id_servico = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); 
            rs = stmt.executeQuery();
            
            if (rs.next()) { // Se o registro for encontrado
                return new Servico(
                    rs.getInt("id_servico"),
                    rs.getString("descricao"),
                    rs.getInt("tempo_estimado"),
                    rs.getDouble("preco_base")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar serviço por ID: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return null;
    }

    // Atualiza um serviço no banco de dados
    public boolean atualizar(int id, Servico servicoComNovosDados) {
        String sql = "UPDATE servicos SET descricao = ?, tempo_estimado = ?, preco_base = ? WHERE id_servico = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            // 1. Define os novos dados
            stmt.setString(1, servicoComNovosDados.getDescricao());
            stmt.setInt(2, servicoComNovosDados.getTempoEstimado());
            stmt.setDouble(3, servicoComNovosDados.getPrecoBase());
            stmt.setInt(4, id); // 2. Define o ID no WHERE

            int linhasAfetadas = stmt.executeUpdate(); 
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar serviço: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    // Remove um serviço pelo ID
    public boolean removerPorId(int id) {
        String sql = "DELETE FROM servicos WHERE id_servico = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int linhasAfetadas = stmt.executeUpdate(); 
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao remover serviço: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }
}
