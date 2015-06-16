/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author root
 */
@Named(value = "ClickCounter")
@SessionScoped
public class ClickCounter implements Serializable {

    private int count;

    /**
     * Creates a new instance of ClickCounter
     */
    public ClickCounter() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        count++;
        System.out.println("Contador de click: " + count);
//        EventBus eventBus = EventBusFactory.getDefault().eventBus();
//        eventBus.publish("/counter", String.valueOf(count));
    }

}
