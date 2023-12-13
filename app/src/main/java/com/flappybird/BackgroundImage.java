package com.flappybird;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
public class BackgroundImage {
    private Bitmap image;
    private int x, y, dx;
    public BackgroundImage() {
        image = AppConstants.getBitmapBank().getBg();
        x = 0;
        y = 0;
        dx = AppConstants.bgSpeed;
    }
    public int getX() {
        return x;
    }

    public int getVelocity() {
        return dx;
    }

    public int getY() {
        return y;
    }

    public void setX(int i) {
        this.x=i;
    }
    public void setSpeed(int i) {
        this.dx=i;
    }
}
