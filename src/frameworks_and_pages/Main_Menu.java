package frameworks_and_pages;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/*
 * Main Menu Page
 * 	- ID: 0
 * 	- Extra Info here...
 */

public class Main_Menu extends Page {
	private Custom_Button start_btn;
	private Custom_Button settings_btn;
	private Custom_Button exit_btn;
	private VBox menu_button_container;
	private GraphicsContext gc;
	private Pop_Up_Window puw;
	private Title_Text mm_title;
	
	public Main_Menu(Settings settings, Flags flags, Page_Manager pm){
		super(settings, flags, 0, pm);
		init();
	}
	
	@Override
	public void update(){
		super.update_page();
		gc.clearRect(0, 0, super.get_width(), super.get_height());
		
		start_btn.update();
		settings_btn.update();
		exit_btn.update();
		menu_button_container.setSpacing(15 * super.get_y_multiplier());
		menu_button_container.setLayoutX(885 * super.get_x_multiplier());
		menu_button_container.setLayoutY(450 * super.get_y_multiplier());
		
		mm_title = new Title_Text("Web Dev Basics", this, (int) (48 * get_y_multiplier()), super.get_width()/2, 350 * super.get_y_multiplier());
		
		puw.update();
		
	}
	
	// method preparing all the button handlers
	private void event_handlers(){
		start_btn_handlers();
		settings_btn_handlers();
		exit_btn_handlers();
		tab_eater();
	}
	
	// Makes sure TAB does not have any effect on the application.
	private void tab_eater(){
		super.get_group().setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.TAB)){
					System.out.println("TAB was consumed");
					event.consume();
				}
			}
			
		});
	}
	
	private void start_btn_handlers(){
		start_btn.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				start_btn.button_hover();
			}
		});
		
		start_btn.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				start_btn.button_exit_hover();
			}
			
		});
		
		start_btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				start_btn.play_select_sound();
				page_transition(get_page_manager().get_page(2));
			}
		});
	}
	
	private void settings_btn_handlers(){
		
		settings_btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				settings_btn.play_select_sound();
				page_transition(get_page_manager().get_page(1));
			}
			
		});
	}
	
	private void exit_btn_handlers(){
		
		exit_btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				//Stage stage = (Stage) exit.getScene().getWindow();
			    //stage.close();
				get_settings().save();
				get_flags().save();
			    exit_btn.play_select_sound();
				puw.pop_up_activate();
			    
				//System.exit(0);
			}
			
		});
	}
	
	private void setup_menu_buttons(){
		start_btn = new Custom_Button("START", 150, 50, get_settings());
		settings_btn = new Custom_Button("SETTINGS", 150, 50, get_settings());
		exit_btn = new Custom_Button("EXIT", 150, 50, get_settings());
		
		menu_button_container = new VBox(15 * super.get_y_multiplier());
		menu_button_container.getChildren().addAll(start_btn, settings_btn, exit_btn);
		get_group().getChildren().addAll(menu_button_container);
		
		menu_button_container.setAlignment(Pos.CENTER);
		menu_button_container.setLayoutX(885 * get_x_multiplier());
		menu_button_container.setLayoutY(450 * get_x_multiplier());
	}

	@Override
	public void init() {
		System.out.println("Main Menu Initialization started...");
		
		gc = get_canvas().getGraphicsContext2D();
		
		gc.setFill(Color.RED);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		Font main_menu_title_font = Font.font("Arial", FontWeight.BOLD, 48 * super.get_y_multiplier());
		gc.setFont(main_menu_title_font);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText("Web Dev Basics", get_width() / 2, 350 * get_y_multiplier());
		gc.strokeText("Web Dev Basics", get_width() / 2, 350 * get_y_multiplier());
		
		System.out.println("Graphics Context Initialized");
		
		puw = new Pop_Up_Window(this);
		
		setup_menu_buttons();
		event_handlers();
		
	}
	
	private class Pop_Up_Window{
		
		private Rectangle overlay;
		private Rectangle pop_up;
		private Custom_Button yes;
		private Custom_Button no;
		private boolean pop_up_flag;
		private Page page;
		
		public Pop_Up_Window(Page page){
			this.page = page;
			
			Text message = new Text("Are you sure you want to exit?");
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
