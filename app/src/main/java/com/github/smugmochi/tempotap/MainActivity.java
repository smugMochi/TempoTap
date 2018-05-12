package com.github.smugmochi.tempotap;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean firstTap = true;
    int nbTaps = 0;
    long t1;
    long t2;
    double tempo;
    double avgTempo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tempoText = findViewById(R.id.tempo);
        final TextView avgTempoText = findViewById(R.id.avg_tempo);
        final TextView nearestWholeText = findViewById(R.id.nearest_whole);
        final TextView nbTapsText = findViewById(R.id.nb_taps);
        final Button tapButton = findViewById(R.id.button_tap);
        final Button resetButton = findViewById(R.id.button_reset);

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!firstTap){
                    t2 = SystemClock.elapsedRealtime();
                    tempo = 60000/(double)(t2 - t1);
                    avgTempo = (nbTaps-1)*avgTempo/nbTaps + tempo/nbTaps;
                    nbTaps++;
                    tempoText.setText(String.format("%.2f", tempo));
                    avgTempoText.setText(String.format("%.2f", avgTempo));
                    nearestWholeText.setText(String.valueOf(Math.round(avgTempo)));
                    nbTapsText.setText(String.valueOf(nbTaps));
                    t1 = t2;
                }
                else{
                    t1 = SystemClock.elapsedRealtime();
                    nbTaps++;
                    firstTap = false;
                    nbTapsText.setText(String.valueOf(nbTaps));
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempoText.setText("");
                avgTempoText.setText("");
                nearestWholeText.setText("");
                nbTapsText.setText("");
                firstTap = true;
                nbTaps = 0;
                avgTempo = 0;
            }
        });
    }
}
