package com.ssj.paul.smolovjrprogram.db;

/**
 * Created by PAUL on 5/7/2015.
 */
public class SmolovUtil {

    public static int getSets(int index){
        switch (index){
            case 0: case 4: case 8:
                return 6;
            case 1:case 5: case 9:
                return 7;
            case 2:case 6:case 10:
                return 8;
            default:
                return 10;
        }
    }

    public static int getReps(int index){
        switch (index){
            case 0: case 4: case 8:
                return 6;
            case 1:case 5: case 9:
                return 5;
            case 2:case 6:case 10:
                return 4;
            default:
                return 3;
        }
    }

    public static int getWeight(int index, int weight, int increment){

        double percentage;
        int mulitplier=2;

        switch (index){
            case 0: case 4: case 8:
                percentage = .7;
                break;
            case 1:case 5: case 9:
                percentage = .75;
                break;
            case 2:case 6:case 10:
                percentage = .8;
                break;
            default:
                percentage = .85;
        }

        if(index < 8)
            mulitplier=1;
        if(index < 4)
            mulitplier=0;

        return (int)Math.round(weight*percentage+increment*mulitplier);
    }
}
