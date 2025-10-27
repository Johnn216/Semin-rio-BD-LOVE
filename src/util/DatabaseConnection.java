package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // 1. ENDEREÇO do banco de dados
    // Note que ajustei o nome do banco para 'lavanderia' (como usamos no SQL), mas mantenha 'lavanderia_db' se for o nome correto.
    private static final String URL = "jdbc:mysql://localhost:3306/lavanderia_db?useTimezone=true&serverTimezone=UTC";
    
    // 2. USUÁRIO - CORRIGIDO: Aspas duplas ("")
    private static final String USER = "root"; 
    
    // 3. SENHA - CORRIGIDO: Aspas duplas ("")
    private static final String PASSWORD = "ceub123456"; 

    // Bloco estático para garantir que o driver seja carregado
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("FATAL: Driver MySQL não encontrado. Verifique a biblioteca Connector/J.");
            // Opcional: throw new RuntimeException(e); para parar o programa
        }
    }

    /**
     * Tenta estabelecer e retornar uma conexão.
     * @return Uma instância de Connection.
     * @throws SQLException Se ocorrer um erro de conexão.
     */
    public static Connection getConnection() throws SQLException {
        // Removemos o try-catch desnecessário aqui, pois o chamador deve lidar com o SQLException.
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /**
     * Adicionado: Fecha a conexão de forma segura.
     * @param connection A conexão a ser fechada.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
