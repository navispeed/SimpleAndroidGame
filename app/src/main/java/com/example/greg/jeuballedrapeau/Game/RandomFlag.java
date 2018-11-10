package com.example.greg.jeuballedrapeau.Game;

import android.annotation.SuppressLint;
import android.util.Pair;
import com.example.greg.jeuballedrapeau.Entity.Flag;
import com.example.greg.jeuballedrapeau.Helpers.SetTimeout;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RandomFlag implements Game {

    private RandomFlagListener listener;
    private Map<Integer, Flag> flagById;

    /**
     * Definition d'un listener pour respecter les règles de l'OO
     */
    public interface RandomFlagListener {
        void putFlagOn(Integer id, Flag f);

        void updateFlag(Integer id, Flag f);
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void run() {
        this.flagById = new HashMap<>();
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
    public synchronized void addRandomFlag(Pair<Integer, Integer> screenSize) {
        final int id = flagById.size();
        final Flag flag = new Flag(ThreadLocalRandom.current().nextInt(0, screenSize.first - Flag.getSize()), ThreadLocalRandom.current().nextInt(0, screenSize.second - Flag.getSize()));
        this.flagById.put(id, flag);
        this.listener.putFlagOn(id, flag);
    }

    public void sendClick(Integer id) {

        final int[] delay = {500}; //Multithread

        flagById.entrySet().stream().filter(entry -> !entry.getValue().isDisabled()).forEach(entry -> {
            final Flag flag = entry.getValue();
            flag.disable();
            SetTimeout.apply(() -> this.listener.updateFlag(entry.getKey(), flag), delay[0]);
            delay[0] += 100;
        });
    }

}
