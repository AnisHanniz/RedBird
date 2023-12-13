package com.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Bird {
    private int birdX,birdY,velocity;
    public Bird(int i, int i1){
        birdX = i;
        birdY = i1;
        velocity = 0;
    }

    public int getVelocity(){
        return velocity;
    }
    public void setVelocity(int velocity){
        this.velocity=velocity;
    }
    public int getX(){
        return birdX;
    }
    public int getY(){
        return birdY;
    }

    public void setX( int birdX){
        this.birdX = birdX;
    }
    public void setY( int birdY){
        this.birdY = birdY;
    }


    public int getWidth() {
        return 57;
    }

    public int getHeight() {
        return 70;
    }
}
