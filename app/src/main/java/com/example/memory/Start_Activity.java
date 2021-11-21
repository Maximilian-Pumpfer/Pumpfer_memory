package com.example.memory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Start_Activity extends AppCompatActivity {

    Spinner size;
    Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_size);
        size = findViewById(R.id.size);
        startGame = findViewById(R.id.setSize);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToGame(view);
            }
        });

    }

    public boolean changeToGame(View view){
        if(size != null){
            String content = size.getSelectedItem().toString();
            if(!content.equals("Select Size")){
                Intent changeScreen = new Intent(this, MemoryActivity.class);
                changeScreen.putExtra("size", size.getSelectedItem().toString());
                startActivity(changeScreen);
                this.finish();
                return true;
            }else{
                Snackbar.make(view, "Spinner muss eine gültige Auswahl haben!", Snackbar.LENGTH_LONG).show();
                return false;
            }
        }else{
            Snackbar.make(view, "Spinner muss eine gültige Auswahl haben!", Snackbar.LENGTH_LONG).show();
            return false;
        }
    }

}
