package com.flappybird;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    GameThread gameThread;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView();
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if(!gameThread.isRunning()){
            gameThread = new GameThread (surfaceHolder);
        }else{
            gameThread.start();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        if(gameThread.isRunning()){
            gameThread.setIsRunning(false);
            boolean retry = true;
            while(retry){
                try{
                    gameThread.join();
                    retry=false;
                }catch (InterruptedException e){}
            }

        }
    }



    void InitView(){
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        gameThread= new GameThread(holder);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (AppConstants.gameEngine.getGameState() == 0) {
                AppConstants.gameEngine.setGameState(1);
            } else if (AppConstants.gameEngine.getGameState() == 1) {
                AppConstants.gameEngine.getBird().setVelocity(AppConstants.VELOCITY_WHEN_JUMPED);
            } else if (AppConstants.gameEngine.getGameState() == 2) {
                AppConstants.gameEngine.changeActivity();
            }
            return true;
        }
        return false;
    }
}
