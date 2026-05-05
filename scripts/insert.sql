

INSERT INTO ativos (ticker, nome_empresa, tipo, valor_unitario) 
VALUES ('ITUB4', 'Itaú Unibanco Holding S.A.', 'Ação', 34.50);

INSERT INTO ativos (ticker, nome_empresa, tipo, valor_unitario) 
VALUES ('PETR4', 'Petróleo Brasileiro S.A. Petrobras', 'Ação', 38.20);

INSERT INTO ativos (ticker, nome_empresa, tipo, valor_unitario) 
VALUES ('MXRF11', 'Maxi Renda Fundo de Investimento Imobiliário', 'FII', 10.15);

INSERT INTO ativos (ticker, nome_empresa, tipo, valor_unitario) 
VALUES ('HGLG11', 'CGHG Logística Fundo de Investimento Imobiliário', 'FII', 165.30);



-- Compra de 10 cotas de Itaú (idAtivo 1)
INSERT INTO compras (idAtivo, dataCompra, valor_compra, quantidade, valor_unitario) 
VALUES (1, '2026-05-01', 345.00, 10.00, 34.50);

-- Compra de 100 cotas de MXRF11 (idAtivo 3)
INSERT INTO compras (idAtivo, dataCompra, valor_compra, quantidade, valor_unitario) 
VALUES (3, '2026-05-02', 1015.00, 100.00, 10.15);

-- Compra de 5 cotas de Petrobras (idAtivo 2)
INSERT INTO compras (idAtivo, dataCompra, valor_compra, quantidade, valor_unitario) 
VALUES (2, '2026-05-03', 191.00, 5.00, 38.20);




INSERT INTO audit_log (operacao, dados_antigos, dados_novos, data) 
VALUES ('INSERT', NULL, 'Nova compra registrada: 10 cotas de ITUB4', NOW());

INSERT INTO audit_log (operacao, dados_antigos, dados_novos, data) 
VALUES ('UPDATE', 'Qtd: 10', 'Qtd: 15', NOW());