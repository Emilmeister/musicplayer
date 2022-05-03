package components.Player;

import components.MusicList.MusicListView;
import javazoom.jl.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import static constant.Path.TEMP_PATH;

@Component
public class PlayerController {

    private Converter converter = new Converter();
    private AudioInputStream audioInputStream;
    private Clip clip;
    private final long CHANGE_TRACK_POSITION_VALUE = 20000000;
    @Autowired
    private PlayerView playerView;
    @Autowired
    private MusicListView musicListView;
    private Queue<String> playingSongsPathsQueue = new LinkedList();
    private LineListener actualLineListener;

    public void setPlayingSongsPathsQueue(Queue<String> queue){
        this.playingSongsPathsQueue = queue;
        this.changeSong();
    }

    private void play(long microSecondPosition) {
        try {
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(microSecondPosition); //устанавливаем указатель
            clip.start(); //Поехали!!!
            this.setProgress();
            if(actualLineListener != null) clip.removeLineListener(actualLineListener);
            LineListener listener = new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getFramePosition() == clip.getFrameLength()) {
                        changeSong();
                        actualLineListener = null;
                    }
                }
            };
            clip.addLineListener(listener);
            actualLineListener = listener;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setProgress(){
        final  PlayerView playerView = this.playerView;
        Thread progressSetter =  new Thread(){
            @Override
            public void run() {
                playerView.getRewinder().setMinimum(0);
                playerView.getRewinder().setMaximum((int) clip.getMicrosecondLength());
                int duration = 1000;
                long times = clip.getMicrosecondLength()/duration;
                for(long i = 0; i < times; i++) {
                    try {
                        sleep(duration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playerView.getRewinder().setValue((int) clip.getMicrosecondPosition());
                }
            }
        };
        progressSetter.start();
    }

    public void setVolume(float volume) {
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = Math.min(gainControl.getMaximum(), 3.0f) - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isRunning() {
        return clip.isRunning();
    }

    public void plusTrackPosition() {
        long MAX = clip.getMicrosecondLength() - 1;
        clip.setMicrosecondPosition(Math.min(MAX, clip.getMicrosecondPosition()+CHANGE_TRACK_POSITION_VALUE));
    }

    public void minusTrackPosition() {
        long MIN = 0;
        clip.setMicrosecondPosition(Math.max(MIN, clip.getMicrosecondPosition()-CHANGE_TRACK_POSITION_VALUE));
    }

    public void toggle(){
        if(clip.isRunning()){
            clip.stop();
        } else {
            clip.start();
        }
    }

//    public long getMicrosecondLength(){
//        return clip.getMicrosecondLength();
//    }


    private String newWavName(String mp3Path){
        File file = new File(mp3Path);
        return TEMP_PATH+"\\"+file.getName()+".wav";
    }


    public void changeSong() {
        if(playingSongsPathsQueue.size() > 0){
            try {
                String mp3Path = playingSongsPathsQueue.remove();
                if(this.clip != null) {
                    this.clip.stop();
                    this.clip.close();
                }

                String wavPath = this.newWavName(mp3Path);
                this.converter.convert(mp3Path, wavPath);
                File wavfile = new File(wavPath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavfile);
                this.audioInputStream = audioInputStream;
                this.play(0);
                this.playerView.setPlayPauseButtonLabel();
                this.playerView.getSongName().setText(chars20(new File(mp3Path).getName()));
                this.musicListView.getjList().setSelectedValue(mp3Path.substring(mp3Path.lastIndexOf('\\') + 1), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String chars20(String s) {
        try {
            s = s.substring(0,s.lastIndexOf('.'));
            if(s.length() < 30) return s;
            else return s.substring(0, 30) + "...";
        } catch (Exception e) {
            return "none";
        }

    }

    public PlayerController() {
        try {
            this.clip = AudioSystem.getClip();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
