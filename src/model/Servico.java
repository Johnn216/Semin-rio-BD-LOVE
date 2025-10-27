package model;

public class Servico {
    private int id_servico;
    private String descricao;
    private int tempo_estimado;
    private double preco_base;

    // 1. Construtor para CRIAR um novo Serviço (INSERT no banco - SEM ID)
    public Servico(String descricao, int tempo_estimado, double preco_base) {
        this.descricao = descricao;
        this.tempo_estimado = tempo_estimado;
        this.preco_base = preco_base;
    }

    // 2. Construtor para LER do banco de dados (SELECT - COM ID)
    public Servico(int id_servico, String descricao, int tempo_estimado, double preco_base) {
        this.id_servico = id_servico; // Atribui o ID lido do banco
        this.descricao = descricao;
        this.tempo_estimado = tempo_estimado;
        this.preco_base = preco_base;
    }

    // Getters e Setters
    public int getId() { return id_servico; }
    public void setId(int id) { this.id_servico = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getTempoEstimado() { return tempo_estimado; }
    public void setTempoEstimado(int tempo_estimado) { this.tempo_estimado = tempo_estimado; }
    public double getPrecoBase() { return preco_base; }
    public void setPrecoBase(double preco_base) { this.preco_base = preco_base; }

    @Override
    public String toString() {
        return "Serviço [ID=" + id_servico + ", Descrição='" + descricao + "', Tempo Estimado=" + tempo_estimado + "h, Preço Base=" + preco_base + "]";
    }
}
