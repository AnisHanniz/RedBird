package com.flappybird;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class AppConstants {
    static BitmapBank bitmapBank;
    static int SCREEN_WIDTH,SCREEN_HEIGHT,gravity,gap,pipeSpeed,VELOCITY_WHEN_JUMPED,bgSpeed,tubeHeight,tubeWidth,birdHeight,birdWidth;
    static GameEngine gameEngine;
    static Context context;
    public static void initialization(Context context){
        AppConstants.context = context;
        setScreenSize(context);
        bitmapBank = new BitmapBank(context.getResources());
        gameEngine = new GameEngine(context);
        pipeSpeed = 15;
        gap = 500;
        bgSpeed = 10;
        gravity = 3;
        VELOCITY_WHEN_JUMPED = -35;
        tubeHeight = 718;
        tubeWidth = 89;
        birdHeight = 41;
        birdWidth = 57;
    }

    public static BitmapBank getBitmapBank(){
        return bitmapBank;
    }
    public static GameEngine getGameEngine(){
        return gameEngine;
    }
    private static void setScreenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics= new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        AppConstants.SCREEN_WIDTH =width;
        AppConstants.SCREEN_HEIGHT= height;
    }
    public static void setContext(Context ctx) {
        context = ctx;
    }

}
