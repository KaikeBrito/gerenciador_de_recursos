-- -------------------------------------------
-- SCRIPT DE CARGA INICIAL (import.sql)
-- Projeto: Lar Francisco de Assis
-- -------------------------------------------
-- Este script popula as tabelas de "catálogo"
-- para que a API possa ser testada.
-- -------------------------------------------

-- 1. CATEGORIAS (Para agrupar produtos)
INSERT INTO categorias (id, nome, descricao) VALUES (1, 'Alimentos Não Perecíveis', 'Itens de cesta básica, grãos, enlatados.');
INSERT INTO categorias (id, nome, descricao) VALUES (2, 'Higiene Geriátrica', 'Fraldas G, lenços umedecidos, pomadas.');
INSERT INTO categorias (id, nome, descricao) VALUES (3, 'Material de Limpeza', 'Água sanitária, desinfetante, sabão.');
INSERT INTO categorias (id, nome, descricao) VALUES (4, 'Equipamentos de Apoio', 'Cadeiras de rodas, muletas, camas hospitalares.');

-- 2. LOCALIZAÇÕES (Onde o estoque é guardado)
INSERT INTO localizacoes (id, nome, descricao) VALUES (1, 'Almoxarifado Principal', 'Estantes A/B (Não perecíveis e Limpeza)');
INSERT INTO localizacoes (id, nome, descricao) VALUES (2, 'Despensa da Cozinha', 'Armários (Alimentos de consumo rápido)');
INSERT INTO localizacoes (id, nome, descricao) VALUES (3, 'Sala de Higiene', 'Armário (Itens de higiene e fraldas)');
INSERT INTO localizacoes (id, nome, descricao) VALUES (4, 'Sala de Equipamentos', 'Sala anexa (Cadeiras de rodas, etc)');

-- 3. DOADORES (Para registrar a origem das doações)
INSERT INTO doadores (id, nome, tipo_pessoa, documento, email, telefone) VALUES (1, 'Supermercado Bom Preço Ltda', 'PJ', '12.345.678/0001-99', 'contato@bompreco.com', '853030-1010');
INSERT INTO doadores (id, nome, tipo_pessoa, documento, email, telefone) VALUES (2, 'Ana Silva Costa', 'PF', '111.222.333-44', 'ana.silva@email.com', '859999-1010');
INSERT INTO doadores (id, nome, tipo_pessoa, documento, email, telefone) VALUES (3, 'Farmácia Pague Menos S/A', 'PJ', '07.728.495/0001-57', 'doacoes@paguemenos.com.br', '854000-1000');

-- 4. PRODUTOS (Os itens que serão gerenciados)
INSERT INTO produtos (id, id_categoria, nome, descricao, unidade_medida) VALUES (1, 1, 'Arroz (Pacote 1kg)', 'Arroz branco tipo 1', 'PCT');
INSERT INTO produtos (id, id_categoria, nome, descricao, unidade_medida) VALUES (2, 1, 'Feijão Carioca (Pacote 1kg)', 'Feijão carioca tipo 1', 'PCT');
INSERT INTO produtos (id, id_categoria, nome, descricao, unidade_medida) VALUES (3, 2, 'Fralda Geriátrica G (Pacote 20un)', 'Fralda Geriátrica G noturna', 'PCT');
INSERT INTO produtos (id, id_categoria, nome, descricao, unidade_medida) VALUES (4, 2, 'Sabonete Líquido (500ml)', 'Sabonete líquido hipoalergênico', 'UN');
INSERT INTO produtos (id, id_categoria, nome, descricao, unidade_medida) VALUES (5, 3, 'Água Sanitária (1L)', 'Água sanitária 1L', 'L');
INSERT INTO produtos (id, id_categoria, nome, descricao, unidade_medida) VALUES (6, 3, 'Desinfetante Lavanda (2L)', 'Desinfetante 2L', 'L');
INSERT INTO produtos (id, id_categoria, nome, descricao, unidade_medida) VALUES (7, 4, 'Cadeira de Rodas Simples', 'Cadeira de rodas para transporte', 'UN');

-- O estoque começa VAZIO.
-- Use a API para popular (POST /api/v1/movimentacoes/entradas)