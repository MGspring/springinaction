package com.example.tacos.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.tacos.Order1;
import com.example.tacos.Taco;
import com.fasterxml.jackson.databind.ObjectMapper;


@Repository
	public class JdbcOrderRepository implements Order1Repository{

		private SimpleJdbcInsert orderInserter;
		private SimpleJdbcInsert orderTacoInserter;
		private ObjectMapper objectMapper;

	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc)
			.withTableName("Taco_Order")
			.usingGeneratedKeyColumns("id");

		this.orderTacoInserter = new SimpleJdbcInsert(jdbc) {
			.withTableName("Taco_Order_Tacos");

		this.objectMapper =new ObjectMapper();
		}
	}

	@Override
	public Order1 save(Order1 order) {
		@SuppressWarnings("unchecked")
		Map<String, Object> values =
			objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt());

		long orderId =
			orderInserter
				.executeAndReturnKey(values)
				.longValue();
		return orderId;
	}

	private void saveTacoToOrder(Taco taco, long orderId) {
		Map<String,Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());
		orderTacoInserter.execute(values);
	}
}
