package com.example.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Playground {

    public static ArrayList<Card> cards = new ArrayList<>();
    private int whosOnTurn;
    private int[] score = new int[2];
    public int x;
    public int y;

    public void init(){
        HashMap<Integer, Integer> values = new HashMap<>();
        for(int i = 0;i<(x*y/2);i++){
            values.put(i, 0);
        }
        int val = 0;
        Random r = new Random();
        for(int i = 0;i<x;i++){
            for(int a = 0;a<y;a++){
                boolean right = false;
                while(right == false){
                    val = r.nextInt((x*y/2));
                    if(values.get(val) != 2){
                        right = true;
                        Position p = new Position(x,y);
                        Card c = new Card(p, val);
                        cards.add(c);
                        int amount = values.get(val);
                        values.replace(val, amount+1);
                    }
                }
            }
        }
        for(int i = 0; i < cards.size(); i++){
          //  System.out.println(cards.get(i).toString());
        }
    }

    public boolean isPair(Position p1, Position p2){
        int xx = p1.getX();
        int yy = p1.getY();
        int ges = 0;
        int xx2 = p2.getX();
        int yy2 = p2.getY();
        int ges2 = 0;
        ges = (xx * x) + xx + yy;
        ges2 = (xx2 * x) + xx2 + yy2;

        return cards.get(ges).getValue() == cards.get(ges2).getValue();
    }

    public Card getCard(Position pos){
        int xx = pos.getX();
        int yy = pos.getY();
        int ges = 0;
        ges = (xx * x) + xx + yy;
        return cards.get(ges);
    }

    public void updateScore(int player){
        score[player] += 1;
    }

    public int[] getScore(){
        return score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Playground(int x, int y){
        this.x = x;
        this.y = y;
    }

}
