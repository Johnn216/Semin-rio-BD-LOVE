package model;

// Classes abstratas não podem ser instanciadas diretamente
public abstract class Pessoa {
    
    // Campos comuns (ID e Nome)
    protected int id;
    protected String nome;

    // Construtor para leitura (com ID)
    public Pessoa(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    // Construtor para criação (sem ID)
    public Pessoa(String nome) {
        this.nome = nome;
    }

    // Getters e Setters (compartilhados)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    // Método Abstrato: Força as subclasses a implementarem algo específico
    public abstract String getIdentificacaoUnica();
}
