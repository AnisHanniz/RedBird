package com.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    private TextView scoreTextView;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        AppConstants.setContext(getApplicationContext());
        AppConstants.getGameEngine().resetGame();
        scoreTextView = findViewById(R.id.scoreTextView);
        gameView = new GameView(this, null);
        setContentView(gameView);
    }
}
