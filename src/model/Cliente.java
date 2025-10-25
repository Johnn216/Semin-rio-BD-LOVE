// src/model/Cliente.java
package model;

public class Cliente {
    private int id_cliente; // Nome do SQL
    private String nome;
    private String email;
    private String endereco; // Novo atributo
    private String telefone;

    public Cliente(String nome, String email, String endereco, String telefone) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    // Getters e Setters
    public int getId() { return id_cliente; }
    public void setId(int id) { this.id_cliente = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() {
        return "Cliente [ID=" + id_cliente + ", Nome='" + nome + "', Email='" + email + "', Endereço='" + endereco + "', Telefone='" + telefone + "']";
    }
}
