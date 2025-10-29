package repository;

import model.Pagamento;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class PagamentoRepository {
    
    // REMOVIDA a função convertUtilToSql e o import java.util.Date,
    // pois o modelo Pagamento.java já usa java.sql.Date diretamente.

    /**
     * Adiciona um novo registro de pagamento no banco de dados.
     */
    public Pagamento adicionar(Pagamento pagamento) {
        // Altere 'pagamentos' para 'Pagamento' se for o nome da tabela no seu SQL
        String sql = "INSERT INTO Pagamento (id_pedido, forma_pagamento, valor_total, data_pagamento, status_pagamento) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            // 1. Define os parâmetros
            stmt.setInt(1, pagamento.getIdPedido());
            stmt.setString(2, pagamento.getFormaPagamento());
            stmt.setDouble(3, pagamento.getValorTotal()); // CORRIGIDO: Agora usa setDouble
            
            // Data de pagamento
            if (pagamento.getDataPagamento() != null) {
                stmt.setDate(4, pagamento.getDataPagamento());
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            stmt.setString(5, pagamento.getStatusPagamento());
            
            stmt.executeUpdate();

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
     */
    public ArrayList<Pagamento> listar() {
        ArrayList<Pagamento> pagamentos = new ArrayList<>();
        // Altere 'pagamentos' para 'Pagamento' se for o nome da tabela no seu SQL
        String sql = "SELECT * FROM Pagamento";
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
                    rs.getDouble("valor_total"), // CORRIGIDO: Agora usa rs.getDouble
                    rs.getDate("data_pagamento"), 
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
    
    // ... outros métodos ...
}
