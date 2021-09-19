package com.abner.trabalho.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText jogadorXNome, jogadorONome;
    private Button iniciarJogoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Jogo da Velha");

        jogadorXNome = findViewById(R.id.playerXName);
        jogadorONome = findViewById(R.id.playerOName);
        iniciarJogoButton = findViewById(R.id.startGameButton);

        iniciarJogoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jogadorXNome.getText().toString().isEmpty()) jogadorXNome.setError("É necessário informar um nome");
                else if(jogadorONome.getText().toString().isEmpty()) jogadorONome.setError("É necessário informar um nome");
                else {
                    Intent jogoIntent = new Intent(getBaseContext(), JogoActivity.class);
                    jogoIntent.putExtra("jogadorXNome", jogadorXNome.getText().toString());
                    jogoIntent.putExtra("jogadorONome", jogadorONome.getText().toString());
                    startActivity(jogoIntent);
                }
            }
        });
    }
}