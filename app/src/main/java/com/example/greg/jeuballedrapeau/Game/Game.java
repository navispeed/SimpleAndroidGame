package com.example.greg.jeuballedrapeau.Game;

import android.util.Pair;

public interface Game {

    void run(Pair<Integer, Integer> screenSize);
    void save(String name);
    void load(String name);


}
