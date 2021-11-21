package com.example.memory;

public class Card{

    private boolean visible = false;
    private int value;
    private Position pos;

    public int getValue() {
        return value;
    }

    public Card(Position p, int v){
        this.value = v;
        pos = p;
    }

    public void setVisible(boolean state){
        this.visible = state;
    }

    public boolean isVisible(){
        if(this.visible){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString(){
        return this.value +  ", " + this.pos.posX + ", " + this.pos.posY;
    }

}
