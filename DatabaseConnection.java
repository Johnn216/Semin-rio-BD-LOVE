// util/DatabaseConnection.java

package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // 1. ENDEREÇO do banco de dados (SGBD:mysql://servidor:porta/nome_do_banco)
    private static final String URL = "jdbc:mysql://localhost:3306/lavanderia_db?useTimezone=true&serverTimezone=UTC";
    
    // 2. USUÁRIO do MySQL (geralmente 'root' em ambientes de teste)
    private static final String USER = 'root'; 
    
    // 3. SENHA do usuário
    private static final String PASSWORD = 'ceub123456'; 

    /**
     * Tenta estabelecer e retornar uma conexão com o banco de dados.
     * @return Uma instância de Connection.
     * @throws SQLException Se ocorrer um erro de conexão.
     */
    public static Connection getConnection() throws SQLException {
        // O DriverManager tentará carregar o driver (Connector/J) e abrir a conexão.
        try {
            // Opcional: Carregar explicitamente o driver, bom para clareza em sistemas antigos
            // Class.forName("com.mysql.cj.jdbc.Driver"); 
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Se a conexão falhar, lança a exceção para quem chamou o método
            throw new SQLException("Falha na conexão com o banco de dados. Verifique a URL, USER e PASSWORD. Erro: " + e.getMessage(), e);
        }
    }
}
