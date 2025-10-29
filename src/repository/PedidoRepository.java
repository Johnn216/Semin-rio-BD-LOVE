package repository;

import model.Pedido;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
// REMOVIDO: import java.util.Date; (Não era usado, como mostrava o aviso)

public class PedidoRepository {

    public Pedido adicionar(Pedido pedido) {
        String sql = "INSERT INTO pedido (id_cliente, id_funcionario, data_recebimento, data_entrega_prevista, data_entrega_real, status) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdFuncionario());
            stmt.setDate(3, pedido.getDataRecebimento()); 
            stmt.setDate(4, pedido.getDataEntregaPrevista());
            // data_entrega_real pode ser NULL (e o modelo Pedido.java garante isso no construtor)
            if (pedido.getDataEntregaReal() != null) {
                stmt.setDate(5, pedido.getDataEntregaReal());
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setString(6, pedido.getStatus());
            
            stmt.executeUpdate();

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

    public ArrayList<Pedido> listar() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pedido p = new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_funcionario"),
                    rs.getDate("data_recebimento"),
                    rs.getDate("data_entrega_prevista"),
                    rs.getDate("data_entrega_real"),
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

    /**
     * Busca um pedido pelo ID (MÉTODO QUE FALTAVA)
     */
    public Pedido buscarPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_funcionario"),
                    rs.getDate("data_recebimento"),
                    rs.getDate("data_entrega_prevista"),
                    rs.getDate("data_entrega_real"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedido por ID: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return null;
    }

    /**
     * Atualiza um pedido (MÉTODO QUE FALTAVA)
     */
    public boolean atualizar(int id, Pedido pedidoComNovosDados) {
        String sql = "UPDATE pedido SET id_cliente = ?, id_funcionario = ?, data_recebimento = ?, data_entrega_prevista = ?, data_entrega_real = ?, status = ? WHERE id_pedido = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, pedidoComNovosDados.getIdCliente());
            stmt.setInt(2, pedidoComNovosDados.getIdFuncionario());
            stmt.setDate(3, pedidoComNovosDados.getDataRecebimento()); 
            stmt.setDate(4, pedidoComNovosDados.getDataEntregaPrevista());
            if (pedidoComNovosDados.getDataEntregaReal() != null) {
                stmt.setDate(5, pedidoComNovosDados.getDataEntregaReal());
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            stmt.setString(6, pedidoComNovosDados.getStatus());
            stmt.setInt(7, id);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pedido: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }
}
