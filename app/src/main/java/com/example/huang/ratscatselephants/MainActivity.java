package com.example.huang.ratscatselephants;


/**
 * Authors:
 * Project 1 Cats Rats and Elephants
 * CS 639 Mobile Android Development
 * wh57852n@pace.edu
 * qc03674n@pace.edu
 * Wei Huang and QiYi Chen
 */

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    static final String TAG = "Rats-Cats-Elephants";
    private int won = 0;
    private int lose = 0;
    private int tight = 0;
    private double status = 0;

    SeekBar statusSeekBar;
    ImageButton rockImageButton;
    ImageButton paperImageButton;
    ImageButton scissorImageButton;
    ImageView imageView1;
    ImageView imageView2;
    TextView result_tv;
    TextView count_tv;
    Button aboutButton;
    Button exitButton;
    RelativeLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = (ImageView) findViewById(R.id.computer_imageview);
        imageView2 = (ImageView) findViewById(R.id.human_imageview);
        result_tv = (TextView) findViewById(R.id.result);
        count_tv = (TextView) findViewById(R.id.count);
        gameLayout = (RelativeLayout) findViewById(R.id.gameLayout);
        gameLayout.setBackgroundResource(R.drawable.image1);
        aboutButton = (Button) findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        rockImageButton = (ImageButton) findViewById(R.id.cat_imagebutton);
        rockImageButton.setOnClickListener(this);
        paperImageButton = (ImageButton) findViewById(R.id.rat_imagebutton);
        paperImageButton.setOnClickListener(this);
        scissorImageButton = (ImageButton) findViewById(R.id.elephant_imagebutton);
        scissorImageButton.setOnClickListener(this);
        statusSeekBar = (SeekBar) findViewById(R.id.status_seekBar);
        exitButton = (Button) findViewById(R.id.exit_button);

    }

    @Override
    protected void onResume(){
        super.onResume();
        Music.play(this,R.raw.main);
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
        Music.stop(this);
    }

    @Override
    public void onClick(View v) {

        // show Pct. status report from calculation won/(total)%
        final String countText =
                "W: "+won+
                        " | L: "+lose+
                        " | T: "+tight+
                        " | "+String.format("Pct. %.2f", status)+"%";

        // Use Intent and show about class, and modify the alert dialog in android Manifest
        switch(v.getId()){
            case R.id.about_button:
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
                break;

            case R.id.exit_button:
                finish();
                break;

            case R.id.rat_imagebutton:
                imageView2.setImageResource(R.drawable.rat);
                getRandomPic();
                getResult1();
                count_tv.setText(countText);
                changeBackgroundColor();
                break;

            case R.id.cat_imagebutton:
                imageView2.setImageResource(R.drawable.cat);
                getRandomPic();
                getResult2();
                count_tv.setText(countText);
                changeBackgroundColor();
                break;

            case R.id.elephant_imagebutton:
                imageView2.setImageResource(R.drawable.elephant);
                getRandomPic();
                getResult3();
                count_tv.setText(countText);
                changeBackgroundColor();
                break;

        }
    }

    // Put image into Array
    static final int[] photos = {R.drawable.rat,R.drawable.cat,R.drawable.elephant};

    public void getRandomPic(){

        // Random Image generated
        Random ran = new Random(System.currentTimeMillis());
        int i = ran.nextInt(photos.length);
        Log.d(TAG, "Randomly Pick Up Picture No. " + i);
        // Set image for image Resource - Create Image in View
        // Set image for image Tag - Create image_id for each picture
        imageView1.setImageResource(photos[i]);
        imageView1.setTag(photos[i]);

    }

    // Setting Item
    // Intent in Prefs.Class

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, Prefs.class));
                return true;
        }
        return false;
    }

    static final String wText = "Hey I Won, Play It Again";
    static final String lText = "Loser Loser, Try It Again";
    static final String tText = "Hmm Tight, Do It Again";

    // Use Three Different Result for Different ImageButton
    // Get Count for Won and Lose and Tight

    public void getResult1(){
        if ((Integer)imageView1.getTag() == R.drawable.elephant){
            result_tv.setText(wText);
            won++;
        } else if ((Integer)imageView1.getTag() == R.drawable.cat){
            result_tv.setText(lText);
            lose++;
        } else {
            result_tv.setText(tText);
            tight++;
        }
        getStatus();
        getStatusSeekBar();
    }

    public void getResult2(){
        if ((Integer)imageView1.getTag() == R.drawable.rat){
            result_tv.setText(wText);
            won++;
        } else if ((Integer)imageView1.getTag() == R.drawable.elephant){
            result_tv.setText(lText);
            lose++;
        } else {
            result_tv.setText(tText);
            tight++;
        }
        getStatus();
        getStatusSeekBar();
    }

    public void getResult3(){
        if ((Integer)imageView1.getTag() == R.drawable.cat){
            result_tv.setText(wText);
            won++;
        } else if ((Integer)imageView1.getTag() == R.drawable.rat){
            result_tv.setText(lText);
            lose++;
        } else {
            result_tv.setText(tText);
            tight++;
        }
        getStatus();
        getStatusSeekBar();
    }

    // get percentage from won / won lose tight

    public void getStatus(){

        status = ((double)won/(double)(won+lose+tight))*100;

    }

    // Show Percentage in Status Bar

    public void getStatusSeekBar(){
        statusSeekBar.setMax(100);
        statusSeekBar.setVisibility(View.VISIBLE);
        statusSeekBar.setProgress((int) status);
    }

    // Change Background Color by Percentage

    public void changeBackgroundColor(){
        if(status < 10){
            gameLayout.setBackgroundColor(Color.BLACK);
        } else if (status > 10 && status < 30){
            gameLayout.setBackgroundResource(R.drawable.image3);
        } else if (status > 30 && status < 60){
            gameLayout.setBackgroundResource(R.drawable.image2);
        } else if (status > 70 && status < 90){
            gameLayout.setBackgroundResource(R.drawable.image1);
        }
    }
}
