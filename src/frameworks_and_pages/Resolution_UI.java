package frameworks_and_pages;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Resolution_UI {
	private RadioButton option_1;
	private RadioButton option_2;
	private RadioButton option_3;
	private RadioButton option_4;
	
	private ArrayList<ObservableList<String>> container;
	private ListView<String> res_op_list;
	private int current_aspect_selection;
	
	private Page page;
	
	public Resolution_UI(Page page){
		this.page = page;
		VBox resolution_ui = new VBox(10);
		Text resolution_title = new Text("RESOLUTION");
		Font resolution_title_font = Font.font("Arial", FontWeight.BOLD, 24);
		HBox aspect_ratio_set = new HBox(10);
		
		// Aspect Ratio Settings
		String current_aspect_ratio = page.get_settings().get_current_aspect_ratio();
		
		option_1 = new RadioButton("4:3");
		option_2 = new RadioButton("16:9");
		option_3 = new RadioButton("8:5");
		option_4 = new RadioButton("other");
		
		aspect_ratio_set.getChildren().addAll(option_1, option_3, option_2, option_4);
		container = page.get_settings().get_resolution_lists();
		res_op_list = new ListView<String>();
		res_op_list.setMaxSize(200, 200);
		
		if(current_aspect_ratio.equals("4:3")){
			current_aspect_selection = 1;
			option_1.setSelected(true);
			option_2.setSelected(false);
			option_3.setSelected(false);
			option_4.setSelected(false);
			res_op_list.setItems(container.get(0));
			int x = page.get_settings().get_width();
			int y = page.get_settings().get_height();
			
			String current_resolution = "" + x + "x" + y;
			int container_size = container.get(0).size() - 1;
			
			for(int cnt = 0; cnt < container_size; cnt++){
				if(container.get(0).get(cnt).equals(current_resolution)){
					res_op_list.getSelectionModel().select(cnt);
				}
			}
			
			if(container.get(0).get(container_size).contains(current_resolution)){
				res_op_list.getSelectionModel().select(container_size);
			}
		}
		else if(current_aspect_ratio.equals("16:9")){
			current_aspect_selection = 2;
			option_1.setSelected(false);
			option_2.setSelected(true);
			option_3.setSelected(false);
			option_4.setSelected(false);
			res_op_list.setItems(container.get(1));
			int x = (int) page.get_width();
			int y = (int) page.get_height();
			
			String current_resolution = "" + x + "x" + y;
			int container_size = container.get(1).size() - 1;
			
			for(int cnt = 0; cnt < container_size; cnt++){
				if(container.get(1).get(cnt).equals(current_resolution)){
					res_op_list.getSelectionModel().select(cnt);
				}
			}
			
			
			if(container.get(1).get(container_size).contains(current_resolution)){
				res_op_list.getSelectionModel().select(container_size);
			}
		}
		else if(current_aspect_ratio.equals("8:5")){
			current_aspect_selection = 3;
			option_1.setSelected(false);
			option_2.setSelected(false);
			option_3.setSelected(true);
			option_4.setSelected(false);
			res_op_list.setItems(container.get(2));
			int x = (int) page.get_width();
			int y = (int) page.get_height();
			
			String current_resolution = "" + x + "x" + y;
			int container_size = container.get(2).size() - 1;
			
			for(int cnt = 0; cnt < container_size; cnt++){
				if(container.get(2).get(cnt).equals(current_resolution)){
					res_op_list.getSelectionModel().select(cnt);
				}
			}
			
			if(container.get(2).get(container_size).contains(current_resolution)){
				res_op_list.getSelectionModel().select(container_size);
			}
		}
		else{
			current_aspect_selection = 4;
			// unsupported; figure out a fix
			// force them into the largest possible resolution supported? Ugh.
			option_1.setSelected(false);
			option_2.setSelected(false);
			option_3.setSelected(false);
			option_4.setSelected(true);
			res_op_list.setItems(container.get(3));
			int x = (int) page.get_width();
			int y = (int) page.get_height();
			
			String current_resolution = "" + x + "x" + y;
			int container_size = container.get(3).size() - 1;
			
			for(int cnt = 0; cnt < container_size; cnt++){
				if(container.get(3).get(cnt).equals(current_resolution)){
					res_op_list.getSelectionModel().select(cnt);
				}
			}
			
			if(container.get(3).get(container_size).contains(current_resolution)){
				res_op_list.getSelectionModel().select(container_size);
			}
		}
		
		 
		
		resolution_title.setTextAlignment(TextAlignment.CENTER);
		resolution_title.setFont(resolution_title_font);
		resolution_ui.getChildren().addAll(resolution_title, aspect_ratio_set, res_op_list);
		resolution_ui.setLayoutX((page.get_settings().MAX_WIDTH/2 - 270) * page.get_x_multiplier());
		resolution_ui.setLayoutY(410 * page.get_y_multiplier());
		page.get_group().getChildren().add(resolution_ui);
		
		radio_button_handlers();
	}
	
	private void radio_button_handlers(){
		// 4:3 resolution option selected
		option_1.selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(!arg1 && arg2){
					current_aspect_selection = 1;
					// do things when set true
					option_2.setSelected(false);
					option_3.setSelected(false);
					option_4.setSelected(false);
					res_op_list.setItems(container.get(0));
					if(page.get_settings().get_current_aspect_ratio().equals("4:3")){
						int x = (int) page.get_width();
						int y = (int) page.get_height();
						
						String current_resolution = "" + x + "x" + y;
						int container_size = container.get(0).size() - 1;
						
						for(int cnt = 0; cnt < container_size; cnt++){
							if(container.get(0).get(cnt).equals(current_resolution)){
								res_op_list.getSelectionModel().select(cnt);
							}
						}
						
						if(container.get(0).get(container_size).contains(current_resolution)){
							res_op_list.getSelectionModel().select(container_size);
						}
					}
					else{
						res_op_list.getSelectionModel().select(0);
					}
				}
				else{
					// do things when set false
					
				}
				
			}
			
		});
		
		// 16:9 resolution option selected
		option_2.selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(!arg1 && arg2){
					current_aspect_selection = 2;
					// do things when set true
					option_1.setSelected(false);
					option_3.setSelected(false);
					option_4.setSelected(false);
					res_op_list.setItems(container.get(1));
					
					if(page.get_settings().get_current_aspect_ratio().equals("16:9")){
						int x = (int)page.get_width();
						int y = (int)page.get_height();
						
						String current_resolution = "" + x + "x" + y;
						int container_size = container.get(1).size() - 1;
						
						for(int cnt = 0; cnt < container_size; cnt++){
							if(container.get(1).get(cnt).equals(current_resolution)){
								res_op_list.getSelectionModel().select(cnt);
							}
						}
						
						if(container.get(1).get(container_size).contains(current_resolution)){
							res_op_list.getSelectionModel().select(container_size);
						}
					}
					else{
						res_op_list.getSelectionModel().select(0);
					}
				}
				else{
					// do things when set false
					
				}
				
			}
			
		});
		
		// 16:10 resolution option selected
		option_3.selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(!arg1 && arg2){
					current_aspect_selection = 3;
					// do things when set true
					option_1.setSelected(false);
					option_2.setSelected(false);
					option_4.setSelected(false);
					res_op_list.setItems(container.get(2));
					
					if(page.get_settings().get_current_aspect_ratio().equals("16x10")){
						int x = (int)page.get_width();
						int y = (int)page.get_height();
						
						String current_resolution = "" + x + ":" + y;
						int container_size = container.get(2).size() - 1;
						
						for(int cnt = 0; cnt < container_size; cnt++){
							if(container.get(2).get(cnt).equals(current_resolution)){
								res_op_list.getSelectionModel().select(cnt);
							}
						}
						
						if(container.get(2).get(container_size).contains(current_resolution)){
							res_op_list.getSelectionModel().select(container_size);
						}
					}
					else{
						res_op_list.getSelectionModel().select(0);
					}
				}
				else{
					// do things when set false
					
				}
				
			}
			
		});
		
		// other resolution option selected
		option_4.selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(!arg1 && arg2){
					current_aspect_selection = 4;
					// do things when set true
					option_1.setSelected(false);
					option_2.setSelected(false);
					option_3.setSelected(false);
					res_op_list.setItems(container.get(3));
					
					if(page.get_settings().get_current_aspect_ratio().equals("other")){
						int x = (int)page.get_width();
						int y = (int)page.get_height();
						
						String current_resolution = "" + x + "x" + y;
						int container_size = container.get(3).size() - 1;
						
						for(int cnt = 0; cnt < container_size; cnt++){
							if(container.get(3).get(cnt).equals(current_resolution)){
								res_op_list.getSelectionModel().select(cnt);
							}
						}
						
						if(container.get(3).get(container_size).contains(current_resolution)){
							res_op_list.getSelectionModel().select(container_size);
						}
					}
					else{
						res_op_list.getSelectionModel().select(0);
					}
				}
				else{
					// do things when set false
					
				}
				
			}
			
		});
		
		option_1.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(current_aspect_selection == 1){
					option_1.setSelected(true);
				}
			}
			
		});
		
		option_2.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(current_aspect_selection == 2){
					option_2.setSelected(true);
				}
			}
			
		});
		
		option_3.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(current_aspect_selection == 3){
					option_3.setSelected(true);
				}
			}
			
		});
		
		option_4.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				if(current_aspect_selection == 4){
					option_4.setSelected(true);
				}
			}
			
		});
	}

	public ListView<String> get_res_op_list() {
		return res_op_list;
	}
	
}
