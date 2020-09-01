package com.abz.statemachine.entity;

import lombok.Data;

@Data
public  class Order {
    int id;
    String state;

    public Order(int id, String state) {
        this.id = id;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", state=" + state + "]";
    }

}
