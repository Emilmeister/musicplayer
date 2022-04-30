package components.MusicList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.logging.Logger;

import components.Player.PlayerController;
import constant.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicListView extends JPanel {

    @Autowired(required = false)
    private PlayerController playerController;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JList jList =  new JList(model);
    private String rootPath = "";

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    ListSelectionListener listSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            try{
                if (!e.getValueIsAdjusting()) {
                    int ind = ((JList) e.getSource()).getSelectedIndex();
                    String songPath = rootPath + model.get(ind);
                    playerController.changeSong(songPath);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    };

    void render(List<String> songNamesList){
        model.clear();
        int ind = 0;
        for (String name: songNamesList) {
            model.addElement(name);
        }
    }

    public MusicListView() {
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        jList.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        jList.addListSelectionListener(listSelectionListener);
        jList.setSize(Size.APP_WIDTH, Size.APP_HEIGHT*2/3);

        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(jList);
        scrollPane.createVerticalScrollBar();
        scrollPane.setVisible(true);
        scrollPane.setSize(Size.APP_WIDTH, Size.APP_HEIGHT*2/3);

        add(scrollPane);
        setVisible(true);
    }



}