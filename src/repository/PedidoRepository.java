package repository;

import model.Pedido;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date; // Usaremos java.util.Date, compatível com java.sql.Date

public class PedidoRepository {

    // Helper para converter java.util.Date para java.sql.Date
    private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        return new java.sql.Date(uDate.getTime());
    }

    // Adiciona um novo pedido no banco de dados
    public Pedido adicionar(Pedido pedido) {
        String sql = "INSERT INTO pedidos (id_cliente, id_funcionario, data_recebimento, data_entrega_prevista, data_entrega_real, status) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            // 1. Define os parâmetros
            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdFuncionario());
            // Conversão de java.util.Date para java.sql.Date para o banco:
            stmt.setDate(3, convertUtilToSql(pedido.getDataRecebimento()));
            stmt.setDate(4, convertUtilToSql(pedido.getDataEntregaPrevista()));
            
            // data_entrega_real pode ser NULL no início:
            if (pedido.getDataEntregaReal() != null) {
                stmt.setDate(5, convertUtilToSql(pedido.getDataEntregaReal()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            
            stmt.setString(6, pedido.getStatus());
            
            stmt.executeUpdate();

            // 2. Obtém o ID gerado
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pedido.setId(rs.getInt(1));
            }
            return pedido;
            
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar pedido: " + e.getMessage());
            return null;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    // Lista todos os pedidos
    public ArrayList<Pedido> listar() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Mapeia do banco para o objeto Pedido
                Pedido p = new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_funcionario"),
                    rs.getDate("data_recebimento"),
                    rs.getDate("data_entrega_prevista"),
                    rs.getDate("data_entrega_real"), // Pode ser null
                    rs.getString("status")
                );
                pedidos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return pedidos;
    }
    
    // ATENÇÃO: Os métodos buscarPorId, atualizar e removerPorId também devem ser implementados
    // seguindo a mesma lógica de ClienteRepository.java, mas adaptados para as colunas de Pedido.
    // Sugestão de exercício para você: complete-os!
}
