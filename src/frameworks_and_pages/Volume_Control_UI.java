package frameworks_and_pages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Volume_Control_UI {
	private Slider volume_slider;
	private TextField volume_input;
	private Text name;
	private HBox volume_set;
	private Page page;
	
	public Volume_Control_UI(String name, int default_volume, Page page){
		this.page = page;
		this.name = new Text(name);
		volume_slider = new Slider();
		volume_input = new TextField();
		
		
		volume_set = new HBox(10);
		
		
		volume_slider = new Slider(0, 100, default_volume);
		volume_slider.setMinSize(500 * page.get_x_multiplier(), 20 * page.get_y_multiplier());
		volume_input = new TextField("" + default_volume);
		volume_input.setMaxSize(50 * page.get_x_multiplier(), 20 * page.get_y_multiplier());
		volume_set.getChildren().addAll(this.name, volume_slider, volume_input);
		
		volume_handlers();
		
	}
	
	public HBox get_set(){
		return volume_set;
	}
	
	public TextField get_TextField(){
		return volume_input;
	}
	
	private void volume_handlers(){
		volume_input.focusedProperty().addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
				if((boolean)arg1 && !(boolean)arg2){
					if(volume_input.getText().equals("")){
						volume_input.setText("0");
						volume_slider.setValue(0);
					}
					
				}
				
			}
			
		});
		
		volume_slider.valueProperty().addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
				if(volume_slider.isFocused()){
					volume_input.setText("" + (int)volume_slider.getValue());
				}
			}
		});
		
		volume_input.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode().equals(KeyCode.ENTER)){
					page.get_canvas().requestFocus();
				}
				
				if(arg0.getCode().equals(KeyCode.TAB)){
					arg0.consume();
				}
				
			}
			
		});
		
		volume_input.setOnKeyTyped(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent arg0) {
				if(!arg0.getCode().equals(KeyCode.ESCAPE)){
					if(!Character.isDigit(arg0.getCharacter().charAt(0))){
						arg0.consume();
					}
					else if(volume_input.getText().length() >= 3){
						if(volume_input.getSelectedText().length() == 0){
							arg0.consume();
							volume_input.setText("100");
							volume_input.positionCaret(3);
							volume_slider.setValue(100);
						}
						else if(volume_input.getSelectedText().length() == 1){
							arg0.consume();
							volume_input.setText("100");
							volume_input.positionCaret(3);
							volume_slider.setValue(100);
						}
						else{
							// let it go through
							volume_slider.setValue(Integer.parseInt(volume_input.getText()));
						}
					}
					else if(volume_input.getText().length() == 2 && volume_input.getSelectedText().length() == 0){
						arg0.consume();
						volume_input.setText("100");
						volume_input.positionCaret(3);
						volume_slider.setValue(100);
					}
					else{
						
					}
				}
			}
			
		});
		
		volume_input.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(!volume_input.getText().equals("")){
					volume_slider.setValue(Integer.parseInt(volume_input.getText()));
				}
			}
			
		});
		
	}
	
	public void update(){
		volume_slider.setMinSize(500 * page.get_x_multiplier(), 20 * page.get_y_multiplier());
		volume_input.setMaxSize(50 * page.get_x_multiplier(), 20 * page.get_y_multiplier());
	}
	
}
