package com.abz.statemachine.listener;

import lombok.extern.java.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.statemachine.event.StateMachineEvent;

@Log
public class StateMachineApplicationEventListener
        implements ApplicationListener<StateMachineEvent> {

    @Override
    public void onApplicationEvent(StateMachineEvent event) {
        log.info("监听上下文事件:"+event.toString());
    }
}
