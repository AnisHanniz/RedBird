package com.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Pipe {

    private final Bitmap topPipeBitmap;
    private final Bitmap bottomPipeBitmap;
    private int x;
    private int topY;
    private int bottomY;
    private int gapSize;
    private int velocity;

    private boolean counted;

    public Pipe(int gapSize, int startX, int startY, int minPipeY, int maxPipeY) {
        topPipeBitmap = AppConstants.getBitmapBank().getPipeTop();
        bottomPipeBitmap = AppConstants.getBitmapBank().getPipeBot();
        this.gapSize = gapSize;
        x = startX;

        Random random = new Random();
        int pipeHeight = topPipeBitmap.getHeight() + gapSize + bottomPipeBitmap.getHeight();
        int pipeY = random.nextInt(maxPipeY - minPipeY) + minPipeY;
        topY = pipeY - topPipeBitmap.getHeight();
        bottomY = pipeY + gapSize;
        velocity = 5;
        counted = false;
    }



    public int getX() {
        return x;
    }

    public int getTopY() {
        return topY;
    }

    public int getBottomY() {
        return bottomY;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setTopY(int y) {
        topY = y;
    }

    public void setGapSize(int gapSize) {
        this.gapSize = gapSize;
    }

    public int getBottomPipeY() {
        return bottomY;
    }
    public void setCounted(boolean counted) {
        this.counted = counted;
    }
    public boolean isCounted() {
        return counted;
    }
    public void setY(int pipeY) {
        this.bottomY = pipeY;
    }

    public void setVelocity(int i) {
        velocity=i;
    }

}
