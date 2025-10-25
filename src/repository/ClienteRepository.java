// src/repository/ClienteRepository.java
package repository;

import model.Cliente;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class ClienteRepository {

    // Método auxiliar para criar um objeto Cliente a partir de um ResultSet
    private Cliente extrairCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente(
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("endereco"),
            rs.getString("telefone")
        );
        c.setId(rs.getInt("id_cliente")); 
        return c;
    }

    // CREATE
    public Cliente adicionar(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, email, endereco, telefone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getTelefone());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setId(generatedKeys.getInt(1));
                    }
                }
                return cliente;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar cliente: " + e.getMessage());
        }
        return null;
    }

    // READ ALL
    public ArrayList<Cliente> listar() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(extrairCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    // READ BY ID
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM Cliente WHERE id_cliente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean atualizar(int id, Cliente clienteComNovosDados) {
        String sql = "UPDATE Cliente SET nome = ?, email = ?, endereco = ?, telefone = ? WHERE id_cliente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clienteComNovosDados.getNome());
            stmt.setString(2, clienteComNovosDados.getEmail());
            stmt.setString(3, clienteComNovosDados.getEndereco());
            stmt.setString(4, clienteComNovosDados.getTelefone());
            stmt.setInt(5, id); 

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean removerPorId(int id) {
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover cliente: " + e.getMessage());
            return false;
        }
    }
}
