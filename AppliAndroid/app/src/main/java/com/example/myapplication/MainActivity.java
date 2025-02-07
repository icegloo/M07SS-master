package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.SurfaceView;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.MotionEvent;

import com.example.myapplication.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {


    static {
        System.loadLibrary("myapplication");
    }

    private ActivityMainBinding binding;

    //Video


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pour masquer la barre de statut
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
// Masquer la barre de statut et la barre de navigation
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        //SeekBar
        SeekBar TrackBarDeg = binding.seekBar;
        SeekBar TrackBarHauteur = binding.seekBar3;
        SeekBar TrackBarVitesse = binding.seekBar2;
        //Label/Texte
        TextView TextVitesse = binding.textView;
        TextView TextDegre = binding.textView2;
        TextView TextHauteur = binding.textView3;
        //Bouttons

        Button BoutonCmd = binding.button13;
        Button BoutonTrigo = binding.button;
        Button BoutonHorraire = binding.button2;
        Button BoutonDescendre = binding.button8;
        Button BoutonMonter = binding.button12;
        Button BoutonDecoller = binding.button11;
        Button BoutonAtterrir = binding.button10;
        //Edits
        EditText IPDrone = binding.editTextText;

        //Image View
        ImageView FlecheGauche = binding.imageView3;
        ImageView FlecheDroite = binding.imageView4;
        ImageView FlecheBas = binding.imageView5;
        ImageView FlecheHaut = binding.imageView6;


        TextVitesse.setText( TrackBarVitesse.getProgress()+1 + " cm/s");
        TextHauteur.setText( TrackBarHauteur.getProgress()+1 + " cm");
        TextDegre.setText(TrackBarDeg.getProgress()+1 + " deg");

        ImageView imageView = findViewById(R.id.imageView);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            float initialX = 0;
            float initialY = 0;
            float touchX = 0;
            float touchY = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getRawX();
                float y = event.getRawY();

                int parentWidth = ((View) v.getParent()).getWidth();
                int parentHeight = ((View) v.getParent()).getHeight();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        initialX = imageView.getX();
                        initialY = imageView.getY();
                        touchX = x;
                        touchY = y;
                        break;

                    case MotionEvent.ACTION_MOVE:

                        float deltaX = x - touchX;
                        float deltaY = y - touchY;

                        float newX = Math.max(0, Math.min(initialX + deltaX, parentWidth - imageView.getWidth()));
                        float newY = Math.max(0, Math.min(initialY + deltaY, parentHeight - imageView.getHeight()));

                        imageView.animate().x(newX).y(newY).setDuration(0).start();

                        break;

                    case MotionEvent.ACTION_UP:

                        int[] location = new int[2];

                        imageView.getLocationInWindow(location);

                        int imageViewX = location[0];
                        int imageViewY = location[1];

                        ViewGroup parent = (ViewGroup) imageView.getParent();
                        int[] parentLocation = new int[2];
                        parent.getLocationInWindow(parentLocation);

                        int relativeX = imageViewX - parentLocation[0];
                        int relativeY = imageViewY - parentLocation[1];


                        int imageViewWidth = imageView.getWidth();
                        int imageViewHeight = imageView.getHeight();

                        int centerX = (parentWidth - imageViewWidth) / 2;
                        int centerY = (parentHeight - imageViewHeight) / 2;

                        imageView.animate().x(centerX).y(centerY).setDuration(100).start();


                        int relaX = (relativeX*1000/400) - 500;
                        int relaY = (relativeY*1000/400) - 500;

                        Go(-relaX, -relaY, TrackBarVitesse.getProgress());

                        break;
                }
                return true;
            }
        });
        BoutonCmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commande(String.valueOf(IPDrone.getText()));
            }
        });
        TrackBarVitesse.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String TextVit = TrackBarVitesse.getProgress()+1 + " cm/s";
                TextVitesse.setText(TextVit);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        TrackBarHauteur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String TextHaut = TrackBarHauteur.getProgress() + 1 + " cm";
                TextHauteur.setText(TextHaut);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        TrackBarDeg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String TextDeg = TrackBarDeg.getProgress()+1 + " deg";
                TextDegre.setText(TextDeg);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        FlecheGauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gauche();
            }
        });
        FlecheDroite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Droite();
            }
        });
        FlecheBas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reculer();
            }
        });
        FlecheHaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Avancer();
            }
        });
        BoutonTrigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trigo(TrackBarDeg.getProgress() +1);
            }
        });
        BoutonHorraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Horraire(TrackBarDeg.getProgress() +1);
            }
        });
        BoutonDescendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Descendre(TrackBarHauteur.getProgress() +1);
            }
        });
        BoutonMonter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Monter(TrackBarHauteur.getProgress() +1);
            }
        });
        BoutonAtterrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Atterrir();
            }
        });
        BoutonDecoller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decoller();
            }
        });



    }




    @SuppressLint("ClickableViewAccessibility")




    public native void Commande(String IP);
    public native void Avancer();
    public native void Reculer();
    public native void Droite();
    public native void Gauche();
    public native void Trigo(int deg);
    public native void Horraire(int deg);
    public native void Monter(int Hauteur);
    public native void Descendre(int Hauteur);
    public native void Decoller();
    public native void Atterrir();
    public native void Go(int X, int Y, int Vitesse);
    public native String stringFromJNI();
}

