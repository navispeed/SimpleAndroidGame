package com.example.greg.jeuballedrapeau.Activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.greg.jeuballedrapeau.Entity.Flag;
import com.example.greg.jeuballedrapeau.Game.RandomFlag;
import com.example.greg.jeuballedrapeau.R;

import java.util.HashMap;
import java.util.Map;

public class RandomFlagActivity extends AbstractGameActivity<RandomFlag> implements RandomFlag.RandomFlagListener {

    private View view;
    private Pair<Integer, Integer> size;
    private Map<Integer, ImageView> imageById;

    @SuppressLint("UseSparseArrays")
    public RandomFlagActivity() {
        super(RandomFlag.class);
        this.imageById = new HashMap<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_flag);
        this.view = findViewById(R.id.randomFlagContainer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getGame().run();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            this.getGame().addRandomFlag(size);
        });
        this.getGame().setListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //Here you can get the size!
        Log.d("RandomFlagActivity", "Size");
        this.size = new Pair<>(view.getWidth(), view.getHeight());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("RandomFlagActivity", "Start");
    }

    @Override
    public void putFlagOn(Integer id, Flag f) {
        final ImageView img = new ImageView(this);
        final Drawable drawable = ContextCompat.getDrawable(this, f.getDrawableId());
        img.setImageDrawable(drawable);
        img.setX(f.getX());
        img.setY(f.getY());
        this.imageById.put(id, img);
        FrameLayout frameLayout = findViewById(R.id.randomFlagContainer);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(f.getSize(), f.getSize());
        img.setLayoutParams(layoutParams);
        frameLayout.addView(img);
        img.setOnClickListener(v -> getGame().sendClick(id));
        Log.d("Flag created : ", f.toString());
    }

    @Override
    public void updateFlag(Integer id, Flag f) {
        runOnUiThread(() -> {
            final ImageView img = this.imageById.get(id);

            final Drawable drawable = ContextCompat.getDrawable(this, f.getDrawableId());
            img.setImageDrawable(drawable);

            Log.d("Flag updated : ", f.toString());
        });
    }
}
