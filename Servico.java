
package model;

import java.math.BigDecimal;

public class Servico {
    private int id_servico;
    private String descricao;
    private int tempo_estimado;
    private BigDecimal preco_base;

    public Servico(String descricao, int tempo_estimado, BigDecimal preco_base) {
        this.descricao = descricao;
        this.tempo_estimado = tempo_estimado;
        this.preco_base = preco_base;
    }

    public int getId() { return id_servico; }
    public void setId(int id) { this.id_servico = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getTempoEstimado() { return tempo_estimado; }
    public void setTempoEstimado(int tempo_estimado) { this.tempo_estimado = tempo_estimado; }
    public BigDecimal getPrecoBase() { return preco_base; }
    public void setPrecoBase(BigDecimal preco_base) { this.preco_base = preco_base; }

    @Override
    public String toString() {
        return "Servico [ID=" + id_servico + ", Descrição='" + descricao + "', Preço=" + preco_base + ", Tempo Estimado=" + tempo_estimado + " min]";
    }
}
