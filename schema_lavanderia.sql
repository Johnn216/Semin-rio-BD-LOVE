CREATE TABLE IF NOT EXISTS `Cliente` (
	`id_cliente` int AUTO_INCREMENT NOT NULL UNIQUE,
	`nome` varchar(100) NOT NULL,
	`email` varchar(100) NOT NULL,
	`endereço` varchar(200) NOT NULL,
	`telefone` varchar(20) NOT NULL,
	PRIMARY KEY (`id_cliente`)
);

CREATE TABLE IF NOT EXISTS `Funcionario` (
	`id_funcionario` int AUTO_INCREMENT NOT NULL UNIQUE,
	`nome` varchar(100) NOT NULL,
	`cargo` varchar(50) NOT NULL,
	`telefone` varchar(20) NOT NULL,
	PRIMARY KEY (`id_funcionario`)
);

CREATE TABLE IF NOT EXISTS `Servico` (
	`id_servico` int AUTO_INCREMENT NOT NULL UNIQUE,
	`descricao` varchar(100) NOT NULL,
	`tempo_estimado` int NOT NULL,
	`preco_base` decimal(10,0) NOT NULL,
	PRIMARY KEY (`id_servico`)
);

CREATE TABLE IF NOT EXISTS `pedido` (
	`id_pedido` int AUTO_INCREMENT NOT NULL UNIQUE,
	`id_cliente` int NOT NULL,
	`id_funcionario` int NOT NULL,
	`data_recebimento` date NOT NULL,
	`data_entrega_prevista` date NOT NULL,
	`data_entrega_real` date NOT NULL,
	`status` varchar(30) NOT NULL DEFAULT 'Recebido',
	PRIMARY KEY (`id_pedido`)
);

CREATE TABLE IF NOT EXISTS `Pagamento` (
	`id_pagamento` int AUTO_INCREMENT NOT NULL UNIQUE,
	`id_pedido` int NOT NULL,
	`forma_pagamento` varchar(20) NOT NULL,
	`valor_total` decimal(10,2) NOT NULL,
	`data_pagamento` date NOT NULL,
	`status_pagamento` varchar(20) NOT NULL DEFAULT 'Pendente',
	PRIMARY KEY (`id_pagamento`)
);

CREATE TABLE IF NOT EXISTS `ItemPedido` (
	`id_item` int AUTO_INCREMENT NOT NULL UNIQUE,
	`id_pedido` int NOT NULL,
	`id_servico` int NOT NULL,
	`quantidade` int NOT NULL,
	`valor_unitario` decimal(10,2) NOT NULL,
	`subtotal` decimal(10,2) NOT NULL,
	PRIMARY KEY (`id_item`)
);

ALTER TABLE `Cliente` ADD CONSTRAINT `Cliente_fk0` FOREIGN KEY (`id_cliente`) REFERENCES `pedido`(`id_pedido`);
ALTER TABLE `Funcionario` ADD CONSTRAINT `Funcionario_fk0` FOREIGN KEY (`id_funcionario`) REFERENCES `pedido`(`id_pedido`);
ALTER TABLE `Servico` ADD CONSTRAINT `Servico_fk0` FOREIGN KEY (`id_servico`) REFERENCES `ItemPedido`(`id_item`);
ALTER TABLE `pedido` ADD CONSTRAINT `pedido_fk0` FOREIGN KEY (`id_pedido`) REFERENCES `ItemPedido`(`id_item`);

ALTER TABLE `pedido` ADD CONSTRAINT `pedido_fk1` FOREIGN KEY (`id_cliente`) REFERENCES `Cliente`(`id_cliente`);

ALTER TABLE `pedido` ADD CONSTRAINT `pedido_fk2` FOREIGN KEY (`id_funcionario`) REFERENCES `Funcionario`(`id_funcionario`);
ALTER TABLE `Pagamento` ADD CONSTRAINT `Pagamento_fk0` FOREIGN KEY (`id_pagamento`) REFERENCES `pedido`(`id_pedido`);

ALTER TABLE `Pagamento` ADD CONSTRAINT `Pagamento_fk1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido`(`id_pedido`);
ALTER TABLE `ItemPedido` ADD CONSTRAINT `ItemPedido_fk1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido`(`id_pedido`);

ALTER TABLE `ItemPedido` ADD CONSTRAINT `ItemPedido_fk2` FOREIGN KEY (`id_servico`) REFERENCES `Servico`(`id_servico`);
