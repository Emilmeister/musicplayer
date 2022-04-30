import FilePicker.FilePickerView;
import components.MusicList.MusicListView;
import components.Player.PlayerView;
import components.Window;
import config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    }
}
