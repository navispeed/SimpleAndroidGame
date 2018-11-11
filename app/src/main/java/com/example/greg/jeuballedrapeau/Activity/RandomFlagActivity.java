package com.example.greg.jeuballedrapeau.Activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.greg.jeuballedrapeau.Entity.Flag;
import com.example.greg.jeuballedrapeau.Game.RandomFlag;
import com.example.greg.jeuballedrapeau.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomFlagActivity extends AbstractGameActivity<RandomFlag> implements RandomFlag.RandomFlagListener {

    private View view;
    private TextView timer;
    private Map<Integer, ImageView> imageById;
    private boolean longClick;
    private Handler longClickHandler;


    @SuppressLint("UseSparseArrays")

    public RandomFlagActivity() {
        super(RandomFlag.class);
        this.imageById = new HashMap<>();
        this.longClick = false;
        this.longClickHandler = new Handler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_flag);
        this.view = findViewById(R.id.randomFlagContainer);
        this.timer = findViewById(R.id.timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            this.longClick = false;
            this.getGame().addRandomFlag();
        });
        fab.setOnLongClickListener(v -> {
            this.longClick = true;
            this.handleLongClick();
            return false;
        });
        this.getGame().setListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //Here you can get the size!
        Log.d("RandomFlagActivity", "Size");
        this.getGame().run(new Pair<>(view.getWidth(), view.getHeight()));
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Flag.getSize(), Flag.getSize());
        img.setLayoutParams(layoutParams);
        frameLayout.addView(img);
        img.setOnClickListener(v -> getGame().sendClick(id));
        Log.d("Flag created : ", f.toString());
    }

    @Override
    public synchronized void updateFlags(List<Pair<Integer, Flag>> flags) {
        runOnUiThread(() -> flags.forEach(entry -> {
            final Drawable drawable = ContextCompat.getDrawable(this, entry.second.getDrawableId());

            final ImageView img = this.imageById.get(entry.first);


            img.setImageDrawable(drawable);
            img.setX(entry.second.getX());
            img.setY(entry.second.getY());

            Log.d("Flag updated : ", entry.second.toString());
        }));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void updateTime(Integer tick) {
        runOnUiThread(() -> {
            final int seconds = tick / 1000;
            this.timer.setText(String.format("%2d:%d", seconds / 60, seconds % 60));
        });
    }

    private void handleLongClick() {
        long delay = 100; //Delai entre chaque repetition
        this.longClickHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getGame().addRandomFlag();
                if (longClick)
                    longClickHandler.postDelayed(this, delay);
            }
        }, delay);
    }
}
