package repository;

import model.Pagamento;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class PagamentoRepository {
    
    // Helper para converter java.util.Date para java.sql.Date
    private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        if (uDate == null) return null;
        return new java.sql.Date(uDate.getTime());
    }

    // Adiciona um novo registro de pagamento no banco de dados
    public Pagamento adicionar(Pagamento pagamento) {
        String sql = "INSERT INTO pagamentos (id_pedido, forma_pagamento, valor_total, data_pagamento, status_pagamento) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            // 1. Define os parâmetros
            stmt.setInt(1, pagamento.getIdPedido());
            stmt.setString(2, pagamento.getFormaPagamento());
            stmt.setDouble(3, pagamento.getValorTotal());
            
            // A data de pagamento pode ser NULL (se for inserido com status 'Pendente')
            if (pagamento.getDataPagamento() != null) {
                stmt.setDate(4, convertUtilToSql(pagamento.getDataPagamento()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            
            stmt.setString(5, pagamento.getStatusPagamento());
            
            stmt.executeUpdate(); // Executa a inserção

            // 2. Obtém o ID gerado (id_pagamento)
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pagamento.setId(rs.getInt(1));
            }
            return pagamento;
            
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar pagamento: " + e.getMessage());
            return null;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    /**
     * Lista todos os pagamentos.
     * @return Uma lista de objetos Pagamento.
     */
    public ArrayList<Pagamento> listar() {
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamentos";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Mapeia do banco para o objeto Pagamento
                Pagamento p = new Pagamento(
                    rs.getInt("id_pagamento"),
                    rs.getInt("id_pedido"),
                    rs.getString("forma_pagamento"),
                    rs.getDouble("valor_total"),
                    rs.getDate("data_pagamento"), // Pode ser null
                    rs.getString("status_pagamento")
                );
                pagamentos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pagamentos: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return pagamentos;
    }
    
    // ATENÇÃO: Os métodos buscarPorId, atualizar e removerPorId também devem ser implementados. 
    // Por exemplo, o método 'atualizar' seria crucial para mudar o status de 'Pendente' para 'Pago'.
}
