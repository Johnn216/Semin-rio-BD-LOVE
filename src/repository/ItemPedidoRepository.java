package repository;

import model.ItemPedido;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class ItemPedidoRepository {

    // Adiciona um novo item de pedido no banco de dados
    public ItemPedido adicionar(ItemPedido itemPedido) {
        String sql = "INSERT INTO itens_pedido (id_pedido, id_servico, quantidade, valor_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            // 1. Define os parâmetros
            stmt.setInt(1, itemPedido.getIdPedido());
            stmt.setInt(2, itemPedido.getIdServico());
            stmt.setInt(3, itemPedido.getQuantidade());
            stmt.setDouble(4, itemPedido.getValorUnitario());
            stmt.setDouble(5, itemPedido.getSubtotal());
            
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

    /**
     * Lista todos os itens de pedido (útil para auditoria) ou busca por ID do Pedido.
     * @return Uma lista de objetos ItemPedido.
     */
    public ArrayList<ItemPedido> listar() {
        ArrayList<ItemPedido> itens = new ArrayList<>();
        String sql = "SELECT * FROM itens_pedido";
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
                    rs.getDouble("subtotal")
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
    
    // ATENÇÃO: Os métodos buscarPorId, atualizar e removerPorId devem ser implementados 
    // seguindo a mesma lógica das outras classes (ClienteRepository, etc.). 
    // Recomendo que você os complete como um exercício de fixação do JDBC!
}
