package com.abner.trabalho.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class JogoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView jogadorXScore, jogadorOScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;

    private int jogadorXScoreCount, jogadorOScoreCount, roundCount;
    boolean vezDoJogadorX;

    // Jogador O -> 0
    // Jogador X -> 1
    // Vazio     -> 2

    int [] estadoDoJogo = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int [][] posicoesDeVitoria = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jogo_activity);
        setTitle("Jogo da Velha");

        jogadorXScore = findViewById(R.id.playerXScore);
        jogadorOScore = findViewById(R.id.playerOScore);
        playerStatus = findViewById(R.id.playerStatus);
        resetGame = findViewById(R.id.resetGame);

        for (int i = 0; i < buttons.length; i++) {
            String buttonId = "btn_" + i;
            int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        jogadorXScoreCount = 0;
        jogadorOScoreCount = 0;
        vezDoJogadorX = true;
        resetGame.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (!((Button)view).getText().toString().isEmpty()) return;
        String buttonId = view.getResources().getResourceEntryName(view.getId());
        int posicaoPressionada = Integer.parseInt(buttonId.substring(buttonId.length()-1, buttonId.length()));

        if (vezDoJogadorX) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#FFC34A"));
            estadoDoJogo[posicaoPressionada] = 1;
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#70FFEA"));
            estadoDoJogo[posicaoPressionada] = 0;
        }
        roundCount++;
        resetGame.setVisibility(View.VISIBLE);

        if (checarVitoria()) {
            if(vezDoJogadorX) {
                jogadorXScoreCount++;
                atualizarPontuacoes();
                Toast.makeText(this, "Jogador X ganhou!", Toast.LENGTH_SHORT).show();
                jogarNovamente();
            } else {
                jogadorOScoreCount++;
                atualizarPontuacoes();
                Toast.makeText(this, "Jogador O ganhou!", Toast.LENGTH_SHORT).show();
                jogarNovamente();
            }
        } else if(roundCount == 9) {
            jogarNovamente();
            Toast.makeText(this, "Empate!", Toast.LENGTH_SHORT).show();
        } else {
            vezDoJogadorX = !vezDoJogadorX;
        }

        if (jogadorXScoreCount > jogadorOScoreCount) playerStatus.setText("Jogador X está na frente");
        else if (jogadorXScoreCount < jogadorOScoreCount) playerStatus.setText("Jogador O está na frente");
        else if (jogadorXScoreCount == jogadorOScoreCount && jogadorXScoreCount > 0) playerStatus.setText("Temos um empate aqui");

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jogarNovamente();
                jogadorXScoreCount = 0;
                jogadorOScoreCount = 0;
                playerStatus.setText("");
                atualizarPontuacoes();
            }
        });
    }

    public boolean checarVitoria() {
        boolean alguemGanhou = false;
        for (int [] posicaoDeVitoria : posicoesDeVitoria) {
            if (estadoDoJogo[posicaoDeVitoria[0]] == estadoDoJogo[posicaoDeVitoria[1]] &&
                    estadoDoJogo[posicaoDeVitoria[1]] == estadoDoJogo[posicaoDeVitoria[2]] &&
                    estadoDoJogo[posicaoDeVitoria[0]] != 2) alguemGanhou = true;
        }
        return alguemGanhou;
    }

    public void atualizarPontuacoes() {
        jogadorXScore.setText(Integer.toString(jogadorXScoreCount));
        jogadorOScore.setText(Integer.toString(jogadorOScoreCount));
    }

    public void jogarNovamente() {
        roundCount = 0;
        vezDoJogadorX = true;

        for(int i = 0; i < buttons.length; i++) {
            estadoDoJogo[i] = 2;
            buttons[i].setText("");
        }
    }
}