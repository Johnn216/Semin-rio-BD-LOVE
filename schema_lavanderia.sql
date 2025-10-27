-- 1. Criação do Banco de Dados (Se ainda não existir)
CREATE DATABASE IF NOT EXISTS lavanderia;
USE lavanderia;

-- 2. Tabela CLIENTES
CREATE TABLE IF NOT EXISTS clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    endereco VARCHAR(200),
    telefone VARCHAR(20)
);

-- 3. Tabela FUNCIONARIOS
CREATE TABLE IF NOT EXISTS funcionarios (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cargo VARCHAR(50),
    telefone VARCHAR(20)
);

-- 4. Tabela SERVICOS
CREATE TABLE IF NOT EXISTS servicos (
    id_servico INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    tempo_estimado INT,
    preco_base DECIMAL(10, 2)
);

-- 5. Tabela PEDIDOS (Contém chaves estrangeiras para Cliente e Funcionario)
CREATE TABLE IF NOT EXISTS pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_funcionario INT NOT NULL,
    data_recebimento DATE NOT NULL,
    data_entrega_prevista DATE,
    data_entrega_real DATE,
    status VARCHAR(30) DEFAULT 'Recebido',
    
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    FOREIGN KEY (id_funcionario) REFERENCES funcionarios(id_funcionario)
);

-- 6. Tabela ITENS_PEDIDO (Tabela de ligação entre Pedido e Serviço)
CREATE TABLE IF NOT EXISTS itens_pedido (
    id_item INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_servico INT NOT NULL,
    quantidade INT NOT NULL,
    valor_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    FOREIGN KEY (id_servico) REFERENCES servicos(id_servico)
);

-- 7. Tabela PAGAMENTOS
CREATE TABLE IF NOT EXISTS pagamentos (
    id_pagamento INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT UNIQUE NOT NULL, -- UNIQUE porque cada pedido só pode ter 1 registro de pagamento
    forma_pagamento VARCHAR(20) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    data_pagamento DATE,
    status_pagamento VARCHAR(20) DEFAULT 'Pendente',
    
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido)
);

-- Insere um Cliente
INSERT INTO clientes (nome, email, endereco, telefone) 
VALUES ('Jhonatan Silva', 'jhonatan@exemplo.com', 'Rua Principal, 100', '9999-8888');

-- Insere um Funcionário
INSERT INTO funcionarios (nome, cargo, telefone) 
VALUES ('Maria Souza', 'Gerente de Operações', '9999-7777');

-- Insere um Serviço
INSERT INTO servicos (descricao, tempo_estimado, preco_base) 
VALUES ('Lavagem de Terno', 24, 45.00);

-- Insere outro Serviço
INSERT INTO servicos (descricao, tempo_estimado, preco_base) 
VALUES ('Limpeza de Tapete', 72, 120.50);
