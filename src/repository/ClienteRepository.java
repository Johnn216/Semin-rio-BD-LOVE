package repository;

import model.Cliente;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteRepository {
    
    // ATENÇÃO: Os atributos ArrayList e proximoId foram REMOVIDOS.
    
    /**
     * Adiciona um novo cliente no banco de dados.
     * @param cliente O objeto Cliente a ser inserido.
     * @return O objeto Cliente com o ID gerado pelo banco.
     */
    public Cliente adicionar(Cliente cliente) {
        // SQL para inserção, usando ? para evitar SQL Injection
        String sql = "INSERT INTO clientes (nome, email, endereco, telefone) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            // PreparedStatement para executar o SQL e pedir as chaves geradas (o ID)
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            // 1. Define os parâmetros do INSERT
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getTelefone());
            
            stmt.executeUpdate(); // Executa a inserção

            // 2. Obtém o ID que foi gerado automaticamente pelo MySQL
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getInt(1)); // Define o ID de volta no objeto Java
            }
            return cliente;
            
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar cliente: " + e.getMessage());
            return null;
        } finally {
            // Garante que a conexão e os recursos sejam fechados
            DatabaseConnection.closeConnection(conn);
            // Em uma implementação completa, Statement e ResultSet também seriam fechados separadamente.
        }
    }

    /**
     * Lista todos os clientes do banco de dados.
     * @return Uma lista de objetos Cliente.
     */
    public ArrayList<Cliente> listar() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(); // Executa o SELECT
            
            while (rs.next()) {
                // Mapeia cada linha (registro) do banco para um objeto Cliente
                Cliente c = new Cliente(
                    rs.getInt("id_cliente"),      // Usa o construtor com ID (novo)
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("endereco"),
                    rs.getString("telefone")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return clientes;
    }

    /**
     * Busca um cliente pelo ID.
     * @param id O ID do cliente.
     * @return O objeto Cliente encontrado, ou null se não existir.
     */
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); // Define o ID para o WHERE
            rs = stmt.executeQuery();
            
            if (rs.next()) { // Se o registro for encontrado
                return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("endereco"),
                    rs.getString("telefone")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return null;
    }

    /**
     * Atualiza um cliente no banco de dados.
     * @param id O ID do cliente a ser atualizado.
     * @param clienteComNovosDados Objeto Cliente contendo os novos dados.
     * @return true se a atualização foi bem-sucedida.
     */
    public boolean atualizar(int id, Cliente clienteComNovosDados) {
        String sql = "UPDATE clientes SET nome = ?, email = ?, endereco = ?, telefone = ? WHERE id_cliente = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            // 1. Define os novos dados
            stmt.setString(1, clienteComNovosDados.getNome());
            stmt.setString(2, clienteComNovosDados.getEmail());
            stmt.setString(3, clienteComNovosDados.getEndereco());
            stmt.setString(4, clienteComNovosDados.getTelefone());
            stmt.setInt(5, id); // 2. Define o ID no WHERE
            
            int linhasAfetadas = stmt.executeUpdate(); // Executa a atualização
            return linhasAfetadas > 0; // Se 1 ou mais linhas foram alteradas, deu certo
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    /**
     * Remove um cliente pelo ID.
     * @param id O ID do cliente a ser removido.
     * @return true se a remoção foi bem-sucedida.
     */
    public boolean removerPorId(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int linhasAfetadas = stmt.executeUpdate(); // Executa a remoção
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao remover cliente: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }
}
