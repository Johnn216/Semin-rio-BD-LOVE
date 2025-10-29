package model;

public class Funcionario {
    private int id_funcionario;
    private String nome;
    private String cargo;      // Sincronizado com SQL
    private String telefone;   // Sincronizado com SQL

    // 1. Construtor para CRIAR (INSERT - SEM ID)
    public Funcionario(String nome, String cargo, String telefone) { 
        this.nome = nome;
        this.cargo = cargo;
        this.telefone = telefone;
    }

    // 2. Construtor para LER (SELECT - COM ID)
    public Funcionario(int id_funcionario, String nome, String cargo, String telefone) { 
        this.id_funcionario = id_funcionario;
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
        return "Funcionario [ID=" + id_funcionario + ", Nome='" + nome + "', Cargo=" + cargo + ", Telefone='" + telefone + "']";
    }
}
