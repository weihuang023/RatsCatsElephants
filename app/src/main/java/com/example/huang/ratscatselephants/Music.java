package com.example.huang.ratscatselephants;


import android.content.Context;
import android.media.MediaPlayer;

public class Music {
    private static MediaPlayer mp = null;

    /** Stop the old song and start the new one */
    public static void play(Context context, int resource){
        stop(context);

        if (Prefs.getMusic(context)) {
            mp = MediaPlayer.create(context, resource);
            mp.setLooping(true);
            mp.start();
        }
    }

    /** Stop the music completely */
    public static void stop(Context context){
        if(mp != null) {
            mp.stop();
            mp.release();
            mp=null;
        }
    }
}
