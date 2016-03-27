package com.example.flame.flame;


import java.util.Timer;
import java.util.TimerTask;

public class Flame {

    static public float BASEDFLAMEHIGHT = 100f;//100 pixel
    
    final static private float BASEDSPEED = 10f; // 10 pixel per milliseconds
    
    private float flameHeight = 100f;
    
    private boolean isPress;
    
    private float flameWidth = 20f;
    
    private float speed;
    
    private Gas gas;
    private Rope rope;
    private Altitude altitude;

    private float positionTopX,positionTopY;
    private float positionBottomX,positionBottomY;

    private float changeSize = 1.1f; //当被按住的时候，如果可能，火焰高度变大的倍率

    private float positionX,positionY;

    Flame() {
        this.setFlameHeight(Flame.BASEDFLAMEHIGHT);
    }
    
    private void setSpeed() {
        this.speed = this.rope.getSpeedPercentage() * this.altitude.getSpeedPrecentage() *  Flame.BASEDSPEED;
    }
    
    public float getSpeed() {
        setSpeed();
        return this.speed;
    }
    
    public void useItem() {   //使用道具，保留方法
        
    }
    
    public void setRope(Rope rope) {

        this.rope = rope;
        this.positionBottomX = rope.getPositionTopX() + this.flameWidth /2;
        this.positionBottomY = 1000;
        this.positionTopX = this.positionBottomX - this.flameWidth;
        this.positionTopY = this.positionBottomY - this.flameHeight;

    }

    private void setFlameHeight(float height) {
        this.flameHeight = height;
    }
    
    public float getFlameWidth() {
        return this.flameWidth;
    }

    public void onPress() {


    }

    public void onUnPress() {

    }
    public void setAltitude(Altitude altitude) {
        this.altitude = altitude;
    }

    public float getFlameHeight() {

        return flameHeight;
    }

    public Gas getGas() {
        return this.gas;
    }

    public void setPositionTop(float x, float y) {
        this.positionTopX = x;
        this.positionTopY = y;
    }
    public void setPositionBottom(float x, float y) {
        this.positionBottomX = x;
        this.positionBottomY = y;
    }

    public float getPositionTopX() {
        return this.positionTopX;
    }
    public float getPositionTopY() {
        return this.positionTopY;
    }
    public float getPositionBottomX() {
        return this.positionBottomX;
    }
    public float getPositionBottomY() {
        return this.positionBottomY;
    }

 
}
