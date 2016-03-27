package com.example.flame.flame;

import java.util.Timer;
import java.util.TimerTask;

public class Gas {
    private int maxGas;
    private int currentGas;
    private float increaseSpeed; //每0.5s增加值
    private float descreaseSpeed;//每0.5s减少值
    private float recoveryTime = 7.5f;
    private float insistTime = 5;
    private float changePerSecond = 0.1f;

    Gas(){
        this.maxGas = 100 ;   //100px
        this.currentGas = this.maxGas;
        this.increaseSpeed = maxGas / (recoveryTime * changePerSecond);
        this.descreaseSpeed = maxGas / (recoveryTime * changePerSecond);

    }
    
    public int getCurrentGas() {
        return this.currentGas;
    }
    
    public void increaseNaturally() {     
      changeGas(increaseSpeed);
    }

    public void changeGas(float change) {  //改变瓦斯值，change正值为增加，负值为减少
        if(change > 0) {
            if (currentGas + change > maxGas) {
                this.currentGas = this.maxGas;
            }
            else this.currentGas += change;
        }
        if(change < 0) {

            if (currentGas + change < 0) {
                this.currentGas = 0;
            }
            else this.currentGas += change;
        }
        
    }
    
    public void descraeseSpeedOnTouch() {
                changeGas(descreaseSpeed);

    }

}
