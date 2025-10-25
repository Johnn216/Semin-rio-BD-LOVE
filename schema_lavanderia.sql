-- #################################################
-- ETAPA 1: CRIAÇÃO DO BANCO DE DADOS E TABELAS
-- SINTAXE: MySQL / MariaDB
-- #################################################

-- (Opcional, mas recomendado) Cria o banco de dados se ele não existir
CREATE DATABASE IF NOT EXISTS lavanderia_db;

-- Seleciona o banco de dados para que os comandos CREATE TABLE sejam aplicados nele
USE lavanderia_db;

-- 1. Tabela Cliente
CREATE TABLE Cliente (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    endereco VARCHAR(200),
    telefone VARCHAR(20)
);

-- 2. Tabela Funcionario
CREATE TABLE Funcionario (
    id_funcionario INT PRIMARY KEY AUTO_INCREMENT UNIQUE,
    nome VARCHAR(100) NOT NULL,
    cargo VARCHAR(50),
    telefone VARCHAR(20)
);

-- 3. Tabela Servico
CREATE TABLE Servico (
    id_servico INT PRIMARY KEY AUTO_INCREMENT UNIQUE,
    descricao VARCHAR(100) NOT NULL,
    tempo_estimado INT,
    preco_base DECIMAL(10, 2)
);

-- 4. Tabela Pedido (Contém Chaves Estrangeiras para Cliente e Funcionario)
CREATE TABLE Pedido (
    id_pedido INT PRIMARY KEY AUTO_INCREMENT UNIQUE,
    id_cliente INT NOT NULL,
    id_funcionario INT NOT NULL,
    data_recebimento DATE NOT NULL,
    data_entrega_prevista DATE,
    data_entrega_real DATE,
    status VARCHAR(30) DEFAULT 'Recebido',
    -- DEFINIÇÃO DAS CHAVES ESTRANGEIRAS
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_funcionario) REFERENCES Funcionario(id_funcionario)
);

-- 5. Tabela Pagamento (Contém Chave Estrangeira para Pedido)
CREATE TABLE Pagamento (
    id_pagamento INT PRIMARY KEY AUTO_INCREMENT UNIQUE,
    id_pedido INT NOT NULL,
    forma_pagamento VARCHAR(20),
    valor_total DECIMAL(10, 2),
    data_pagamento DATE,
    status_pagamento VARCHAR(20) DEFAULT 'Pendente',
    -- DEFINIÇÃO DA CHAVE ESTRANGEIRA
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido)
);

-- 6. Tabela ItemPedido (Contém Chaves Estrangeiras para Pedido e Servico)
CREATE TABLE ItemPedido (
    id_item INT PRIMARY KEY AUTO_INCREMENT UNIQUE,
    id_pedido INT NOT NULL,
    id_servico INT NOT NULL,
    quantidade INT,
    valor_unitario DECIMAL(10, 2),
    subtotal DECIMAL(10, 2),
    -- DEFINIÇÃO DAS CHAVES ESTRANGEIRAS
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido),
    FOREIGN KEY (id_servico) REFERENCES Servico(id_servico)
);

-- #################################################
-- ETAPA 2 (OPCIONAL): INSERÇÃO DE DADOS DE TESTE
-- #################################################

-- Insere um cliente de teste
INSERT INTO Cliente (nome, email, endereco, telefone) VALUES 
('João da Silva', 'joao@email.com', 'Rua A, 123', '9999-1234');

-- Insere um funcionário de teste
INSERT INTO Funcionario (nome, cargo, telefone) VALUES 
('Maria Oliveira', 'Atendente', '9888-5678');

-- Insere um serviço de teste
INSERT INTO Servico (descricao, tempo_estimado, preco_base) VALUES 
('Lavagem Simples', 120, 15.00);

-- Insere um pedido de teste
INSERT INTO Pedido (id_cliente, id_funcionario, data_recebimento, data_entrega_prevista) VALUES
(1, 1, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 2 DAY));

-- Insere um item de pedido de teste
INSERT INTO ItemPedido (id_pedido, id_servico, quantidade, valor_unitario, subtotal) VALUES
(1, 1, 3, 15.00, 45.00);

-- Para listar todos os clientes cadastrados
SELECT * FROM Cliente;

-- Para listar todos os funcionários cadastrados
SELECT * FROM Funcionario;

-- Para listar todos os serviços cadastrados
SELECT * FROM Servico;

-- Para listar todos os pedidos cadastrados
SELECT * FROM Pedido;

-- Para listar todos os itens de pedido cadastrados
SELECT * FROM ItemPedido;

-- Verificar o cliente com nome 'João da Silva'
SELECT * FROM Cliente WHERE nome = 'João da Silva';

-- Verificar o pedido com ID 1
SELECT * FROM Pedido WHERE id_pedido = 1;

