/*
 * Copyright 2015-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abz.statemachine;

import com.abz.statemachine.entity.Order;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler.PersistStateChangeListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 将状态持久化到数据库
 */
@Log
public class MyPersistStateMachineHandler {

	private final PersistStateMachineHandler handler;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final PersistStateChangeListener listener = new LocalPersistStateChangeListener();

	public MyPersistStateMachineHandler(PersistStateMachineHandler handler) {
		this.handler = handler;
		this.handler.addPersistStateChangeListener(listener);
	}

	public String listDbEntries() {
		List<Order> orders = jdbcTemplate.query(
		        "select id, state from orders",
		        new RowMapper<Order>() {
		            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	return new Order(rs.getInt("id"), rs.getString("state"));
		            }
		        });
		StringBuilder buf = new StringBuilder();
		for (Order order : orders) {
			buf.append(order);
			buf.append("\n");
		}
		return buf.toString();
	}

	public void change(int order, String event) {
		Order o = jdbcTemplate.queryForObject("select id, state from orders where id = ?", new Object[] { order },
				new RowMapper<Order>() {
					public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new Order(rs.getInt("id"), rs.getString("state"));
					}
				});
		Message<String> message = MessageBuilder
				.withPayload(event).setHeader("order", order).build();

		boolean ret = handler.handleEventWithState(message, o.getState());

		log.info("修改状态执行结果："+ret);
	}

	private class LocalPersistStateChangeListener implements PersistStateChangeListener {

		@Override
		public void onPersist(State<String, String> state, Message<String> message,
				Transition<String, String> transition, StateMachine<String, String> stateMachine) {
			if (message != null && message.getHeaders().containsKey("order")) {
				log.info("全局监听> 事件："+ message.getPayload()
						+",原状态:"+transition.getSource().getId()
						+",目标状态:"+transition.getTarget().getId());
				Integer order = message.getHeaders().get("order", Integer.class);
				jdbcTemplate.update("update orders set state = ? where id = ?", state.getId(), order);
			}
		}
	}

}
