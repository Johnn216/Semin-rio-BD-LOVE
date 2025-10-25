// Main.java
import model.*;
import repository.*;
import util.DatabaseConnection; // Importado para caso o usuário queira testar a conexão diretamente

import java.math.BigDecimal;
import java.sql.Date; // Usar java.sql.Date para compatibilidade com JDBC
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Inicialização dos Repositórios JDBC
    private static ClienteRepository clienteRepository = new ClienteRepository();
    private static FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
    private static ServicoRepository servicoRepository = new ServicoRepository();
    private static PedidoRepository pedidoRepository = new PedidoRepository();
    // Os Repositórios de Pagamento e ItemPedido seriam adicionados aqui:
    // private static PagamentoRepository pagamentoRepository = new PagamentoRepository(); 
    // private static ItemPedidoRepository itemPedidoRepository = new ItemPedidoRepository(); 
    
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--- Sistema de Gerenciamento de Lavanderia ---");
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao(); // Usa método seguro
            
            switch (opcao) {
                case 1: gerenciarFuncionarios(); break;
                case 2: gerenciarClientes(); break;
                case 3: gerenciarServicos(); break;
                case 4: gerenciarPedidos(); break;
                case 0: System.out.println("Saindo do sistema. Adeus!"); break;
                default: System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    // --- MÉTODOS DE UTILIDADE ---

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Retorna inválido
        }
    }
    
    private static BigDecimal lerBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                // Remove vírgulas e substitui por ponto (padrão americano) para evitar erros
                String input = scanner.nextLine().replace(',', '.'); 
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um valor numérico (ex: 15.50).");
            }
        }
    }
    
    private static Date lerData(String prompt) {
        // Formato brasileiro comum: dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print(prompt + " (dd/MM/yyyy): ");
            String input = scanner.nextLine();
            try {
                LocalDate localDate = LocalDate.parse(input, formatter);
                return Date.valueOf(localDate); // Converte para java.sql.Date
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use o formato dd/MM/yyyy (ex: 25/10/2025).");
            }
        }
    }

    // --- MÉTODOS DE EXIBIÇÃO DE MENU ---

    private static void exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Gerenciar Funcionários (CRUD)");
        System.out.println("2. Gerenciar Clientes (CRUD)");
        System.out.println("3. Gerenciar Serviços (CRUD)");
        System.out.println("4. Gerenciar Pedidos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private static void exibirMenuCRUD(String entidade) {
        System.out.println("\n--- MENU DE " + entidade.toUpperCase() + "S ---");
        System.out.println("1. Cadastrar Novo(a) " + entidade);
        System.out.println("2. Listar Todos(as) " + entidade + "s");
        System.out.println("3. Buscar " + entidade + " por ID");
        System.out.println("4. Atualizar " + entidade);
        System.out.println("5. Excluir " + entidade);
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha: ");
    }
    
    // --- GERENCIAMENTO GERAL ---

    private static void gerenciarClientes() {
        int opcao;
        do {
            exibirMenuCRUD("Cliente");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1: cadastrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: buscarCliente(); break;
                // Os métodos 4 e 5 (Atualizar/Excluir) estão no final da classe para economizar espaço aqui
                case 4: atualizarCliente(); break; 
                case 5: excluirCliente(); break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    private static void gerenciarFuncionarios() {
        int opcao;
        do {
            exibirMenuCRUD("Funcionário");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1: cadastrarFuncionario(); break;
                case 2: listarFuncionarios(); break;
                // Implemente os demais CRUDs (Buscar, Atualizar, Excluir)
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    private static void gerenciarServicos() {
        int opcao;
        do {
            exibirMenuCRUD("Serviço");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1: cadastrarServico(); break;
                case 2: listarServicos(); break;
                // Implemente os demais CRUDs (Buscar, Atualizar, Excluir)
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    private static void gerenciarPedidos() {
        int opcao;
        do {
            System.out.println("\n--- MENU DE PEDIDOS ---");
            System.out.println("1. Registrar Novo Pedido");
            System.out.println("2. Listar Todos os Pedidos");
            System.out.println("3. Adicionar Item a um Pedido");
            System.out.println("4. Finalizar Pedido (Pagamento)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1: cadastrarPedido(); break;
                case 2: listarPedidos(); break;
                // Implemente 3 e 4
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // --- MÉTODOS CRUD DE CLIENTE (Exemplo Completo) ---

    private static void cadastrarCliente() {
        System.out.println("\n--- Cadastro de Cliente ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Cliente c = new Cliente(nome, email, endereco, telefone);
        if (clienteRepository.adicionar(c) != null) {
            System.out.println("Cliente cadastrado com sucesso! ID: " + c.getId());
        } else {
            System.out.println("Falha ao cadastrar cliente.");
        }
    }

    private static void listarClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        ArrayList<Cliente> clientes = clienteRepository.listar();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no banco de dados.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }
    
    private static void buscarCliente() {
        System.out.print("Digite o ID do cliente para buscar: ");
        int id = lerOpcao();
        Cliente c = clienteRepository.buscarPorId(id);
        if (c != null) {
            System.out.println("Cliente encontrado: " + c);
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado.");
        }
    }

    private static void atualizarCliente() {
        System.out.print("Digite o ID do cliente para atualizar: ");
        int id = lerOpcao();
        if (clienteRepository.buscarPorId(id) == null) {
            System.out.println("Cliente com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.print("Digite o novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o novo email: ");
        String email = scanner.nextLine();
        System.out.print("Digite o novo endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Digite o novo telefone: ");
        String telefone = scanner.nextLine();
        
        Cliente dadosNovos = new Cliente(nome, email, endereco, telefone);
        if (clienteRepository.atualizar(id, dadosNovos)) {
            System.out.println("Cliente atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar cliente.");
        }
    }
    
    private static void excluirCliente() {
        System.out.print("Digite o ID do cliente para excluir: ");
        int id = lerOpcao();
        if (clienteRepository.removerPorId(id)) {
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Falha ao remover. Cliente com ID " + id + " não encontrado ou possui pedidos associados (chave estrangeira).");
        }
    }

    // --- MÉTODOS CRUD DE FUNCIONÁRIO ---
    
    private static void cadastrarFuncionario() {
        System.out.println("\n--- Cadastro de Funcionário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Funcionario f = new Funcionario(nome, cargo, telefone);
        if (funcionarioRepository.adicionar(f) != null) {
            System.out.println("Funcionário cadastrado com sucesso! ID: " + f.getId());
        } else {
            System.out.println("Falha ao cadastrar funcionário.");
        }
    }
    
    private static void listarFuncionarios() {
        System.out.println("\n--- Lista de Funcionários ---");
        ArrayList<Funcionario> funcionarios = funcionarioRepository.listar();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado no banco de dados.");
        } else {
            for (Funcionario f : funcionarios) {
                System.out.println(f);
            }
        }
    }
    
    // --- MÉTODOS CRUD DE SERVIÇO ---
    
    private static void cadastrarServico() {
        System.out.println("\n--- Cadastro de Serviço ---");
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Tempo Estimado (minutos - INT): ");
        int tempo = lerOpcao();
        
        // Usa o método de leitura seguro para BigDecimal
        BigDecimal preco = lerBigDecimal("Preço Base (DECIMAL): "); 

        Servico s = new Servico(descricao, tempo, preco);
        if (servicoRepository.adicionar(s) != null) {
            System.out.println("Serviço cadastrado com sucesso! ID: " + s.getId());
        } else {
            System.out.println("Falha ao cadastrar serviço.");
        }
    }
    
    private static void listarServicos() {
        System.out.println("\n--- Lista de Serviços ---");
        ArrayList<Servico> servicos = servicoRepository.listar();
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado no banco de dados.");
        } else {
            for (Servico s : servicos) {
                System.out.println(s);
            }
        }
    }

    // --- MÉTODOS DE PEDIDO ---
    
    private static void cadastrarPedido() {
        System.out.println("\n--- Registro de Novo Pedido ---");
        
        System.out.print("ID do Cliente (Necessário): ");
        int idCliente = lerOpcao();
        if (clienteRepository.buscarPorId(idCliente) == null) {
            System.out.println("ERRO: Cliente com ID " + idCliente + " não encontrado. Cancele ou cadastre o cliente.");
            return;
        }

        System.out.print("ID do Funcionário (Necessário): ");
        int idFuncionario = lerOpcao();
        if (funcionarioRepository.buscarPorId(idFuncionario) == null) {
            System.out.println("ERRO: Funcionário com ID " + idFuncionario + " não encontrado. Cancele ou cadastre o funcionário.");
            return;
        }
        
        // Usa o método de leitura seguro para Datas
        Date dataRecebimento = lerData("Data de Recebimento");
        Date dataEntregaPrevista = lerData("Data de Entrega Prevista");

        Pedido p = new Pedido(idCliente, idFuncionario, dataRecebimento, dataEntregaPrevista);
        if (pedidoRepository.adicionar(p) != null) {
            System.out.println("Pedido registrado com sucesso! ID: " + p.getId());
            // Após registrar o pedido, a próxima etapa seria adicionar itens (ItemPedidoRepository)
        } else {
            System.out.println("Falha ao registrar pedido.");
        }
    }
    
    private static void listarPedidos() {
        System.out.println("\n--- Lista de Pedidos ---");
        ArrayList<Pedido> pedidos = pedidoRepository.listar();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado no banco de dados.");
        } else {
            for (Pedido p : pedidos) {
                // Aqui seria ideal buscar o nome do Cliente e Funcionario para exibir
                System.out.println(p);
            }
        }
    }
}
