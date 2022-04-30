package components.Player;

import constant.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Component
public class PlayerView extends JPanel {
    @Autowired(required = false)
    private PlayerController playerController;
    private JSlider slider = new JSlider(0,100, 100);
    private JToggleButton playPauseButton = new JToggleButton("▶");
    private JLabel songName = new JLabel();
    private JProgressBar rewinder = new JProgressBar();



    void setPlayPauseButtonLabel() {
//        playPauseButton.setText(playerController.isRunning() ? "pause": "play");
    }


    void initSlider(){
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                playerController.setVolume( ((JSlider) e.getSource()).getValue()/100.0f );
            }
        };
        slider.addChangeListener(changeListener);
        slider.setSize(Size.APP_WIDTH/20, Size.APP_HEIGHT/50);
    }


    public PlayerView(){
        initSlider();
        ActionListener playpause = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(playerController != null){
                    playerController.toggle();
                    setPlayPauseButtonLabel();
                }
            }
        };
        playPauseButton.addActionListener(playpause);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(songName);
        add(slider);
        add(rewinder);
        add(playPauseButton);
        setVisible(true);
    }


    public JProgressBar getRewinder() {
        return rewinder;
    }

    public void setRewinder(JProgressBar rewinder) {
        this.rewinder = rewinder;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public JSlider getSlider() {
        return slider;
    }

    public void setSlider(JSlider slider) {
        this.slider = slider;
    }

    public JToggleButton getPlayPauseButton() {
        return playPauseButton;
    }

    public void setPlayPauseButton(JToggleButton playPauseButton) {
        this.playPauseButton = playPauseButton;
    }

    public JLabel getSongName() {
        return songName;
    }

    public void setSongName(JLabel songName) {
        this.songName = songName;
    }
}
