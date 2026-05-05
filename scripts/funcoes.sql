DELIMITER ##

-- 1. PROCEDURE: Resumo da Carteira
CREATE PROCEDURE resumoCarteira()
BEGIN
    SELECT
        IFNULL(SUM(c.valor_compra), 0) AS total_investido,
        IFNULL(SUM(c.quantidade * a.valor_unitario), 0) AS valor_atual,
        IFNULL(SUM(c.quantidade * a.valor_unitario) - SUM(c.valor_compra), 0) AS lucro_prejuizo
    FROM Compras c
    JOIN ativos a ON c.idAtivo = a.id;
END ##

-- 2. TRIGGER: Auditoria INSERT
CREATE TRIGGER trg_Auditoria_Insert
AFTER INSERT ON Compras
FOR EACH ROW
BEGIN    
    INSERT INTO audit_log(operacao, dados_antigos, dados_novos, data)
    VALUES('INSERT', NULL, 
           CONCAT('ID:', NEW.idCompra, ' | Ativo:', NEW.idAtivo, ' | Data:', NEW.dataCompra, 
                  ' | Qtd:', NEW.quantidade, ' | V.Unit:', NEW.valor_unitario, ' | Total:', NEW.valor_compra), 
           NOW());
END ##

-- 3. TRIGGER: Auditoria UPDATE
CREATE TRIGGER trg_Auditoria_Update
AFTER UPDATE ON Compras
FOR EACH ROW
BEGIN    
    INSERT INTO audit_log(operacao, dados_antigos, dados_novos, data)
    VALUES('UPDATE', 
           CONCAT('ID:', OLD.idCompra, ' | Ativo:', OLD.idAtivo, ' | Data:', OLD.dataCompra, 
                  ' | Qtd:', OLD.quantidade, ' | V.Unit:', OLD.valor_unitario, ' | Total:', OLD.valor_compra),
           CONCAT('ID:', NEW.idCompra, ' | Ativo:', NEW.idAtivo, ' | Data:', NEW.dataCompra, 
                  ' | Qtd:', NEW.quantidade, ' | V.Unit:', NEW.valor_unitario, ' | Total:', NEW.valor_compra),
           NOW());
END ##

-- 4. TRIGGER: Auditoria DELETE
CREATE TRIGGER trg_Auditoria_Delete
AFTER DELETE ON Compras
FOR EACH ROW
BEGIN    
    INSERT INTO audit_log(operacao, dados_antigos, dados_novos, data)
    VALUES('DELETE', 
           CONCAT('ID:', OLD.idCompra, ' | Ativo:', OLD.idAtivo, ' | Data:', OLD.dataCompra, 
                  ' | Qtd:', OLD.quantidade, ' | V.Unit:', OLD.valor_unitario, ' | Total:', OLD.valor_compra), 
           NULL, 
           NOW());
END ##

-- 5. PROCEDURE: Gerar Relatório Mensal
CREATE PROCEDURE sp_GerarRelatorioMensal(IN p_data_referencia DATE)
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE v_idAtivo INT;
    DECLARE v_valor_total DECIMAL(15,2);

    DECLARE cur CURSOR FOR SELECT id FROM ativos;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO v_idAtivo;
        
        IF done THEN 
            LEAVE read_loop; 
        END IF;

        -- Calcula o valor total do ativo atual baseado nas compras
        SELECT SUM(c.quantidade * a.valor_unitario)
        INTO v_valor_total
        FROM Compras c
        JOIN ativos a ON c.idAtivo = a.id
        WHERE a.id = v_idAtivo;

        INSERT INTO historico_mensal (idAtivo, valor_total_posicao, data_referencia)
        VALUES (v_idAtivo, IFNULL(v_valor_total, 0), p_data_referencia);
        
    END LOOP;

    CLOSE cur;
END ##

DELIMITER ;




DELIMITER ##
CREATE PROCEDURE totalPorTicker()
BEGIN
	SELECT SUM(c.quantidade) AS total_cotas, a.ticker FROM compras c
	INNER JOIN ativos a ON c.idAtivo = a.id
	GROUP BY a.ticker;
END##
DELIMITER ;