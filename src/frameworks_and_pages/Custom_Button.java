package frameworks_and_pages;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Custom_Button extends Button {
	private Audio_Handler button_hover;
	private Audio_Handler button_choose;
	
	private double base_x_pos;
	private double base_y_pos;
	private double base_width;
	private double base_height;
	
	private double default_text_size = 15;
	
	private Settings settings;
	
	
	public Custom_Button(String text, double width, double height, Settings settings){
		super(text);
		this.settings = settings;
		
		base_width = width;
		base_height = height;
		setMinSize(width * settings.get_x_multiplier(), height * settings.get_y_multiplier());
		button_hover();
		button_exit_hover();
		
		button_hover = new Audio_Handler("menu_button_hover.wav", false, 1, settings);
		button_choose = new Audio_Handler("menu_button_select.wav", false, 1, settings);
	}
	
	public void set_layout(double x_pos, double y_pos){
		base_x_pos = x_pos;
		base_y_pos = y_pos;
		setLayoutX(x_pos * settings.get_x_multiplier());
		setLayoutY(y_pos * settings.get_y_multiplier());
	}
	
	public void button_hover(){
		setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// placeholder
				button_hover.play();
				
			}
			
		});
	}
	
	public void button_exit_hover(){
		setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// placeholder
				
			}
			
		});
	}
	
	public void play_select_sound(){
		// placeholder
		button_choose.play();
	}

	public void update() {
		this.setStyle("-fx-font-size: " + default_text_size * settings.get_y_multiplier() + "; ");
		set_layout(base_x_pos, base_y_pos);
		setMinSize(base_width * settings.get_x_multiplier(), base_height * settings.get_y_multiplier());
	}
	
	
}
