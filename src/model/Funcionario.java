package model;

public class Funcionario {
    private int id_funcionario; // ID do banco
    private String nome;
    private double salario;
    private String matricula;

    // 1. Construtor para CRIAR um novo funcionário (INSERT no banco - SEM ID)
    public Funcionario(String nome, double salario, String matricula) {
        this.nome = nome;
        this.salario = salario;
        this.matricula = matricula;
    }

    // 2. Construtor para LER do banco de dados (SELECT - COM ID)
    public Funcionario(int id_funcionario, String nome, double salario, String matricula) {
        this.id_funcionario = id_funcionario; // Atribui o ID lido do banco
        this.nome = nome;
        this.salario = salario;
        this.matricula = matricula;
    }

    // Getters e Setters
    public int getId() { return id_funcionario; }
    public void setId(int id) { this.id_funcionario = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    @Override
    public String toString() {
        return "Funcionario [ID=" + id_funcionario + ", Nome='" + nome + "', Salario=" + salario + ", Matrícula='" + matricula + "']";
    }
}
