package components.Player;

import javazoom.jl.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.File;
import static constant.Path.TEMP_PATH;

@Component
public class PlayerController {

    private Converter converter = new Converter();
    private AudioInputStream audioInputStream;
    private Clip clip;
    @Autowired
    private PlayerView playerView;

    private void play(long microSecondPosition) {
        try {
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(microSecondPosition); //устанавливаем указатель
            clip.start(); //Поехали!!!
            this.setProgress();
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
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
            System.out.println("volume " + gainControl.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isRunning() {
        return clip.isRunning();
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


    public void changeSong(String mp3Path) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String chars20(String s) {
        try {
            s = s.split("\\.")[0];
            if(s.length() < 20) return s;
            else return s.substring(0, 20) + "...";
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
