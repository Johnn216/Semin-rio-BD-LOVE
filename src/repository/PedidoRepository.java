// src/repository/PedidoRepository.java
package repository;

import model.Pedido;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class PedidoRepository {

    private Pedido extrairPedido(ResultSet rs) throws SQLException {
        Pedido p = new Pedido(
            rs.getInt("id_cliente"),
            rs.getInt("id_funcionario"),
            rs.getDate("data_recebimento"),
            rs.getDate("data_entrega_prevista")
        );
        p.setId(rs.getInt("id_pedido"));
        // A data real pode ser NULL no DB, getDate lida com isso.
        p.setDataEntregaReal(rs.getDate("data_entrega_real")); 
        p.setStatus(rs.getString("status"));
        return p;
    }

    // CREATE
    public Pedido adicionar(Pedido pedido) {
        String sql = "INSERT INTO Pedido (id_cliente, id_funcionario, data_recebimento, data_entrega_prevista, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdFuncionario());
            stmt.setDate(3, pedido.getDataRecebimento()); // setDate para java.sql.Date
            stmt.setDate(4, pedido.getDataEntregaPrevista());
            stmt.setString(5, pedido.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pedido.setId(generatedKeys.getInt(1));
                    }
                }
                return pedido;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar pedido: " + e.getMessage());
        }
        return null;
    }

    // READ ALL
    public ArrayList<Pedido> listar() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pedidos.add(extrairPedido(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos: " + e.getMessage());
        }
        return pedidos;
    }
    
    // READ BY ID
    public Pedido buscarPorId(int id) {
        String sql = "SELECT * FROM Pedido WHERE id_pedido = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairPedido(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedido: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean atualizar(int id, Pedido pedidoComNovosDados) {
        // Note que o UPDATE permite alterar id_cliente, datas e status, incluindo a data real de entrega
        String sql = "UPDATE Pedido SET id_cliente = ?, id_funcionario = ?, data_entrega_prevista = ?, data_entrega_real = ?, status = ? WHERE id_pedido = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedidoComNovosDados.getIdCliente());
            stmt.setInt(2, pedidoComNovosDados.getIdFuncionario());
            stmt.setDate(3, pedidoComNovosDados.getDataEntregaPrevista());
            stmt.setDate(4, pedidoComNovosDados.getDataEntregaReal()); // setDate lida com NULL
            stmt.setString(5, pedidoComNovosDados.getStatus());
            stmt.setInt(6, id); 

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pedido: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean removerPorId(int id) {
        // CUIDADO: Este DELETE só funciona se não houver Pagamento ou ItemPedido associado!
        String sql = "DELETE FROM Pedido WHERE id_pedido = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover pedido. Verifique se existem itens ou pagamentos associados: " + e.getMessage());
            return false;
        }
    }
}
