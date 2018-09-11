package frameworks_and_pages;

import java.util.StringTokenizer;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Volume_Manager_UI {
	private VBox volume_ui;
	private Volume_Control_UI master_volume_ui;
	private Volume_Control_UI music_volume_ui;
	private Volume_Control_UI sound_effects_volume_ui;
	
	private Text volume_title;
	private Font volume_title_font;
	
	private Page page;
	
	public Volume_Manager_UI(Page page){
		this.page = page;
		volume_ui = new VBox(10);
		volume_title = new Text("VOLUME");
		volume_title_font = Font.font("Arial", FontWeight.BOLD, 24 * page.get_y_multiplier());
		
		volume_title.setTextAlignment(TextAlignment.CENTER);
		volume_title.setFont(volume_title_font);
		
		master_volume_ui = new Volume_Control_UI("Master Volume", page.get_settings().get_master_volume(), page);
		music_volume_ui = new Volume_Control_UI("Music Volume", page.get_settings().get_music_volume(), page);
		sound_effects_volume_ui = new Volume_Control_UI("Sound Effects Volume", page.get_settings().get_sound_effects_volume(), page);
		
		volume_ui.getChildren().addAll(volume_title, master_volume_ui.get_set(), music_volume_ui.get_set(), sound_effects_volume_ui.get_set());
		volume_ui.setLayoutX((page.get_settings().MAX_WIDTH/2 - 270) * page.get_x_multiplier());
		volume_ui.setLayoutY(250 * page.get_y_multiplier());
		
		
		page.get_group().getChildren().add(volume_ui);
	}
	
	public void update_settings(){
		page.get_settings().set_volume(Integer.parseInt(master_volume_ui.get_TextField().getText()), Integer.parseInt(music_volume_ui.get_TextField().getText()), Integer.parseInt(sound_effects_volume_ui.get_TextField().getText()));
	}
	
	public void update(){
		volume_title_font = Font.font("Arial", FontWeight.BOLD, 24 * page.get_settings().get_y_multiplier());
		volume_title.setFont(volume_title_font);
		master_volume_ui.update();
		music_volume_ui.update();
		sound_effects_volume_ui.update();
		volume_ui.setLayoutX((page.get_settings().MAX_WIDTH/2 - 270) * page.get_x_multiplier());
		volume_ui.setLayoutY(250 * page.get_y_multiplier());
	}

}
