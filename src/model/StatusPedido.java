package model;

// Define todos os status válidos para um pedido
public enum StatusPedido {
    RECEBIDO("Recebido"),
    PROCESSANDO("Processando"),
    PRONTO_PARA_ENTREGA("Pronto para Entrega"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
