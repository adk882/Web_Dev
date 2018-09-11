package frameworks_and_pages;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.stage.*;

public class Main_Framework extends Application {
	
	public static void main(String args[]) throws IOException{
		launch(args);
	}

	@Override
	public void start(Stage main_frame) throws Exception {
		main_frame.setTitle("Web Dev Basics");
		main_frame.setResizable(false);
		main_frame.setFullScreenExitHint("");
		main_frame.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		
		Flags flags = new Flags();
		
		Settings settings = new Settings(flags.first_run());
		
		Page_Manager pm = new Page_Manager(main_frame);
		
		if(settings.get_full_screen()){
			main_frame.initStyle(StageStyle.UNDECORATED);
			main_frame.setMaximized(true);
		}
		else{
			main_frame.initStyle(StageStyle.DECORATED);
			main_frame.setMaximized(false);
			main_frame.setMaxHeight(settings.get_height());
			main_frame.setMaxWidth(settings.get_width());
		}
		
		Audio_Handler mm_song = new Audio_Handler("Patakas World.wav", true, 0, settings);
		
		
		Main_Menu mm_page = new Main_Menu(settings, flags, pm);
		Settings_Page set_page = new Settings_Page(settings, flags, pm);
		Game_Mode_Menu gmm_page = new Game_Mode_Menu(settings, flags, pm);
		
		pm.set_active_scene(mm_page);
		
		
		main_frame.show();
		System.out.println("Stage Showing");
		mm_song.play();
	}
	
}
