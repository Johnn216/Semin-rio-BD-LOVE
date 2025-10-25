// src/model/ItemPedido.java
package model;

import java.math.BigDecimal;

public class ItemPedido {
    private int id_item;
    private int id_pedido;
    private int id_servico;
    private int quantidade;
    private BigDecimal valor_unitario;
    private BigDecimal subtotal;

    public ItemPedido(int id_pedido, int id_servico, int quantidade, BigDecimal valor_unitario) {
        this.id_pedido = id_pedido;
        this.id_servico = id_servico;
        this.quantidade = quantidade;
        this.valor_unitario = valor_unitario;
        // Calcula o subtotal ao criar (o banco também pode fazer isso)
        this.subtotal = valor_unitario.multiply(new BigDecimal(quantidade));
    }

    // Getters e Setters
    public int getId() { return id_item; }
    public void setId(int id) { this.id_item = id; }
    public int getIdPedido() { return id_pedido; }
    public void setIdPedido(int id_pedido) { this.id_pedido = id_pedido; }
    public int getIdServico() { return id_servico; }
    public void setIdServico(int id_servico) { this.id_servico = id_servico; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public BigDecimal getValorUnitario() { return valor_unitario; }
    public void setValorUnitario(BigDecimal valor_unitario) { this.valor_unitario = valor_unitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return "ItemPedido [ID=" + id_item + ", Pedido ID=" + id_pedido + ", Serviço ID=" + id_servico + 
               ", Qtd=" + quantidade + ", Subtotal=" + subtotal + "]";
    }
}
