package com.abz.statemachine.listener;


import lombok.extern.java.Log;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.monitor.AbstractStateMachineMonitor;
import org.springframework.statemachine.transition.Transition;

/**
 * 监听耗时？
 */
@Log
public class TestStateMachineMonitor extends AbstractStateMachineMonitor<String, String> {

    @Override
    public void transition(StateMachine<String, String> stateMachine, Transition<String, String> transition, long duration) {
        log.info("全局监听> ："+ stateMachine.getState().getId()
                +",原状态:"+transition.getSource().getId()
                +",目标状态:"+transition.getTarget().getId()+"，耗时："+duration);
    }

    @Override
    public void action(StateMachine<String, String> stateMachine, Action<String, String> action, long duration) {
        log.info("全局监听> ："+ stateMachine.getState().getId()
                +",动作类型:"+action.getClass().getSimpleName()+"，耗时："+duration);
    }
}
