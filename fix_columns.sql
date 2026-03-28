-- Correção de tipos de coluna para PostgreSQL
-- Execute este script no banco de dados Neon

-- Corrigir tb_products: price deve ser numeric
ALTER TABLE tb_products ALTER COLUMN price TYPE numeric(12,2) USING price::numeric;

-- Corrigir tb_items_orders: quantity deve ser integer, priceUnit deve ser numeric
ALTER TABLE tb_items_orders ALTER COLUMN quantity TYPE integer USING quantity::integer;
ALTER TABLE tb_items_orders ALTER COLUMN priceUnit TYPE numeric(12,2) USING priceUnit::numeric;

-- Corrigir tb_orders: total deve ser numeric, deliveryFee deve ser numeric
ALTER TABLE tb_orders ALTER COLUMN total TYPE numeric(12,2) USING total::numeric;
ALTER TABLE tb_orders ALTER COLUMN deliveryFee TYPE numeric(12,2) USING deliveryFee::numeric;

-- Corrigir tb_payment: amount deve ser numeric
ALTER TABLE tb_payment ALTER COLUMN amount TYPE numeric(12,2) USING amount::numeric;

-- Se houver status como varchar que deveria ser enum
-- ALTER TABLE tb_orders ALTER COLUMN status TYPE varchar(20);
-- ALTER TABLE tb_payment ALTER COLUMN status TYPE varchar(20);

-- Verificar os tipos corrigidos
SELECT table_name, column_name, data_type 
FROM information_schema.columns 
WHERE table_name IN ('tb_orders','tb_items_orders','tb_products','tb_payment','tb_users','tb_clients','tb_categories')
ORDER BY table_name, column_name;
