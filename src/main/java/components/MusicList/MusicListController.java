package components.MusicList;

import components.Player.PlayerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MusicListController {

    @Autowired
    private MusicListView  musicListView;
    @Autowired
    private PlayerController playerController;
    private String rootPath;

    private List<String> songsPathList = new ArrayList<>();

    public void setSongsPaths(String path){
        File dir = new File(path);
        this.rootPath = dir.getAbsolutePath();
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

    public void changeSong(int ind){
        playerController.setPlayingSongsPathsQueue(new LinkedList<>(
                songsPathList.subList(ind, songsPathList.size())
                        .stream()
                        .map(object -> this.rootPath + "\\" + object)
                        .collect(Collectors.toList())));
    }




}
