package FilePicker;

import components.MusicList.MusicListController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class FilePickerView extends JFrame {

    FilePickerView me = this;

    @Autowired
    MusicListController musicListController;

    JFileChooser directoryChooser = new JFileChooser();

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                musicListController.setSongsPaths(directoryChooser.getSelectedFile().getPath());
                me.setVisible(false);
            }
        }
    };


    public FilePickerView() {
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.addActionListener(actionListener);

        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        add(directoryChooser);
        setVisible(true);
    }

}
