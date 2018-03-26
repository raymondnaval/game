/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raymondn.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

/**
 *
 * @author raymondnaval
 */
public class GameStateManager {
    private Stack<State> states;
    
    public GameStateManager() {
        states = new Stack<State>();
    }
    
    public void push(State state) {
        states.push(state);
    }
    
    public void pop() {
        states.pop().dispose();
    }
    
    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }
    
    public void update(float dt) {
        states.peek().update(dt);
    }
}
