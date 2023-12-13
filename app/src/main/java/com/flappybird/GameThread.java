package com.flappybird;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import static com.flappybird.AppConstants.context;
import static com.flappybird.AppConstants.gameEngine;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private long delay = 33;

    public GameThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        isRunning = true;
    }

    @Override
    public void run() {
        long startTime, loopTime;
        while (isRunning) {
            startTime = System.currentTimeMillis();
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    synchronized (surfaceHolder) {
                        gameEngine.updateAndDrawBackgroundImage(canvas);
                        gameEngine.updateAndDrawPipes(canvas);
                        gameEngine.updateAndDrawBird(canvas);
                        if (gameEngine.collisionAvecObstacle()) {
                            AppConstants.gameEngine.gameOver(canvas);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    ((GameActivity)context).finish();
                                }
                            }, 3000);
                        }
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            loopTime = System.currentTimeMillis() - startTime;
            if (loopTime < delay) {
                try {
                    Thread.sleep(delay - loopTime);
                } catch (InterruptedException e) {
                    Log.e("GameThread", "Interrupted while sleeping", e);
                }
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
