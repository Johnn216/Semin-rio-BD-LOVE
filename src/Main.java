package util;

import model.*;
import repository.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    // --- Declaração de Repositórios ---
    private static ClienteRepository clienteRepository;
    private static FuncionarioRepository funcionarioRepository;
    private static ServicoRepository servicoRepository;
    private static PedidoRepository pedidoRepository;
    private static ItemPedidoRepository itemPedidoRepository;
    private static PagamentoRepository pagamentoRepository;
    
    private static Scanner scanner = new Scanner(System.in);

    // --- BLOCO ESTÁTICO DE INICIALIZAÇÃO (CRÍTICO: Trata a Conexão JDBC) ---
    static {
        try {
            // Inicializa todos os repositórios migrados. 
            // A chamada a new ClienteRepository() implicitamente tenta carregar o driver JDBC.
            clienteRepository = new ClienteRepository();
            funcionarioRepository = new FuncionarioRepository();
            servicoRepository = new ServicoRepository();
            pedidoRepository = new PedidoRepository();
            itemPedidoRepository = new ItemPedidoRepository();
            pagamentoRepository = new PagamentoRepository();
            
            System.out.println("✅ Sistema inicializado. Conexão com o Banco de Dados estabelecida.");
        } catch (Exception e) {
            // Captura qualquer erro de inicialização, como SQLException do DatabaseConnection
            System.err.println("❌ ERRO FATAL: Falha ao iniciar o sistema de persistência.");
            System.err.println("Verifique o arquivo DatabaseConnection.java, as credenciais e se o MySQL está rodando.");
            e.printStackTrace();
            System.exit(1); // Encerra o programa
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Sistema de Gerenciamento de Lavanderia ---");
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();
            
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
    
    // -------------------------------------------------------------------------
    // --- MÉTODOS DE UTILIDADE E LEITURA SEGURA ---
    // -------------------------------------------------------------------------

    private static int lerOpcao() {
        try {
            // Usa nextLine() e parse para evitar problemas com buffer do scanner
            String input = scanner.nextLine().trim(); 
            if (input.isEmpty()) return -1;
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; // Retorna inválido
        }
    }
    
    private static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                // Remove vírgulas e substitui por ponto (padrão SQL/Java)
                String input = scanner.nextLine().replace(',', '.');
                return Double.parseDouble(input);
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

    // -------------------------------------------------------------------------
    // --- MÉTODOS DE EXIBIÇÃO DE MENU ---
    // -------------------------------------------------------------------------

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
    
    // -------------------------------------------------------------------------
    // --- GERENCIAMENTO GERAL ---
    // -------------------------------------------------------------------------

    private static void gerenciarClientes() {
        int opcao;
        do {
            exibirMenuCRUD("Cliente");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1: cadastrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: buscarCliente(); break;
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
                case 3: buscarFuncionario(); break; // NOVO
                case 4: atualizarFuncionario(); break; // NOVO
                case 5: excluirFuncionario(); break; // NOVO
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
                case 3: buscarServico(); break; // NOVO
                case 4: atualizarServico(); break; // NOVO
                case 5: excluirServico(); break; // NOVO
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
                // Os métodos 3 e 4, que envolvem mais lógica, são os próximos a serem implementados
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // -------------------------------------------------------------------------
    // --- MÉTODOS CRUD DE CLIENTE (Usando JDBC) ---
    // -------------------------------------------------------------------------

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
        Cliente clienteExistente = clienteRepository.buscarPorId(id);
        if (clienteExistente == null) {
            System.out.println("Cliente com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.print("Digite o novo nome (Atual: " + clienteExistente.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Digite o novo email (Atual: " + clienteExistente.getEmail() + "): ");
        String email = scanner.nextLine();
        System.out.print("Digite o novo endereço (Atual: " + clienteExistente.getEndereco() + "): ");
        String endereco = scanner.nextLine();
        System.out.print("Digite o novo telefone (Atual: " + clienteExistente.getTelefone() + "): ");
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

    // -------------------------------------------------------------------------
    // --- MÉTODOS CRUD DE FUNCIONÁRIO (Usando JDBC) ---
    // -------------------------------------------------------------------------
    
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

    private static void buscarFuncionario() {
        System.out.print("Digite o ID do funcionário para buscar: ");
        int id = lerOpcao();
        Funcionario f = funcionarioRepository.buscarPorId(id);
        if (f != null) {
            System.out.println("Funcionário encontrado: " + f);
        } else {
            System.out.println("Funcionário com ID " + id + " não encontrado.");
        }
    }

    private static void atualizarFuncionario() {
        System.out.print("Digite o ID do funcionário para atualizar: ");
        int id = lerOpcao();
        Funcionario funcionarioExistente = funcionarioRepository.buscarPorId(id);
        if (funcionarioExistente == null) {
            System.out.println("Funcionário com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.print("Digite o novo nome (Atual: " + funcionarioExistente.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Digite o novo cargo (Atual: " + funcionarioExistente.getCargo() + "): ");
        String cargo = scanner.nextLine();
        System.out.print("Digite o novo telefone (Atual: " + funcionarioExistente.getTelefone() + "): ");
        String telefone = scanner.nextLine();
        
        Funcionario dadosNovos = new Funcionario(nome, cargo, telefone);
        if (funcionarioRepository.atualizar(id, dadosNovos)) {
            System.out.println("Funcionário atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar funcionário.");
        }
    }

    private static void excluirFuncionario() {
        System.out.print("Digite o ID do funcionário para excluir: ");
        int id = lerOpcao();
        if (funcionarioRepository.removerPorId(id)) {
            System.out.println("Funcionário removido com sucesso!");
        } else {
            System.out.println("Falha ao remover. Funcionário com ID " + id + " não encontrado ou possui pedidos associados.");
        }
    }
    
    // -------------------------------------------------------------------------
    // --- MÉTODOS CRUD DE SERVIÇO (Usando JDBC) ---
    // -------------------------------------------------------------------------
    
    private static void cadastrarServico() {
        System.out.println("\n--- Cadastro de Serviço ---");
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Tempo Estimado (em minutos - INT): ");
        int tempo = lerOpcao();
        
        // CORRIGIDO: Usando lerDouble() para ler o preço
        double preco = lerDouble("Preço Base (DECIMAL): "); 

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

    private static void buscarServico() {
        System.out.print("Digite o ID do serviço para buscar: ");
        int id = lerOpcao();
        Servico s = servicoRepository.buscarPorId(id);
        if (s != null) {
            System.out.println("Serviço encontrado: " + s);
        } else {
            System.out.println("Serviço com ID " + id + " não encontrado.");
        }
    }

    private static void atualizarServico() {
        System.out.print("Digite o ID do serviço para atualizar: ");
        int id = lerOpcao();
        Servico servicoExistente = servicoRepository.buscarPorId(id);
        if (servicoExistente == null) {
            System.out.println("Serviço com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.print("Digite a nova descrição (Atual: " + servicoExistente.getDescricao() + "): ");
        String descricao = scanner.nextLine();
        System.out.print("Digite o novo Tempo Estimado em minutos (Atual: " + servicoExistente.getTempoEstimado() + "): ");
        int tempo = lerOpcao(); 
        
        double preco = lerDouble("Novo Preço Base (Atual: " + servicoExistente.getPrecoBase() + "): "); 

        Servico dadosNovos = new Servico(descricao, tempo, preco);
        if (servicoRepository.atualizar(id, dadosNovos)) {
            System.out.println("Serviço atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar serviço.");
        }
    }

    private static void excluirServico() {
        System.out.print("Digite o ID do serviço para excluir: ");
        int id = lerOpcao();
        if (servicoRepository.removerPorId(id)) {
            System.out.println("Serviço removido com sucesso!");
        } else {
            System.out.println("Falha ao remover. Serviço com ID " + id + " não encontrado ou está em uso em algum pedido.");
        }
    }

    // -------------------------------------------------------------------------
    // --- MÉTODOS DE PEDIDO (Usando JDBC) ---
    // -------------------------------------------------------------------------
    
    private static void cadastrarPedido() {
        // ... (o seu método cadastrarPedido está correto e foi mantido)
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
        
        Date dataRecebimento = lerData("Data de Recebimento");
        Date dataEntregaPrevista = lerData("Data de Entrega Prevista");

        Pedido p = new Pedido(idCliente, idFuncionario, dataRecebimento, dataEntregaPrevista);
        if (pedidoRepository.adicionar(p) != null) {
            System.out.println("Pedido registrado com sucesso! ID: " + p.getId());
            // Sugestão: Chamar adicionarItensAoPedido(p.getId()) aqui para guiar o usuário
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
                System.out.println(p);
            }
        }
    }

    // Os métodos 3 e 4 do menu de Pedidos (Adicionar Item e Finalizar Pagamento) precisam ser implementados.

}
