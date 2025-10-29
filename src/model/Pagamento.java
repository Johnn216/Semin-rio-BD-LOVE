package model;

import java.sql.Date; // IMPORTANTE: Usar java.sql.Date

public class Pagamento {
    private int id_pagamento;
    private int id_pedido;
    private String forma_pagamento;
    private double valor_total; // Sincronizado (double é compatível com decimal do SQL)
    private Date data_pagamento;
    private String status_pagamento; // Sincronizado com SQL

    // 1. Construtor para NOVO Pagamento (INSERT - SEM ID)
    public Pagamento(int id_pedido, String forma_pagamento, double valor_total, Date data_pagamento, String status_pagamento) {
        this.id_pedido = id_pedido;
        this.forma_pagamento = forma_pagamento;
        this.valor_total = valor_total;
        this.data_pagamento = data_pagamento;
        this.status_pagamento = status_pagamento; // Recebe o status (Pago/Pendente) do Main.java
    }

    // 2. Construtor para LER DO BANCO (SELECT - COM ID)
    public Pagamento(int id_pagamento, int id_pedido, String forma_pagamento, double valor_total, Date data_pagamento, String status_pagamento) {
        this.id_pagamento = id_pagamento;
        this.id_pedido = id_pedido;
        this.forma_pagamento = forma_pagamento;
        this.valor_total = valor_total;
        this.data_pagamento = data_pagamento;
        this.status_pagamento = status_pagamento;
    }

    // Getters e Setters
    public int getId() { return id_pagamento; }
    public void setId(int id) { this.id_pagamento = id; }
    public int getIdPedido() { return id_pedido; }
    public void setIdPedido(int id_pedido) { this.id_pedido = id_pedido; }
    public String getFormaPagamento() { return forma_pagamento; }
    public void setFormaPagamento(String forma_pagamento) { this.forma_pagamento = forma_pagamento; }
    public double getValorTotal() { return valor_total; }
    public void setValorTotal(double valor_total) { this.valor_total = valor_total; }
    public Date getDataPagamento() { return data_pagamento; }
    public void setDataPagamento(Date data_pagamento) { this.data_pagamento = data_pagamento; }
    public String getStatusPagamento() { return status_pagamento; }
    public void setStatusPagamento(String status_pagamento) { this.status_pagamento = status_pagamento; }

    @Override
    public String toString() {
        return "Pagamento [ID=" + id_pagamento + ", Pedido ID=" + id_pedido + ", Forma='" + forma_pagamento + 
               "', Total=" + valor_total + ", Data=" + data_pagamento + ", Status=" + status_pagamento + "]";
    }
}
