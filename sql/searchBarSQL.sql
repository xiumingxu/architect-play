SELECT 
	i.item_name as itemName,
	i.id as itemId,
	i.sell_counts as sellCounts,
	tempSpec.price_discount as price,
	ii.url as itemImage
FROM items as i
LEFT JOIN items_img as ii on i.id = ii.item_id
LEFT JOIN
	(
		-- 临时表
		SELECT item_id, MIN(price_discount) as price_discount
		FROM items_spec
		-- 注意 group by 才可以找最小
		GROUP BY item_id
	) tempSpec
ON i.id = tempSpec.item_id
--  主图
WHERE ii.is_main = 1

-- 
-- -- 做一个临时表 然后给上面那个表用
-- -- 找最低价 每个item的最小
-- SELECT item_id, MIN(price_discount) as priceDiscount
-- FROM items_spec
-- -- 注意 group by 才可以找最小
-- GROUP BY item_id
-- 	
	