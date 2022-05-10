package components;

import components.FilePicker.FilePickerView;
import components.MusicList.MusicListView;
import components.Player.PlayerView;

import javax.swing.*;
import java.awt.*;

import constant.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Window extends JFrame {

    @Autowired(required = false)
    FilePickerView filePickerView;

    JPanel panel = new JPanel();
    JPanel pageEndPanel = new JPanel();

    JButton newChangeDirectoryButton(){
        JButton button = new JButton("Изменить директорию");
        button.addActionListener(e -> filePickerView.setVisible(true));
        return button;
    }



    public Window(@Autowired PlayerView playerView, @Autowired MusicListView musicListView){
        setSize(Size.APP_WIDTH, Size.APP_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mp3 Player");
        add(panel);
        panel.setLayout(new BorderLayout());

        pageEndPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pageEndPanel.add(playerView);
        pageEndPanel.add(newChangeDirectoryButton());


        panel.add(pageEndPanel, BorderLayout.PAGE_END);
        panel.add(musicListView, BorderLayout.CENTER);




        panel.setVisible(true);
        setVisible(true);
    }
}


