package com.example.greg.jeuballedrapeau.Activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.greg.jeuballedrapeau.Game.Game;
import com.example.greg.jeuballedrapeau.Game.GameFactory;
import com.example.greg.jeuballedrapeau.R;

@SuppressLint("Registered") //Cette classe est abstraite, pas besoin de l'enregistrer en tant qu'activity
public class AbstractGameActivity<T extends Game> extends AppCompatActivity {

    private T game;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected AbstractGameActivity(Class<T> tClass) {
        this.game = GameFactory.build(tClass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected T getGame() {
        return this.game;
    }

}
