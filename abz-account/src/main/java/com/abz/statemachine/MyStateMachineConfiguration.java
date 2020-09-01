package com.abz.statemachine;

import com.abz.statemachine.action.*;
import com.abz.statemachine.listener.StateMachineEventListener;
import com.abz.statemachine.listener.TestStateMachineMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.monitor.StateMachineMonitor;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;

//tag::snippetA[]
@Configuration
@EnableStateMachine
public class MyStateMachineConfiguration
        extends StateMachineConfigurerAdapter<String, String> {

    @Autowired
    private StateMachine<String, String> stateMachine;

    //配置状态
    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states
                .withStates()
                .initial("PLACED")
                .stateEntry("PROCESSING",new EntryAction())
                .stateDo("PROCESSING",new DoAction())
                .stateExit("PROCESSING",new ExitAction())
                .state("SENT")
                .state("DELIVERED");
    }

    //配置状态转换的事件
    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("PLACED").target("PROCESSING")
                .event("PROCESS").action(new TestAction1())
                .and()
                .withExternal()
                .source("PROCESSING").target("SENT")
                .event("SEND").action(new TestAction2())
                .and()
                .withExternal()
                .source("SENT").target("DELIVERED")
                .event("DELIVER").action(new TestAction3());
    }



    //状态持久化设置
    @Bean
    public MyPersistStateMachineHandler persist() {
        return new MyPersistStateMachineHandler(persistStateMachineHandler());
    }
    @Bean
    public PersistStateMachineHandler persistStateMachineHandler() {
        return new PersistStateMachineHandler(stateMachine);
    }

    //监听器
    @Bean
    public StateMachineMonitor<String, String> stateMachineMonitor() {
        return new TestStateMachineMonitor();
    }

    //监听器
    @Bean
    public StateMachineEventListener stateMachineEventListener() {
        StateMachineEventListener listener = new StateMachineEventListener();
        stateMachine.addStateListener(listener);
        return listener;
    }

}
