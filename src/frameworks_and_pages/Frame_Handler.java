package frameworks_and_pages;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Frame_Handler {
	private Stage main_frame;
	private Scene current_scene;
	private boolean current_full_screen;
	private Settings settings;
	// placeholder
	
	public Frame_Handler(Stage mf, Settings settings){
		main_frame = mf;
		this.settings = settings;
	}
	
	public Stage get_main_frame(){
		return main_frame;
	}
	
	public void scene_update(Scene next_scene){
		current_scene = next_scene;
		main_frame.setScene(next_scene);
	}
	
	public void stage_update(){
		Stage new_frame = new Stage();
		new_frame.setScene(current_scene);
		if(settings.get_full_screen() && !current_full_screen){
			new_frame.initStyle(StageStyle.UNDECORATED);
			new_frame.setMaximized(true);
			current_full_screen = true;
		}
		else{
			new_frame.initStyle(StageStyle.DECORATED);
			new_frame.setMaximized(false);
			new_frame.setMaxHeight(settings.get_height());
			new_frame.setMaxWidth(settings.get_width());
			current_full_screen = false;
		}
		new_frame.show();
		main_frame.close();
		main_frame = new_frame;
	}

}
