package frameworks_and_pages;

import html_css_js.CSS_Chapters_List;
import html_css_js.HTML_Chapters_List;
import html_css_js.JavaScript_Chapters_List;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game_Mode_Menu extends Page {
	private Custom_Button HTML;
	private Custom_Button CSS;
	private Custom_Button JavaScript;
	private Custom_Button back;
	private VBox gmm_button_container;
	
	private Submenu_UI submenu_HTML;
	private Submenu_UI submenu_CSS;
	private Submenu_UI submenu_JavaScript;
	
	private boolean submenu_on;
	
	
	public Game_Mode_Menu(Settings settings, Flags flags, Page_Manager pm){
		super(settings, flags, 2, pm);
		init();
	}
	
	private void event_handlers(){
		HTML_handlers();
		CSS_handlers();
		JavaScript_handlers();
		back_handlers();
		keyboard_handler();
	}
	
	private void keyboard_handler(){
		get_group().setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.TAB)){
					System.out.println("TAB was consumed");
					event.consume();
				}
			}
			
		});
	}
	
	private void HTML_handlers(){
		HTML.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				submenu_on = true;
				HTML.play_select_sound();
				submenu_HTML.display_submenu();
			}
			
		});
	}
	
	private void CSS_handlers(){
		CSS.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				submenu_on = true;
				CSS.play_select_sound();
				submenu_CSS.display_submenu();
			}
			
		});
	}
	
	private void JavaScript_handlers(){
		JavaScript.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				submenu_on = true;
				JavaScript.play_select_sound();
				submenu_JavaScript.display_submenu();
			}
			
		});
	}
	
	private void back_handlers(){
		
		back.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				back.play_select_sound();
				page_transition(get_page_manager().get_page(0));
			}
		});
		
		get_scene().setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().toString().equals("ESCAPE")){
					if(submenu_on){
						back.play_select_sound();
						page_transition(get_page_manager().get_page(0));
					}
					else{
						
					}
				}
			}
			
		});
	}
	
	private void setup_menu_buttons(){
		HTML = new Custom_Button("HTML", 150, 50, get_settings());
		CSS = new Custom_Button("CSS", 150, 50, get_settings());
		JavaScript = new Custom_Button("JavaScript", 150, 50, get_settings());
		
		gmm_button_container = new VBox(25);
		gmm_button_container.getChildren().addAll(HTML, CSS, JavaScript);
		get_group().getChildren().add(gmm_button_container);
		
		gmm_button_container.setAlignment(Pos.CENTER);
		gmm_button_container.setLayoutX((get_settings().MAX_WIDTH / 2 - 75) * get_settings().get_x_multiplier());
		gmm_button_container.setLayoutY(450 * get_settings().get_y_multiplier());
	}
	
	public void submenu_off(){
		submenu_on = false;
	}
	
	public void init(){
		back = new Custom_Button("BACK", 150, 50, get_settings());
		
		Canvas canvas = new Canvas(get_width(), get_height());
		//System.out.println(h);
		
		setup_menu_buttons();
		
		back.set_layout(1670, 960);
		get_group().getChildren().add(back);
		
		event_handlers();
		
		submenu_HTML = new Submenu_UI(HTML_Chapters_List.get_HTML_Chapters(), this, (byte) 0);
		submenu_CSS = new Submenu_UI(CSS_Chapters_List.get_CSS_Chapters(), this, (byte) 1);
		submenu_JavaScript = new Submenu_UI(JavaScript_Chapters_List.get_JavaScript_Chapters(), this, (byte) 2);
		
		submenu_on = false;
	}

	@Override
	public void update() {
		back.update();
		HTML.update();
		CSS.update();
		JavaScript.update();
		
		gmm_button_container.setLayoutX((get_settings().MAX_WIDTH/2 - 75) * get_x_multiplier());
		gmm_button_container.setLayoutY(450 * get_y_multiplier());
	}
}
