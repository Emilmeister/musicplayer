package config;

import FilePicker.FilePickerView;
import components.Window;
import components.MusicList.MusicListController;
import components.MusicList.MusicListView;
import components.Player.PlayerController;
import components.Player.PlayerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Neil Alishev
 */
@Configuration
public class SpringConfig {
    @Bean
    public MusicListController MusicListController() {
        return new MusicListController();
    }

    @Bean
    public FilePickerView FilePickerView() {
        return new FilePickerView();
    }

    @Bean
    public MusicListView MusicListView() {
        return new MusicListView();
    }

    @Bean
    public PlayerController PlayerController() {
        return new PlayerController();
    }

    @Bean
    public PlayerView PlayerView() {
        return new PlayerView();
    }

    @Bean
    public Window Window() {
        return new Window(PlayerView(), MusicListView());
    }

}