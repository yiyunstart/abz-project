package com.abz.statemachine.action;

import lombok.extern.java.Log;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Log
@Component
public class EntryAction implements Action<String, String> {
    @Override
    public void execute(StateContext context) {
        log.info("触发动作：EntryAction，事件名称："+context.getEvent()+"，订单ID:"+ context.getMessageHeaders().get("order", Integer.class));

    }
}
