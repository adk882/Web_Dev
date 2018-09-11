package frameworks_and_pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * IDs:
 * 		- 0 : Main Menu
 * 		- 1 : Game Mode Menu
 * 		- 2 : Settings
 * 		- 3...
 */

public class Page_Manager {
	private HashMap<Integer, Page> pages;
	private int size;
	private Stage main_frame;
	
	// debugging purposes
	private static Page active_page;
	
	public Page_Manager(Stage main_frame){
		pages = new HashMap<Integer, Page>();
		size = 0;
		this.main_frame = main_frame;
	}
	
	public Page_Manager(Stage main_frame, Page start){
		pages = new HashMap<Integer, Page>();
		size = 0;
		this.main_frame = main_frame;
		add_page(start);
		main_frame.setScene(start.get_scene());
	}
	
	public void add_page(Page page){
		pages.put(page.get_id(), page);
		size++;
	}
	
	public void add_pages(Page... pages){
		for(Page page : pages){
			size++;
			this.pages.put(page.get_id(), page);
		}
	}
	
	public void set_active_scene(Page page){
		main_frame.setScene(page.get_scene());
		active_page = page;
	}
	
	public void set_active_page(Page page){
		active_page = page;
	}
	
	public Page get_page(int id){
		return pages.get(id);
	}
	
	// Public or Private? Need to double check later.
	// Can parallelize this; may modify to parallelize
	public void update_pages(){
		for(int x = 0; x < size; x++){
			pages.get(0).update();
		}
	}
	
	public void remove_page(int id){
		pages.remove(id);
	}
	
}
