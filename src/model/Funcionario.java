// src/model/Funcionario.java
package model;

public class Funcionario {
    private int id_funcionario; // Nome do SQL
    private String nome;
    private String cargo;
    private String telefone;

    public Funcionario(String nome, String cargo, String telefone) {
        this.nome = nome;
        this.cargo = cargo;
        this.telefone = telefone;
    }

    // Getters e Setters
    public int getId() { return id_funcionario; }
    public void setId(int id) { this.id_funcionario = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() {
        return "Funcionario [ID=" + id_funcionario + ", Nome='" + nome + "', Cargo='" + cargo + "', Telefone='" + telefone + "']";
    }
}
