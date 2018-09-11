package frameworks_and_pages;

import java.util.StringTokenizer;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/*
 * Setting Page
 * 	- ID: 1
 * 	- Extra Info here...
 */

public class Settings_Page extends Page {
	private Custom_Button back;
	private Custom_Button accept;
	private Custom_Button reset;
	
	private GraphicsContext gc;
	
	Volume_Manager_UI vmUI;
	Resolution_UI resUI;
	Title_Text settings_title;
	
	private boolean pop_up_flag;
	
	private Pop_Up_Window_Reset puwr;
	private Pop_Up_Window_Accept puwa;
	
	public Settings_Page(Settings settings, Flags flags, Page_Manager pm){
		// placeholder
		super(settings, flags, 1, pm);
		init();
	}
	
	private void button_ui(){
		// og: width - 250
		back.set_layout(1670, 960);
		// og: width - 610
		accept.set_layout(1310, 960);
		// og: width - 450
		reset.set_layout(1470, 960);
		get_group().getChildren().addAll(back, accept, reset);
	}
	
	private void event_handlers(){
		button_handlers();
		keyboard_handlers();
		mouse_handlers();
		resolution_list_handlers();
	}
	
	private void resolution_list_handlers(){
		
		resUI.get_res_op_list().setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode().equals(KeyCode.ESCAPE)){
					if(!pop_up_flag){
						back.play_select_sound();
						page_transition(get_page_manager().get_page(0));
					}
					else{
						arg0.consume();
					}
				}
			}
			
		});
		
	}
	
	private void mouse_handlers(){
		get_canvas().setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				get_canvas().requestFocus();
			}
			
		});
	}
	
	private void keyboard_handlers(){
		get_group().setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.TAB)){
					event.consume();
				}
				if(event.getCode().equals(KeyCode.ESCAPE)){
					if(!pop_up_flag){
						back.play_select_sound();
						page_transition(get_page_manager().get_page(0));
					}
					else{
						event.consume();
					}
				}
			}
			
		});
	}
	
	private void button_handlers(){
		
		back.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				// need to add pop up message asking if they are sure if changes were made.
				back.play_select_sound();
				page_transition(get_page_manager().get_page(0));
			}
		});
		
		accept.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				// create pop up window asking if user is sure.
				//Pop_Up_Window.init("Are you sure?", get_group());
				puwa.pop_up_activate();
			}
		});
		
		reset.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				// create pop up window asking if user is sure
				// reset settings to default (i.e. full window, 100 volume, etc.)
				
				//Pop_Up_Window.init("Are you sure?", get_group());
				puwr.pop_up_activate();
			}
		});
		
		
	}
	
	public int[] get_volume_data(){
		int[] volume_set = {};
		return volume_set;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		// pop_up_flag = false;
		back.update();
		accept.update();
		reset.update();
		vmUI.update();
		
		
		get_canvas().resize(get_width(), get_height());
	}

	@Override
	public void init() {
		pop_up_flag = false;
		back = new Custom_Button("BACK", 150, 50, get_settings());
		accept = new Custom_Button("ACCEPT", 150, 50, get_settings());
		reset = new Custom_Button("RESET", 150, 50, get_settings());
		
		settings_title = new Title_Text("Settings", this, (int) (48 * get_y_multiplier()), get_width()/2, 150 * get_y_multiplier());
		
		// add setting ui
		
		// volume setting ui
		vmUI = new Volume_Manager_UI(this);
		
		// resolution setting ui
		resUI = new Resolution_UI(this);
		
		puwr = new Pop_Up_Window_Reset();
		puwa = new Pop_Up_Window_Accept();
		
		// button ui
		button_ui();
		event_handlers();
	}
	
	private class Pop_Up_Window_Accept{
		
		private Rectangle overlay;
		private Rectangle pop_up;
		private Custom_Button yes;
		private Custom_Button no;
		private boolean pop_up_flag;
		Text message;
		
		public Pop_Up_Window_Accept(){
			
			message = new Text("Are you sure you want to CHANGE SETTINGS?");
			overlay = new Rectangle(get_width(), get_height(), Color.BLACK);
			overlay.setOpacity(0.5);
			pop_up = new Rectangle(700, 320, Color.WHITE);
			yes = new Custom_Button("YES", 150, 50, get_settings());
			no = new Custom_Button("NO", 150, 50, get_settings());
			
			pop_up.setLayoutX(610 * get_x_multiplier());
			pop_up.setLayoutY(350 * get_y_multiplier());
			
			yes.set_layout(670, 600);
			no.set_layout(840, 600);
			
			button_handlers();
			
			System.out.println("Processing pop up...");
		}
		
		public void pop_up_activate(){
			get_group().getChildren().addAll(overlay, pop_up, yes, no, message);
		}
		
		private void pop_up_deactivate(){
			get_group().getChildren().removeAll(overlay, pop_up, yes, no, message);
		}
		
		private void button_handlers(){
			yes.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent arg0) {
					yes.play_select_sound();
					
					vmUI.update_settings();
					StringTokenizer st = new StringTokenizer(resUI.get_res_op_list().getSelectionModel().getSelectedItem());
					String resolution = st.nextToken();
					if(st.hasMoreTokens()){
						get_settings().set_full_screen(true);
					}
					else{
						get_settings().set_full_screen(false);
					}
					st = new StringTokenizer(resolution, "x");
					get_settings().set_resolution(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
					pop_up_deactivate();
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
			pop_up.setLayoutX(610 * get_x_multiplier());
			pop_up.setLayoutY(350 * get_y_multiplier());
			
			yes.set_layout(670, 600);
			no.set_layout(840, 600);
			
			yes.update();
			no.update();
		}
	}
	
	private class Pop_Up_Window_Reset{
		
		private Rectangle overlay;
		private Rectangle pop_up;
		private Custom_Button yes;
		private Custom_Button no;
		private boolean pop_up_flag;
		private Text message;
		
		public Pop_Up_Window_Reset(){
			message = new Text("Are you sure you want to RESET SETTINGS?");
			overlay = new Rectangle(get_width(), get_height(), Color.BLACK);
			overlay.setOpacity(0.5);
			message.setLayoutX(600 * get_x_multiplier());
			message.setLayoutY(360 * get_y_multiplier());
			pop_up = new Rectangle(700, 320, Color.WHITE);
			yes = new Custom_Button("YES", 150, 50, get_settings());
			no = new Custom_Button("NO", 150, 50, get_settings());
			
			pop_up.setLayoutX(610 * get_x_multiplier());
			pop_up.setLayoutY(350 * get_y_multiplier());
			
			yes.set_layout(670, 600);
			no.set_layout(840, 600);
			
			button_handlers();
			
			System.out.println("Processing pop up...");
		}
		
		public void pop_up_activate(){
			get_group().getChildren().addAll(overlay, pop_up, yes, no, message);
		}
		
		private void pop_up_deactivate(){
			get_group().getChildren().removeAll(overlay, pop_up, yes, no, message);
		}
		
		private void button_handlers(){
			yes.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent arg0) {
					yes.play_select_sound();
					get_settings().set_default_values();
					pop_up_deactivate();
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
			pop_up.setLayoutX(610 * get_x_multiplier());
			pop_up.setLayoutY(350 * get_y_multiplier());
			
			yes.set_layout(670, 600);
			no.set_layout(840, 600);
			
			yes.update();
			no.update();
		}
	}
}
