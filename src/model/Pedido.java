package model;

import java.sql.Date; // IMPORTANTE: Usar java.sql.Date para compatibilidade com JDBC

public class Pedido {
    private int id_pedido;
    private int id_cliente;    
    private int id_funcionario;
    private Date data_recebimento;
    private Date data_entrega_prevista;
    private Date data_entrega_real; // Pode ser null
    private String status;    

    // 1. Construtor para NOVO pedido (INSERT no banco)
    // Inicializa o status como "Recebido" por padrão.
    public Pedido(int id_cliente, int id_funcionario, Date data_recebimento, Date data_entrega_prevista) {
        this.id_cliente = id_cliente;
        this.id_funcionario = id_funcionario;
        this.data_recebimento = data_recebimento;
        this.data_entrega_prevista = data_entrega_prevista;
        this.status = "Recebido";
        this.data_entrega_real = null; // Garante que é nulo ao criar
    }

    // 2. Construtor para LER DO BANCO (SELECT) - Contém ID e todos os campos
    public Pedido(int id_pedido, int id_cliente, int id_funcionario, Date data_recebimento, Date data_entrega_prevista, Date data_entrega_real, String status) {
        this.id_pedido = id_pedido; // ID lido do banco
        this.id_cliente = id_cliente;
        this.id_funcionario = id_funcionario;
        this.data_recebimento = data_recebimento;
        this.data_entrega_prevista = data_entrega_prevista;
        this.data_entrega_real = data_entrega_real; // Pode ser nulo se o banco retornar NULL
        this.status = status; // Status lido do banco
    }

    // Getters e Setters
    public int getId() { return id_pedido; }
    public void setId(int id) { this.id_pedido = id; }
    public int getIdCliente() { return id_cliente; }
    public void setIdCliente(int id_cliente) { this.id_cliente = id_cliente; }
    public int getIdFuncionario() { return id_funcionario; }
    public void setIdFuncionario(int id_funcionario) { this.id_funcionario = id_funcionario; }
    public Date getDataRecebimento() { return data_recebimento; }
    public void setDataRecebimento(Date data_recebimento) { this.data_recebimento = data_recebimento; }
    public Date getDataEntregaPrevista() { return data_entrega_prevista; }
    public void setDataEntregaPrevista(Date data_entrega_prevista) { this.data_entrega_prevista = data_entrega_prevista; }
    public Date getDataEntregaReal() { return data_entrega_real; }
    public void setDataEntregaReal(Date data_entrega_real) { this.data_entrega_real = data_entrega_real; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Pedido [ID=" + id_pedido + ", Cliente ID=" + id_cliente + ", Func. ID=" + id_funcionario + 
                ", Recebido=" + data_recebimento + ", Entrega Prevista=" + data_entrega_prevista + 
                ", Entrega Real=" + (data_entrega_real != null ? data_entrega_real : "PENDENTE") + 
                ", Status=" + status + "]";
    }
}
