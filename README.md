# Trabalho para seminário da matéria de Banco de dados
## Sistema de lavanderia integrado no Banco de dados real, integração feita com a Linguagem Java por meio do método JDBC
### Sistema simples com o formato de Crud 

 ## Sistema de Gerenciamento de Lavanderia (JDBC)Este projeto é uma aplicação Java console (CLI) desenvolvida para gerenciar o fluxo de trabalho de uma lavanderia, utilizando o JDBC (Java Database Connectivity) para interagir com um banco de dados MySQL. O foco principal é demonstrar o uso do padrão DAO/Repository para operações CRUD (Create, Read, Update, Delete).
 ### Tecnologias Utilizadas:
 - TecnologiaFunçãoVersão TípicaJavaLinguagem de desenvolvimento principal.Java 11
 - +MySQLSistema de Gerenciamento de Banco de Dados (SGBD).8.0
 - +JDBC
 - APIInterface de Conectividade com Banco de Dados
 - Java.Padrão Java SEMySQL Connector/JO driver específico para conectar Java ao MySQL.8.0+💡
 - Conceito Arquitetural: JDBC e Padrão DAOO projeto segue uma arquitetura baseada em camadas para garantir separação de responsabilidades
   # 1. Camada de Modelo (model)Conceito: Classes simples que representam as entidades de negócio (tabelas no banco).
   ## Função: Armazenar dados e fornecer Getters e Setters.
   ## Exemplos:
   - Cliente.java
   - Servico.java
   - Pedido.java
   # 2. Camada de Persistência (repository)Conceito: Padrão Data Access Object (DAO). Esta é a camada que interage diretamente com o banco de dados.
   ## Função: Contém os métodos CRUD (adicionar, listar, atualizar, removerPorId), utilizando comandos SQL via JDBC.
   ## Exemplos:
   - ClienteRepository.java
   - ServicoRepository.java
   # 3. Utilidades (util)DatabaseConnection.java: É o coração da conexão. Ele carrega o Driver JDBC, define a URL do banco (usuário e senha) e fornece a conexão (Connection) para os Repositórios.Main.java: A interface de linha de comando (CLI) que orquestra as chamadas aos Repositórios com base na interação do usuário.
   ## Configuração Inicial (SETUP)Para rodar o projeto, siga estes passos:
   - 1. Criar o Banco de DadosCrie um banco de dados no seu MySQL chamado lavanderia_db e execute o script de criação de tabelas (incluindo Cliente, Funcionario, Servico, Pedido, ItemPedido e Pagamento)
   - 2. Adicionar o Driver JDBCO erro No suitable driver found indica que o driver está faltando. Você deve adicionar o arquivo mysql-connector-j-X.X.X.jar ao Build Path (caminho de construção) do seu projeto na IDE (Eclipse, IntelliJ, VS Code).
   - 3. Ajustar CredenciaisVerifique e ajuste as variáveis de conexão no arquivo util/DatabaseConnection.java:Javaprivate static final String URL = "jdbc:mysql://localhost:3306/lavanderia_db?...";
private static final String USER = "seu_usuario"; // Ex: root
private static final String PASSWORD = "sua_senha"; // Ex: ceub123456
# Guia de Uso e Funcionalidades (CRUD)
## Execute o Main.java para acessar o menu:OpçãoMóduloCRUDFunções Chave1FuncionáriosCompletoGerencia a equipe de trabalho.2ClientesCompletoGerencia os clientes que utilizam o serviço.3ServiçosCompletoDefine os serviços oferecidos (Lavagem, Secagem, Preço).4PedidosCriação / AtualizaçãoAssocia Clientes, Funcionários e Serviços (via ItemPedido).5PagamentosCriação / AtualizaçãoRegistra o pagamento para um pedido específico e altera seu status.✅ Teste de Funcionalidade (Validação)Para garantir que a integração JDBC está funcionando corretamente:Testar Persistência:Vá em 1. Gerenciar Funcionários e 1. Cadastrar Novo.Verifique diretamente no MySQL (SELECT * FROM Funcionario;) se o registro foi criado.Testar Recuperação:Vá em 3. Gerenciar Serviços e 2. Listar Todos. O serviço cadastrado deve aparecer no console.Testar o Fluxo de Negócio:Cadastre 1 Cliente, 1 Funcionário e 1 Serviço.Crie um Pedido (Opção 4), associando os itens anteriores.Registre um Pagamento (Opção 5) para o novo pedido.Se todas as operações CRUD e o fluxo de negócio funcionarem, o projeto está validado!
