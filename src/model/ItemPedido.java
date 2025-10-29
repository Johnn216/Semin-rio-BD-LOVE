package model;

// REMOVIDO: import java.math.BigDecimal;

public class ItemPedido {
    private int id_item;
    private int id_pedido;
    private int id_servico;
    private int quantidade;
    private double valor_unitario; // Sincronizado (double é compatível com decimal do SQL)
    private double subtotal;       // Sincronizado (double é compatível com decimal do SQL)

    // 1. Construtor para NOVO Item (INSERT - SEM ID)
    public ItemPedido(int id_pedido, int id_servico, int quantidade, double valor_unitario, double subtotal) {
        this.id_pedido = id_pedido;
        this.id_servico = id_servico;
        this.quantidade = quantidade;
        this.valor_unitario = valor_unitario;
        this.subtotal = subtotal; 
    }
    
    // 2. Construtor para LER DO BANCO (SELECT - COM ID)
    public ItemPedido(int id_item, int id_pedido, int id_servico, int quantidade, double valor_unitario, double subtotal) {
        this.id_item = id_item;
        this.id_pedido = id_pedido;
        this.id_servico = id_servico;
        this.quantidade = quantidade;
        this.valor_unitario = valor_unitario;
        this.subtotal = subtotal;
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
    public double getValorUnitario() { return valor_unitario; }
    public void setValorUnitario(double valor_unitario) { this.valor_unitario = valor_unitario; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return "ItemPedido [ID=" + id_item + ", Pedido ID=" + id_pedido + ", Serviço ID=" + id_servico + 
               ", Qtd=" + quantidade + ", Subtotal=" + subtotal + "]";
    }
}
