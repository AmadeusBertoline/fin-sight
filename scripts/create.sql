CREATE DATABASE IF NOT EXISTS db_investimentos;
USE db_investimentos;

-- Tabela: ativos
CREATE TABLE ativos (
    id INT(11) NOT NULL AUTO_INCREMENT,
    ticker VARCHAR(10) NOT NULL,
    nome_empresa VARCHAR(100) DEFAULT NULL,
    tipo VARCHAR(30) NOT NULL,
    valor_unitario DECIMAL(15,2) NOT NULL
);

-- Tabela: compras
CREATE TABLE compras (
    idCompra INT(11) NOT NULL AUTO_INCREMENT,
    idAtivo INT(11) NOT NULL,
    dataCompra DATE NOT NULL,
    valor_compra DECIMAL(10,2) DEFAULT NULL,
    quantidade DECIMAL(10,2) DEFAULT NULL,
    valor_unitario DECIMAL(10,2) DEFAULT NULL
);

-- Tabela: audit_log
CREATE TABLE audit_log (
    id_log INT(11) NOT NULL AUTO_INCREMENT,
    operacao VARCHAR(10) DEFAULT NULL,
    dados_antigos TEXT DEFAULT NULL,
    dados_novos TEXT DEFAULT NULL,
    data DATETIME DEFAULT NULL
);


ALTER TABLE ativos 
    ADD CONSTRAINT pk_ativos PRIMARY KEY (id);

ALTER TABLE compras 
    ADD CONSTRAINT pk_compras PRIMARY KEY (idCompra);

ALTER TABLE audit_log 
    ADD CONSTRAINT pk_audit_log PRIMARY KEY (id_log);


ALTER TABLE compras 
    ADD CONSTRAINT fk_compras_ativos FOREIGN KEY (idAtivo) REFERENCES ativos (id);