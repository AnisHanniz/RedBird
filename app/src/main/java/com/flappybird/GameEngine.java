package com.flappybird;

import static com.flappybird.AppConstants.SCREEN_HEIGHT;
import static com.flappybird.AppConstants.SCREEN_WIDTH;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;

public class GameEngine {
    private final Context context;
    private final BackgroundImage backgroundImage;
    private static int gameState, score;
    public Bird bird;
    private Handler handler;
    private ArrayList<Pipe> pipes;

    public GameEngine(Context context) {
        this.context = context;
        backgroundImage = new BackgroundImage();
        bird = new Bird(100, 200);
        gameState = 0;
        pipes = new ArrayList<>();
    }

    public void updateAndDrawBackgroundImage(Canvas canvas) {
        backgroundImage.setX(backgroundImage.getX() - backgroundImage.getVelocity());
        if (backgroundImage.getX() < -BitmapBank.getBackgroundWidth()) {
            backgroundImage.setX(0);
        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getBg(), backgroundImage.getX(),
                backgroundImage.getY(), null);
        if (backgroundImage.getX() < (BitmapBank.getBackgroundWidth() - SCREEN_WIDTH)) {
            canvas.drawBitmap(AppConstants.getBitmapBank().getBg(), backgroundImage.getX() +
                    BitmapBank.getBackgroundWidth(), backgroundImage.getY(), null);
        }
    }

    public void updateAndDrawBird(Canvas canvas) {

        if (gameState == 1) {
            if (bird.getY() < (SCREEN_HEIGHT - AppConstants.birdHeight)
                    || bird.getVelocity() < 0) {

                bird.setVelocity(bird.getVelocity() + AppConstants.gravity);
                bird.setY(bird.getY() + bird.getVelocity());
            }
            // Check for successful passage through pipes
            for (Pipe pipe : pipes) {
                if (pipe.getX() + AppConstants.tubeWidth < bird.getX() && !pipe.isCounted()) {
                    score++;
                    pipe.setCounted(true);
                }
            }

        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getBird(), bird.getX(), bird.getY(), null);
        //SCORE
        canvas.drawBitmap(AppConstants.getBitmapBank().getScore_image(), bird.getX(), 50, null);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(String.valueOf(score), SCREEN_WIDTH / 2, 125, paint);
    }
    public void updateAndDrawPipes(Canvas canvas) {

        // Vérifier si la liste des obstacles est vide ou si le dernier obstacle de la liste est à moins d'un tiers de l'écran de largeur.
        if (pipes.isEmpty() || pipes.get(pipes.size() - 1).getX() < AppConstants.SCREEN_WIDTH - AppConstants.SCREEN_WIDTH / 3) {
            // Ajouter un nouvel obstacle sur le bord droit de l'écran.
            int gap = AppConstants.SCREEN_HEIGHT /120;
            int topTubeY = -AppConstants.tubeHeight + (int) (Math.random() * (AppConstants.SCREEN_HEIGHT / 2 - gap));
            int bottomTubeY = topTubeY + AppConstants.SCREEN_HEIGHT / 3 + AppConstants.tubeHeight + gap;

            Pipe pipe = new Pipe(AppConstants.gap, AppConstants.SCREEN_WIDTH + 300, bottomTubeY,100, 600);
            pipes.add(pipe);
        }

        // Pour chaque obstacle dans la liste d'obstacles, mettre à jour sa position et le dessiner sur le canevas.
        for (Pipe pipe : pipes) {
            if(gameState==1) {
                pipe.setX(pipe.getX() - AppConstants.pipeSpeed);
            }else {}
            // Si l'obstacle est complètement hors de l'écran, le supprimer de la liste des obstacles.
            if (pipe.getX()+3*AppConstants.tubeWidth < 0) {
                pipes.remove(pipe);
                break;
            }

            // Dessiner le haut de l'obstacle.
            canvas.drawBitmap(AppConstants.getBitmapBank().getPipeTop(), pipe.getX(), pipe.getTopY(), null);

            // Dessiner le bas de l'obstacle.
            canvas.drawBitmap(AppConstants.getBitmapBank().getPipeBot(), pipe.getX(), pipe.getBottomPipeY(), null);
        }
    }

    public boolean collisionAvecObstacle() {
        // Parcourir la liste des obstacles.
        for (Pipe pipe : pipes) {
            // Récupérer les positions de l'oiseau et de l'obstacle pour les comparer.
            int birdLeft = bird.getX();
            int birdRight = birdLeft + AppConstants.birdWidth;
            int birdTop = bird.getY();
            int birdBottom = birdTop + AppConstants.birdHeight;
            int topTubeLeft = pipe.getX();
            int topTubeRight = topTubeLeft + AppConstants.tubeWidth;
            int topTubeBottom = pipe.getTopY();
            int bottomTubeTop = pipe.getBottomY();
            // Vérifier si l'oiseau touche le haut ou le bas du tuyau.
            if (birdRight > topTubeLeft && birdLeft < topTubeRight) {
                if (birdTop < topTubeBottom || birdBottom > bottomTubeTop) {
                    return true;
                }
            }
                if (birdTop < 0 || birdBottom > SCREEN_HEIGHT) {
                    return true;
                }
        }
        return false;
    }


    public ArrayList<Pipe> getPipes() {
        return pipes;
    }


    public void changeActivity() {
        if (gameState == 2) {
            // 1. Mettre gameState à 2 pour arrêter le jeu
            gameState = 2;

            // 2. Afficher le score final
            Log.d("GAME OVER", "Score final : ");

            // 3. Créer un Handler pour retarder l'ouverture de la nouvelle activité de 5 secondes
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 4. Ouvrir la nouvelle activité
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }, 2000);
        } else if (gameState == 1) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                pipes.forEach(pipe -> pipe.setVelocity(0));
            }
        }
    }




    public void resetGame() {
        score=0;
        backgroundImage.setX(0);
        backgroundImage.setSpeed(AppConstants.bgSpeed);
        bird.setY(200);
        bird.setVelocity(0);
        gameState = 1;
    }


    public void setGameState(int gameState) {
        GameEngine.gameState = gameState;
    }

    public int getGameState() {
        return gameState;
    }

    public Bird getBird() {
        return bird;
    }

    public void gameOver(Canvas canvas) {
        gameState = 2;
        bird.setVelocity(0);
        backgroundImage.setSpeed(0);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("Game Over", SCREEN_WIDTH / 2 - 250, SCREEN_HEIGHT / 2 - 100, paint);
        canvas.drawText("Score: " + score, SCREEN_WIDTH / 2 - 200, SCREEN_HEIGHT / 2, paint);

        // Attendre 3 secondes avant de rediriger l'utilisateur vers l'écran d'accueil.

        if (handler == null) {
            Looper.prepare();
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            }, 3000);
        }
    }

}