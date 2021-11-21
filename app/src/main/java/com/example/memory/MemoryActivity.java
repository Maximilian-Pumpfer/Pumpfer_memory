package com.example.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MemoryActivity extends AppCompatActivity {

    private ImageButton[][] buttons;

    private LinearLayout gameField;
    private Playground field;
    private Position previous = null;
    private  int pairsFound = 0;
    private int[] pics = getPicsArray();
    private int player = 0;
    private boolean closed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameField = findViewById(R.id.playingField);
        createPlayfield();
    }

    public void createPlayfield()  {

        String size = getIntent().getStringExtra("size");
        String[] sizes = size.split("x");
        int x = Integer.parseInt(sizes[0]);
        int y = Integer.parseInt((sizes[1]));
        field = new Playground(x, y);
        field.init();
        generateAndAddRows(x, y);
    }

    public void generateAndAddRows(int nrRows, int nrCols){
        ArrayList<LinearLayout> rows = new ArrayList<>();
        buttons = new ImageButton[nrRows][nrCols];
        for(int i = 0;i<nrRows;i++){
            rows.add(new LinearLayout(this));
            gameField.addView(rows.get(rows.size()-1));
            for(int j = 0; j<nrCols; j++){
                buttons[i][j] = generateButton(new Position(i,j));
                rows.get(rows.size()-1).addView(buttons[i][j]);
            }
        }

    }

    public ImageButton generateButton(Position pos){
        ImageButton b = new ImageButton(this);
        b.setImageResource(R.drawable.back);
        int x = field.getX();
        int y = field.getY();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        b.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        b.setForegroundGravity(Gravity.CENTER);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                buttonClick(view);
            }
        });
        ViewGroup.LayoutParams params = b.getLayoutParams();
        params.height = height/(x+1);
        params.width = width/y;
        return b;
    }

    public static int[] getPicsArray() {
        int[] c = new int[20];

        c[0] = R.drawable.i000;
        c[1] = R.drawable.i001;
        c[2] = R.drawable.i002;
        c[3] = R.drawable.i003;
        c[4] = R.drawable.i004;
        c[5] = R.drawable.i005;
        c[6] = R.drawable.i006;
        c[7] = R.drawable.i007;
        c[8] = R.drawable.i008;
        c[9] = R.drawable.i009;
        c[10] = R.drawable.i010;
        c[11] = R.drawable.i011;
        c[12] = R.drawable.i012;
        c[13] = R.drawable.i013;
        c[14] = R.drawable.i014;
        c[15] = R.drawable.i015;
        c[16] = R.drawable.i016;
        c[17] = R.drawable.i017;
        c[18] = R.drawable.i018;
        c[19] = R.drawable.i019;
        return c;
    }

    public void buttonClick(View view){

        if(closed == true) {
            ImageButton ib = (ImageButton) view;
            Position posi = null;

            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    if (buttons[i][j] == ib) {
                        posi = new Position(i, j);
                        break;
                    }
                }
            }

            ib.setImageResource(pics[field.getCard(posi).getValue()]);

            if (field.getCard(posi).isVisible() == false) {
                Card c = field.getCard(posi);
                int val = c.getValue();
                ib.setImageResource(pics[val]);
                c.setVisible(true);
                if (previous == null) {
                    previous = posi;
                } else {
                    closed = false;
                    if (field.isPair(previous, posi)) {
                        pairsFound++;
                        field.updateScore(player);
                        previous = null;
                        closed = true;
                        if (pairsFound == (field.getX() * field.getY()) / 2) {
                            win();
                        }
                    } else {
                        if (player == 1) {
                            player = 0;
                        } else {
                            player++;
                        }
                        closeCards(previous, posi);
                        c.setVisible(false);
                        field.getCard(previous).setVisible(false);
                    }
                }
            }else{
                Snackbar.make(view, "Karte bereits aufgedeckt!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public void closeCards(Position pos1, Position pos2){
        class CloseTask extends TimerTask
        {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    buttons[pos1.getX()][pos1.getY()].setImageResource(R.drawable.back);
                    buttons[pos2.getX()][pos2.getY()].setImageResource(R.drawable.back);
                    previous = null;
                    closed = true;
                });
            }
        }

        Timer timer = new Timer();
        timer.schedule(new CloseTask(),1000);
    }

    public void buttonClick2(View view){
        Intent changeScreen = new Intent(this, Start_Activity.class);
        this.onDestroy();
        startActivity(changeScreen);
    }

    public void win(){
        int[] scores = field.getScore();
        int player1 = scores[0];
        int player2 = scores[1];
        String text = "";
        if(player1 == player2){
            text = "Unentschieden!";
        }else{
            if(player1 > player2){
                text = "Player 1 hat gewonnen! " + player1 + ":" + player2;
            }else{
                text = "Player 2 hat gewonnen! " + player1 + ":" + player2;
            }
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        TextView t = new TextView(this);
        t.setText(text);
        t.setWidth(width);
        t.setHeight(height-(height/4));
        Button b = new Button(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                buttonClick2(view);
            }
        });
        b.setText("New Game");
        gameField.removeAllViews();
        gameField.addView(t);
        gameField.addView(b);
    }

}