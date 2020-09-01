package com.abz.statemachine.listener;


import lombok.extern.java.Log;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

/**
 * 错误监控
 */
@Log
public class ErrorStateMachineListener
        extends StateMachineListenerAdapter<String, String> {

    @Override
    public void stateMachineError(StateMachine<String, String> stateMachine, Exception exception) {
        // do something with error
        log.info("检测到错误");
    }
}
