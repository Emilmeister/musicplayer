package components.MusicList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

import constant.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicListView extends JPanel {

    @Autowired(required = false)
    private MusicListController musicListController;
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
                    musicListController.changeSong(ind);
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
        setLayout(new FlowLayout(FlowLayout.CENTER));

        jList.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        jList.addListSelectionListener(listSelectionListener);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(jList);
        scrollPane.createVerticalScrollBar();
        scrollPane.setVisible(true);
        add(scrollPane);
        setVisible(true);
        scrollPane.setPreferredSize(new Dimension(Size.APP_WIDTH*95/100, Size.APP_HEIGHT*2/3));
    }

    public JList getjList() {
        return jList;
    }
}
