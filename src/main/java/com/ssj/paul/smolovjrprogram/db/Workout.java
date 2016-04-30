package com.ssj.paul.smolovjrprogram.db;


/**
 * Created by PAUL on 5/1/2015.
 */
public class Workout {

    private int id;
    private int sets;
    private int reps;
    private int weight;
    private String date;

    public Workout() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return date + ": " + sets + "x" + reps + " @" + weight;
    }

}
