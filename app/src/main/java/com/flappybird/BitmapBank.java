package com.flappybird;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

public class BitmapBank {

    public static Bitmap backrground_game,score_image,pipeTop,pipeBot;

    Bitmap bird;

    public BitmapBank(Resources resources){
        backrground_game = BitmapFactory.decodeResource(resources,R.drawable.bgingame);
        backrground_game = scaleImage(backrground_game);
        bird = BitmapFactory.decodeResource(resources,R.drawable.bird_frame1);
        //bird = scaleImage(bird);
        pipeBot = BitmapFactory.decodeResource(AppConstants.context.getResources(),R.drawable.pipebot);
        //pipeBot = scaleImage(pipeBot);
        pipeTop =BitmapFactory.decodeResource(AppConstants.context.getResources(),R.drawable.pipetop);
        //pipeTop = scaleImage(pipeTop);
        score_image = BitmapFactory.decodeResource(AppConstants.context.getResources(), R.drawable.score_image);
        //score_image = scaleImage(score_image);

    }
    public Bitmap getBird(){
        return bird;
    }
    public Bitmap getBg(){
        return backrground_game;
    }
    public Bitmap getPipeTop(){
        return pipeTop;
    }
    public Bitmap getPipeBot(){
        return pipeBot;
    }
    public Bitmap getScore_image(){return score_image;}
    public static int getBackgroundWidth(){
        return backrground_game.getWidth();
    }
    public static int getBackgroundHeight(){
        return backrground_game.getHeight();
    }
    public Bitmap scaleImage(Bitmap bitmap){
        float widthHeightRatio = getBackgroundWidth() / getBackgroundHeight();
        int backgroundScaleWidth = (int) widthHeightRatio * AppConstants.SCREEN_HEIGHT;
        return Bitmap.createScaledBitmap(bitmap,backgroundScaleWidth,AppConstants.SCREEN_HEIGHT,false);
    }

}
