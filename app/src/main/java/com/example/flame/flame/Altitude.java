package com.example.flame.flame;

public class Altitude {
    private float altitude = 0;
    private float basedSpeed = 1f;
    private float SpeedPrecentage;
    final private float altitude1 = 12000f;
    final private float altitude2 = 25000f;
    final private float altitude3 = 40000f;
    

    public void setAltitude(float altitude) {
        this.altitude += altitude;
    }
    public float getAltitude() {
        return this.altitude;
    }
    
    public float getSpeedPrecentage() {
        if (this.altitude < this.altitude1) {
            return basedSpeed * 1f;
        }
        else if(this.altitude < this.altitude2) {
            return basedSpeed * 1.25f;
        }
        else if(this.altitude < this.altitude3) {
            return basedSpeed * 1.50f;
        }
        else return basedSpeed * 1.75f;
    }
    
    
}
