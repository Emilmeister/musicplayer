package components.MusicList;

import components.Player.PlayerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class MusicListController {

    @Autowired
    private MusicListView  musicListView;
    @Autowired
    private PlayerController playerController;

    private List<String> songsPathList = new ArrayList<>();

    public void setSongsPaths(String path){
        File dir = new File(path); 
        songsPathList.clear();
        try {
            for (String songPath : dir.list()) {
                if(songPath.endsWith(".mp3")) {
                    songsPathList.add(songPath);
                }
            }
            musicListView.setRootPath(dir.getPath()+"\\");
            musicListView.render(songsPathList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getSongsPaths() {
        return songsPathList;
    }

    public void changeSong(String songPath){
        playerController.changeSong(songPath);
    }




}
