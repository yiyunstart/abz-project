package com.abz.statemachine.listener;

import lombok.extern.java.Log;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Log
public class StateMachineEventListener  extends StateMachineListenerAdapter<String, String> {

    @Override
    public void stateChanged(State<String, String> from, State<String, String> to) {
        log.info("监听：stateChanged");
    }

    @Override
    public void stateEntered(State<String, String> state) {
        log.info("监听：stateEntered");

    }

    @Override
    public void stateExited(State<String, String> state) {
        log.info("监听：stateExited");

    }

    @Override
    public void eventNotAccepted(Message<String> event) {
        log.info("监听：eventNotAccepted");
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transition(Transition<String, String> transition) {
        log.info("监听：transition");

    }

    @Override
    public void transitionStarted(Transition<String, String> transition) {

        log.info("监听：transitionStarted");
    }

    @Override
    public void transitionEnded(Transition<String, String> transition) {
        log.info("监听：transitionEnded");
    }

    @Override
    public void stateMachineStarted(StateMachine<String, String> stateMachine) {
        log.info("监听：stateMachineStarted");
    }

    @Override
    public void stateMachineStopped(StateMachine<String, String> stateMachine) {
        log.info("监听：stateMachineStopped");
    }

    @Override
    public void stateMachineError(StateMachine<String, String> stateMachine, Exception exception) {
        log.info("监听：stateMachineError");
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {
        log.info("监听：extendedStateChanged");
    }

    @Override
    public void stateContext(StateContext<String, String> stateContext) {
        log.info("监听：stateContext");
    }
}
