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

@Component
public class PlayerView extends JPanel {
    @Autowired(required = false)
    private PlayerController playerController;
    private JSlider slider = new JSlider(0,100, 100);
    private JButton playPauseButton = new JButton("▶");
    private JLabel songName = new JLabel();
    private JProgressBar rewinder = new JProgressBar();
    private JButton plusPosition = new JButton("Plus");
    private JButton minusPosition = new JButton("Minus");



    void setPlayPauseButtonLabel() {
//        playPauseButton.setText(playerController.isRunning() ? "pause": "play");
    }


    void init(){
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                playerController.setVolume( ((JSlider) e.getSource()).getValue()/100.0f );
            }
        };
        slider.addChangeListener(changeListener);
        slider.setSize(Size.APP_WIDTH/20, Size.APP_HEIGHT/50);
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
        ActionListener plusPositionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerController.plusTrackPosition();
            }
        };
        plusPosition.addActionListener(plusPositionListener);
        ActionListener minusPositionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerController.minusTrackPosition();
            }
        };
        minusPosition.addActionListener(minusPositionListener);

        slider.setPreferredSize(new Dimension(100, 20));
        rewinder.setPreferredSize(new Dimension(350, 20));
    }


    public PlayerView(){
        init();

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(songName);
        add(slider);
        add(rewinder);
        add(playPauseButton);
        add(minusPosition);
        add(plusPosition);
        setVisible(true);
    }


    public JProgressBar getRewinder() {
        return rewinder;
    }

    public JLabel getSongName() {
        return songName;
    }

    public JSlider getSlider() {
        return slider;
    }
}
