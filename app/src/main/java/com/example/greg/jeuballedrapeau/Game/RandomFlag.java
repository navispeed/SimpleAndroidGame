package com.example.greg.jeuballedrapeau.Game;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Pair;
import com.example.greg.jeuballedrapeau.Entity.Flag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomFlag implements Game {

    private static final Random r = new Random();
    private RandomFlagListener listener;
    private Map<Integer, Flag> flagById;
    private Long millis;
    private Pair<Integer, Integer> screenSize;

    /**
     * Definition d'un listener pour respecter les règles de l'OO
     */
    public interface RandomFlagListener {
        void putFlagOn(Integer id, Flag f);

        void updateFlags(List<Pair<Integer, Flag>> flags);

        void updateTime(Integer tick);
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void run(Pair<Integer, Integer> screenSize) {
        this.flagById = new HashMap<>();
        this.screenSize = screenSize;
        this.millis = System.currentTimeMillis();
        Handler handler = new Handler();
        int delay = 100; //milliseconds

        handler.postDelayed(new Runnable() {
            public void run() {
                listener.updateTime(Math.round(System.currentTimeMillis() - millis)); // On met à jour le timer avec le temps écoulé
                moveAll();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    public void save(String name) {
        //Non implementé
    }

    @Override
    public void load(String name) {
        //Non implementé
    }

    public void setListener(RandomFlagListener listener) {
        this.listener = listener;
    }

    /**
     * Ajout un flag de couleur aléatoire
     */
    public void addRandomFlag() {
        final int id = flagById.size();
        final Flag flag = new Flag(id, ThreadLocalRandom.current().nextInt(0, screenSize.first - Flag.getSize()), ThreadLocalRandom.current().nextInt(0, screenSize.second - Flag.getSize()));
        this.flagById.put(id, flag);
        this.listener.putFlagOn(id, flag);
    }

    public void sendClick(Integer id) {

//        final int[] delay = {500}; //Multithread
//
//        flagById.entrySet().stream().filter(entry -> !entry.getValue().isDisabled()).forEach(entry -> {
//            final Flag flag = entry.getValue();
//            flag.disable();
//            SetTimeout.apply(() -> this.listener.updateFlag(entry.getKey(), flag), delay[0]);
//            delay[0] += 100;
//        });
        final Flag flag = this.flagById.get(id);
        flag.disable();
        this.listener.updateFlags(Collections.singletonList(Pair.create(id, flag)));
    }

    private void moveAll() {
        this.flagById.entrySet().stream().filter(entry -> !entry.getValue().isDisabled()).forEach(entry -> {
            final Flag f = entry.getValue();
            f.setX((f.getX() + f.getSpeedVector().first) % this.screenSize.first);
            f.setY((f.getY() + f.getSpeedVector().second) % this.screenSize.second);
            this.listener.updateFlags(Collections.singletonList(Pair.create(entry.getKey(), f)));
        });
    }

}
