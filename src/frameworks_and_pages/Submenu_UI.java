package frameworks_and_pages;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Submenu_UI {
	private String[] elements; 
	private VBox submenu;
	private Page page;
	private Rectangle overlay;
	private Custom_Button back;
	
	public Submenu_UI(String[] input, Page page, byte chapter){
		elements = input;
		submenu = new VBox();
		this.page = page;
		overlay = new Rectangle(page.get_width(), page.get_height(), Color.WHITE);
		back = new Custom_Button("BACK", 150, 50, page.get_settings());
		byte section = 0;
		for(String element : elements){
			Custom_Button btn = new Custom_Button(element, 150, 50, page.get_settings());
			submenu.getChildren().add(btn);
			submenu_button_handler(btn, section, chapter);
			section++;
		}
		
		// Layout...
		back.set_layout(1670, 960);
		
		button_handlers();
	}
	
	public void display_submenu(){
		page.get_group().getChildren().addAll(overlay, submenu, back);
	}
	
	public void disable_submenu(){
		page.get_group().getChildren().removeAll(overlay, submenu, back);
	}
	
	private void submenu_button_handler(Custom_Button btn, byte section, byte chapter){
		btn.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				btn.play_select_sound();
				page.get_page_manager().add_page(new Game_Page(page.get_settings(), page.get_flags(), chapter, section, page.get_page_manager()));
				page.page_transition(page.get_page_manager().get_page(3));
			}
			
		});
	}
	
	private void button_handlers(){
		back.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				back.play_select_sound();
				disable_submenu();
				
			}
			
		});
	}
}
