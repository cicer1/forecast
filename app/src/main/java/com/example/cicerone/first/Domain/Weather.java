package com.example.cicerone.first.Domain;

import android.util.Log;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * Created by francesco on 04/09/14.
 */
public class Weather {

    private String image;
    private String weather;
    private String temp;
    private String max;
    private String min;
    private String datetime;
    private String description;

    private static HashMap<String,String> icon;

    static{
        icon = new HashMap<String, String>(){
            {
                put("01d", "sd");put("01n","sn");
                put("02d","pcd");put("02n","pcs");
                put("03d","cd");put("03n","cn");
                put("04d","fd");put("04n","fn");
                put("09d","rd");put("09n","rn");
                put("10d","prd");put("10n","prn");
                put("11d","td");put("11n","tn");
                put("13d","swd");put("13n","swn");

            }
        };
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = icon.get(image); }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
