package com.example.greg.jeuballedrapeau.Helpers;

public class SetTimeout {
    public static void apply(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}
