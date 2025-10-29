package repository;

import model.ItemPedido;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class ItemPedidoRepository {

    // Adiciona um novo item de pedido no banco de dados
    public ItemPedido adicionar(ItemPedido itemPedido) {
        String sql = "INSERT INTO itempedido (id_pedido, id_servico, quantidade, valor_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            // 1. Define os parâmetros - Alinhado com o modelo ItemPedido (usa double)
            stmt.setInt(1, itemPedido.getIdPedido());
            stmt.setInt(2, itemPedido.getIdServico());
            stmt.setInt(3, itemPedido.getQuantidade());
            stmt.setDouble(4, itemPedido.getValorUnitario());
            stmt.setDouble(5, itemPedido.getSubtotal()); // Agora usa getSubtotal()

            stmt.executeUpdate(); // Executa a inserção

            // 2. Obtém o ID gerado
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                itemPedido.setId(rs.getInt(1));
            }
            return itemPedido;
            
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar item de pedido: " + e.getMessage());
            return null;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    // Lista todos os itens de pedido (útil para auditoria) ou busca por ID do Pedido.
    public ArrayList<ItemPedido> listar() {
        ArrayList<ItemPedido> itens = new ArrayList<>();
        String sql = "SELECT * FROM itempedido";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Mapeia do banco para o objeto ItemPedido
                ItemPedido ip = new ItemPedido(
                    rs.getInt("id_item"), // ID próprio do Item
                    rs.getInt("id_pedido"),
                    rs.getInt("id_servico"),
                    rs.getInt("quantidade"),
                    rs.getDouble("valor_unitario"),
                    rs.getDouble("subtotal") // Mapeia o subtotal do banco
                );
                itens.add(ip);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar itens de pedido: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return itens;
    }
    
    // Você pode adicionar buscarPorId, atualizar e removerPorId aqui, se necessário.
}
