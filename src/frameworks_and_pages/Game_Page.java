package frameworks_and_pages;

import html_css_js.Game_Handler;
import html_css_js.Introduction;
import html_css_js.Main_Game;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game_Page extends Page {
	
	private byte section;		// 0 : HTML | 1 : CSS | 2 : JavaScript
	private byte chapter;
	
	private Game_Handler chap_sect;
	
	private Custom_Button next;
	
	public Game_Page(Settings settings, Flags flags, byte chapter, byte section, Page_Manager pm){
		super(settings, flags, 3, pm);
		this.section = section;
		this.chapter = chapter;
		init();
		
	}
	
	//  Empties the page once the current game is done. Should be called after
	// every run.
	private void cleanup(){
		get_page_manager().remove_page(get_id());
	}
	
	private void intro_init(Introduction input){
		System.out.println("Initializing game introduction...");
		Title_Text intro_title = new Title_Text(input.get_intro_title(), this, (int) (48 * get_y_multiplier()), get_width()/2, 150 * get_y_multiplier());
		Text intro_text = new Text(input.get_intro_text());
		Font intro_text_font = Font.font("Arial", FontWeight.NORMAL, 20 * get_y_multiplier());
		intro_text.setFont(intro_text_font);
		intro_text.setLayoutX(500 * get_x_multiplier());
		intro_text.setLayoutY(250 * get_y_multiplier());
		intro_text.setWrappingWidth(500);
		get_group().getChildren().addAll(intro_text);
	}
	
	private void game_init(Main_Game input){
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		switch(section){
			case 0:
				chap_sect = new Introduction(section);
				Introduction intro = (Introduction)chap_sect;
				intro_init(intro);
				break;
			default:
				break;
		}
	}
	
	private class Pause_Window{
		
		private Rectangle overlay;
		private Rectangle pop_up;
		private Custom_Button yes;
		private Custom_Button no;
		private boolean pop_up_flag;
		private Page page;
		
		public Pause_Window(Page page){
			this.page = page;
			
			Text message = new Text("PAUSED");
			overlay = new Rectangle(page.get_width(), page.get_height(), Color.BLACK);
			overlay.setOpacity(0.5);
			pop_up = new Rectangle(700, 320, Color.WHITE);
			yes = new Custom_Button("YES", 150, 50, page.get_settings());
			no = new Custom_Button("NO", 150, 50, page.get_settings());
			
			pop_up.setLayoutX(610 * page.get_x_multiplier());
			pop_up.setLayoutY(350 * page.get_y_multiplier());
			
			yes.set_layout(670, 600);
			no.set_layout(840, 600);
			
			button_handlers();
			
			System.out.println("Processing pop up...");
		}
		
		public void pop_up_activate(){
			page.get_group().getChildren().addAll(overlay, pop_up, yes, no);
		}
		
		private void pop_up_deactivate(){
			page.get_group().getChildren().removeAll(overlay, pop_up, yes, no);
		}
		
		private void button_handlers(){
			yes.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent arg0) {
					// yes.play_select_sound();
					Platform.exit();
				}
				
			});
			
			no.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					no.play_select_sound();
					pop_up_deactivate();
				}
				
			});
		}
		
		public void update(){
			pop_up.setLayoutX(610 * page.get_x_multiplier());
			pop_up.setLayoutY(350 * page.get_y_multiplier());
			
			yes.set_layout(670, 600);
			no.set_layout(840, 600);
			
			yes.update();
			no.update();
		}
	}
}
