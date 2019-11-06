package com.aldrick.xogame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XOgame_main extends AppCompatActivity implements View.OnClickListener {

    // if turn is odd = circle, even = cross
    private int turn = 0;
    private boolean gameIsActive = true;
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xogame_main);
    }

    @Override
    public void onClick(View v) {
        int tappedCounter = Integer.parseInt(v.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = turn % 2;

            switch (v.getId()) {
                case R.id.spot1:
                case R.id.spot2:
                case R.id.spot3:
                case R.id.spot4:
                case R.id.spot5:
                case R.id.spot6:
                case R.id.spot7:
                case R.id.spot8:
                case R.id.spot9:
                    ImageView circle = (ImageView) v;

                    circle.setTranslationY(-1000f);

                    if (turn % 2 == 0) {
                        circle.setImageResource(R.drawable.circle);
                    } else {
                        circle.setImageResource(R.drawable.cross);
                    }

                    circle.animate().translationYBy(1000f).setDuration(300);

                    turn += 1;
                    break;
                case R.id.btnPlayAgain:
                    playAgain();
                    break;
                default:
                    break;
            }

            // Someone has won
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {
                    TextView winnerMessage = findViewById(R.id.txtWinnerMessage);
                    LinearLayout layout = findViewById(R.id.layoutPlayAgain);

                    String winner;

                    if (gameState[winningPosition[0]] == 0) winner = "circle";
                    else winner = "cross";

                    winnerMessage.setText(winner.concat(" has won !"));
                    layout.setVisibility(View.VISIBLE);

                    gameIsActive = false;
                } else {
                    boolean gameIsOver = true;

                    for (int counterState : gameState) if (counterState == 2) gameIsOver = false;

                    if (gameIsOver) {
                        TextView winnerMessage = findViewById(R.id.txtWinnerMessage);
                        LinearLayout layout = findViewById(R.id.layoutPlayAgain);
                        String message = "";

                        winnerMessage.setText(message.concat("It's a draw !"));
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain() {
        LinearLayout layout = findViewById(R.id.layoutPlayAgain);
        GridLayout gridLayout = findViewById(R.id.gridLayoutBoard);

        turn = 0;
        layout.setVisibility(View.INVISIBLE);

        for (int i = 0; i < gameState.length; i++) gameState[i] = 2;
        for (int i = 0; i < gridLayout.getChildCount(); i++) ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
    }
}
