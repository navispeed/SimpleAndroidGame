package com.example.greg.jeuballedrapeau.Entity;

import com.example.greg.jeuballedrapeau.R;

import java.util.Random;

public class Flag {

    private static final int[] DRAWABLE_IDS = new int[]{R.drawable.blue_flag, R.drawable.red_flag};
    private static final int DISABLED_DRAWABLE_ID = R.drawable.grey_flag;
    private static final int size = 100;
    private static final Random r = new Random();

    private final Integer x;
    private final Integer y;
    private int drawable_id;

    public Flag(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.drawable_id = DRAWABLE_IDS[Math.abs(r.nextInt()) % DRAWABLE_IDS.length]; //Selection aléatoire d'un drawable

    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public int getDrawableId() {
        return this.drawable_id;
    }

    public static int getSize() {
        return size;
    }

    public void disable() {
        this.drawable_id = DISABLED_DRAWABLE_ID;
    }

    public boolean isDisabled() {
        return this.drawable_id == DISABLED_DRAWABLE_ID;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
