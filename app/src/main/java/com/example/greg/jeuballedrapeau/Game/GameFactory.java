package com.example.greg.jeuballedrapeau.Game;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.InvocationTargetException;

public class GameFactory {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static <T extends Game> T build(Class<T> toBuild) {
        try {
            return toBuild.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
