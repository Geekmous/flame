package com.example.flame.flame;

import java.util.Random;

public class Rope {

    final static  private float BASEDROPESPEED = 1f;//对影响火焰速度的基准
    final static private int MINROPELENGTH = 6; //绳子长度关于火焰高度的最小倍率
    final static private int MAXROPELENGTH = 10;//绳子长度关于火焰高度的最大倍率
    final static public int NORLMAL_SIZE = 2;  //绳子粗细的常亮，对应整型的数值的设置是为了便于
    final static public int BIG_SIZE = 3;    //在绘图时便于画笔粗细的设置
    final static public int SMALL_SIZE = 1;  //
    final static public int[] ropeSizes = {SMALL_SIZE, NORLMAL_SIZE, BIG_SIZE};
    private float speedPercentage;//对火焰速度的影响因素大小，浮点形式
    private int size;//绳子的粗细

    private float length ;
    private float flameHeight;
    private float positionTopX;
    private float positionTopY;   

    
    Rope() {
        this.setSize(ropeSizes[new Random().nextInt(3)]);
        this.setSpeedPercentage();
        this.setHeight(100f);
        this.setLength();

    }

    public void setHeight(float height) {
        this.flameHeight = height;
    }
    
    private void setSize(int Size) {

        this.size = Size;
    }
    
    private void setSpeedPercentage() {                                       //设置速度影响档
        int temp = this.size - Rope.NORLMAL_SIZE;
        if (temp > 0) {
            speedPercentage = Rope.BASEDROPESPEED * (1 + temp * 0.25f);     //1.25倍正常速度
        }
        else if(temp < 0){
            this.speedPercentage = Rope.BASEDROPESPEED  * (1 - temp * 0.25f); //0.75倍正常速度
        }
        else this.speedPercentage = (float)Rope.NORLMAL_SIZE;                   //正常速度
    }
    
    
    public float getSpeedPercentage() {
        return this.speedPercentage;
    }
    
    private void setLength() {                                                  //设置长度

        this.length =this.flameHeight * (new Random().nextInt(MAXROPELENGTH - MINROPELENGTH) + MINROPELENGTH);
    }
    
    public void setPostionTop(float x, float y) {
        this.positionTopX = x;
        this.positionTopY = y;
    }

    public float getPositionTopX() {

        return this.positionTopX;
    }

    public float getPositionTopY() {
        return this.positionTopY;
    }

    public float getLength() {

        return this.length;
    }

    public int getSize() {
        return this.size;
    }
    
    
}
