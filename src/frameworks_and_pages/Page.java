package frameworks_and_pages;

import java.util.HashMap;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class Page {
	private Scene scene;
	private Group group;
	private Canvas canvas;
	
	private double width;
	private double height;
	private double x_multiplier;
	private double y_multiplier;
	
	private Settings settings;
	private Flags flags;
	
	private int page_id;
	private Page_Manager pm;
	
	public Page(Settings settings, Flags flags, int id, Page_Manager pm){
		group = new Group();
		scene = new Scene(group);
		canvas = new Canvas(settings.get_width(), settings.get_height());
		group.getChildren().add(canvas);
		
		width = settings.get_width();
		height = settings.get_height();
		x_multiplier = settings.get_x_multiplier();
		y_multiplier = settings.get_y_multiplier();
		
		this.settings = settings;
		
		page_id = id;
		
		this.pm = pm;
		pm.add_page(this);
		
		this.flags = flags;
	}
	
	public Page(Settings settings, Flags flags, Stage main_frame){
		group = new Group();
		scene = new Scene(group);
		canvas = new Canvas(settings.get_width(), settings.get_height());
		group.getChildren().add(canvas);
		
		width = settings.get_width();
		height = settings.get_height();
		x_multiplier = settings.get_x_multiplier();
		y_multiplier = settings.get_y_multiplier();
		
		this.settings = settings;
		
		page_id = 0;
		
		this.flags = flags;
	}
	
	// Method call to update page settings. Should be called before update or within update.
	public void update_page(){
		width = settings.get_width();
		height = settings.get_height();
		x_multiplier = settings.get_x_multiplier();
		y_multiplier = settings.get_y_multiplier();
		//update();
	}
	
	public int get_id(){
		return page_id;
	}
	
	public Group get_group(){
		return group;
	}
	
	public Scene get_scene(){
		return scene;
	}
	
	public Canvas get_canvas(){
		return canvas;
	}
	
	public void set_width(double width){
		this.width = width;
	}
	
	public double get_width(){
		return width;
	}
	
	public double get_height(){
		return height;
	}
	
	public Page_Manager get_page_manager(){
		return pm;
	}
	
	public void set_height(double height){
		this.height = height;
	}
	
	public void page_transition(Page next){
		group.setDisable(true);
		Rectangle screen_cover = new Rectangle(width, height, Color.BLACK);
		FadeTransition ft = new FadeTransition(Duration.millis(1500), screen_cover);
		ft.setFromValue(0);
		ft.setToValue(1);
		group.getChildren().add(screen_cover);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				pm.set_active_scene(next);
				next.get_group().setDisable(true);
				FadeTransition ft = new FadeTransition(Duration.millis(1500), screen_cover);
				next.get_group().getChildren().add(screen_cover);
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.play();
				ft.setOnFinished(new EventHandler<ActionEvent>(){

					@Override
					public void handle(ActionEvent event) {
						next.get_group().setDisable(false);
						next.get_group().getChildren().remove(screen_cover);
					}
					
				});
			}
			
		});
	}

	public double get_y_multiplier() {
		return y_multiplier;
	}
	
	public double get_x_multiplier(){
		return x_multiplier;
	}
	
	public Settings get_settings(){
		return settings;
	}
	
	public Flags get_flags(){
		return flags;
	}
	
	// Method call to update the various elements of this class. Must be overridden.
	public abstract void update();
	
	// Method call to instantiate the elements for this class. Must be overridden.
	public abstract void init();
	
}
