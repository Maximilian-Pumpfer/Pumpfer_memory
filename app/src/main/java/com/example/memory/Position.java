package com.example.memory;

public class Position {

    public int posX;
    public int posY;

    public Position() {

    }


    public int getX() {
        return this.posX;
    }

    public int getY(){
        return this.posY;
    }

    public Position(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return posX == position.posX && posY == position.posY;
    }

    public String toString(){
        return "x: " + posX + " y: " + posY;
    }
}
