package com.it.vietnv.ngovanviet_1412101031;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.it.vietnv.ngovanviet_1412101031.adapters.CustomListAdapter;
import com.it.vietnv.ngovanviet_1412101031.interfaces.IAsyncListener;
import com.it.vietnv.ngovanviet_1412101031.models.Song;
import com.it.vietnv.ngovanviet_1412101031.tasks.SongTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvSongs;
    CustomListAdapter adapter;
    ArrayList<Song> arrSong;

    TextView tvRunTime, tvTotalTime, tvSongAndSinger;
    SeekBar seekBar;
    ImageView imgRewind, imgForward, imgPlay;

    MediaPlayer mediaPlayer;
    int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();

        mediaPlayer = new MediaPlayer();


        new SongTask(new IAsyncListener() {
            @Override
            public void doStuff(ArrayList<Song> objects) {
                arrSong = objects;
                adapter = new CustomListAdapter(MainActivity.this, objects);

                lvSongs.setAdapter(adapter);

                addEvents();
            }
        }).execute("https://ct1801hpu.000webhostapp.com/music-app/index.php?format=xml");
    }

    private void addEvents() {
        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                loadMusic(position);
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                } else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                }
            }
        });

        imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition++;
                if (currentPosition == arrSong.size()) {
                    currentPosition = 0;
                }

                loadMusic(currentPosition);
            }
        });

        imgRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition--;
                if (currentPosition < 0) {
                    currentPosition = arrSong.size() - 1;
                }

                loadMusic(currentPosition);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void loadMusic(int position) {
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(arrSong.get(position).getLink()));
        mediaPlayer.start();

        tvSongAndSinger.setText(arrSong.get(position).getSongName().trim() + " - " + arrSong.get(position).getSingerName());
        setRunTime();
        setTotalTime();
        imgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
    }

    private void setRunTime() {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                tvRunTime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        currentPosition++;
                        if (currentPosition == arrSong.size()) {
                            currentPosition = 0;
                        }

                        loadMusic(currentPosition);
                    }
                });

                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void setTotalTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        int duration = mediaPlayer.getDuration();
        tvTotalTime.setText(simpleDateFormat.format(duration));
        seekBar.setMax(duration);
    }

    private void addControls() {
        lvSongs = findViewById(R.id.lvSongs);
        tvSongAndSinger = findViewById(R.id.tvSongAndSinger);
        tvSongAndSinger.setSelected(true);

        tvRunTime = findViewById(R.id.tvRunTime);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvSongAndSinger = findViewById(R.id.tvSongAndSinger);
        seekBar = findViewById(R.id.seekBar);
        imgRewind = findViewById(R.id.imgRewind);
        imgForward = findViewById(R.id.imgForward);
        imgPlay = findViewById(R.id.imgPlay);
    }
}
