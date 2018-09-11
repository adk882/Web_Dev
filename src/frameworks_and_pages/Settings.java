package frameworks_and_pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Settings extends Observable {
	// caps
	final public int MAX_WIDTH = 1920;
	final public int MAX_HEIGHT = 1080;
	
	// resolution numerical data
	final private int[] four_three_multipliers = {200, 256, 288, 320, 350};
	final private int[] sixteen_nine_multipliers = {100, 120};
	final private int[] eight_five_multipliers = {180};
	final private int[] odd_resolutions_x = {1280, 1360, 1366};
	final private int[] odd_resolutions_y = {1024, 768, 768};
	
	// resolution ui data (based on screen resolution)
	private double x_multiplier;
	private double y_multiplier;
	
	// setting options
	
	// resolution/ui settings
	private int resolution_x;
	private int resolution_y;
	private boolean full_screen;
	private int f_s_x;
	private int f_s_y;
	private String screen_aspect_ratio;
	private String current_aspect_ratio;
	private ObservableList<String> four_three;
	private ObservableList<String> sixteen_nine;
	private ObservableList<String> eight_five;
	private ObservableList<String> odd_ratios;
	
	// volume settings
	private int master_volume;
	private int music_volume;
	private int sound_effects_volume;
	
	// general data
	private List<Observer> observers;
	
	public Settings(boolean first_run) throws IOException{
		observers = new ArrayList<Observer>();
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		int height = (int)screenBounds.getHeight();
		int width = (int)screenBounds.getWidth();
		
		f_s_y = height;
		f_s_x = width;
		
		int divisor = get_gcd(width, height);
		int ratio_1 = width / divisor;
		int ratio_2 = height / divisor;
		if(!(ratio_1 == 4 && ratio_2 == 3) && !(ratio_1 == 16 && ratio_2 == 9) && !(ratio_1 == 8 && ratio_2 == 5)){
			screen_aspect_ratio = "other";
		}
		screen_aspect_ratio = "" + ratio_1 + ":" + ratio_2;
		
		calculate_resolutions();
		
		if(first_run){
			if(width > MAX_WIDTH){
				x_multiplier = 1;
				resolution_x = 1920;
			}
			else{
				x_multiplier = width / MAX_WIDTH;
				resolution_x = width;
			}
			if(height > MAX_HEIGHT){
				y_multiplier = 1;
				resolution_y = 1080;
			}
			else{
				y_multiplier = height / MAX_HEIGHT;
				resolution_y = height;
			}
			
			if(width > MAX_WIDTH && height > MAX_HEIGHT){
				current_aspect_ratio = "8:5";
				full_screen = false;
			}
			else{
				current_aspect_ratio = screen_aspect_ratio;
				full_screen = true;
			}
			master_volume = 100;
			music_volume = 100;
			sound_effects_volume = 100;
		}
		else{
			File settings = new File("settings.txt");
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(settings));
			} catch (FileNotFoundException e) {
				settings.createNewFile();
				// repair file
			}
			
			// read file and set the variables
			// make sure saved settings are "legal" (i.e. current screen size resolution > saved resolution)
			
			reader.close();
			
			//x_multiplier = ;
			//y_multiplier = ;
		}
	}
	
	public void init(boolean first_run) throws IOException{
		
	}
	
	private int get_gcd(int a, int b){
		if (b == 0) {
	        return a;
	    }

	    return get_gcd(b, a%b);
	}
	
	public void set_resolution(int w, int h){
		resolution_x = w;
		resolution_y = h;
	}
	
	public void set_volume(int master_v, int music_v, int sound_effects_v){
		master_volume = master_v;
		music_volume = music_v;
		sound_effects_volume = sound_effects_v;
		int volume_container[] = {master_volume, music_volume, sound_effects_volume};
		setChanged();
		notifyObservers(volume_container);
	}
	
	public void set_full_screen(boolean input){
		full_screen = input;
	}
	
	public int get_width(){
		return resolution_x;
	}
	
	public int get_height(){
		return resolution_y;
	}
	
	public boolean get_full_screen(){
		return full_screen;
	}
	
	public int get_master_volume(){
		return master_volume;
	}
	
	public int get_sound_effects_volume(){
		return sound_effects_volume;
	}
	
	public int get_music_volume(){
		return music_volume;
	}
	
	public int get_f_s_x(){
		return f_s_x;
	}
	
	public int get_f_s_y(){
		return f_s_y;
	}
	
	public String get_current_aspect_ratio(){
		return current_aspect_ratio;
	}
	
	public String get_screen_aspect_ratio(){
		return screen_aspect_ratio;
	}
	
	public ArrayList<ObservableList<String>> get_resolution_lists(){
		ArrayList<ObservableList<String>> container = new ArrayList<ObservableList<String>>();
		container.add(four_three);
		container.add(sixteen_nine);
		container.add(eight_five);
		container.add(odd_ratios);
		return container;
	}
	
	public double get_x_multiplier(){
		return x_multiplier;
	}
	
	public double get_y_multiplier(){
		return y_multiplier;
	}
	
	public void save(){
		// placeholder
	}
	
	private void calculate_aspect_ratio(){
		int divisor = get_gcd(resolution_x, resolution_y);
		int ratio_1 = resolution_x / divisor;
		int ratio_2 = resolution_y / divisor;
		if(!(ratio_1 == 4 && ratio_2 == 3) && !(ratio_1 == 16 && ratio_2 == 9) && !(ratio_1 == 8 && ratio_2 == 5)){
			current_aspect_ratio = "other";
		}
		else{
			current_aspect_ratio = "" + ratio_1 + ":" + ratio_2;
		}
		
	}
	
	private void calculate_resolutions(){
		four_three = FXCollections.observableArrayList();
		sixteen_nine = FXCollections.observableArrayList();
		eight_five = FXCollections.observableArrayList();
		odd_ratios = FXCollections.observableArrayList();
		
		// 4:3 calculations
		// 200, 256, 288, 320, (add more later)
		int x = f_s_x/4;
		int y = f_s_y/3;
		int max_multiplier = Math.max(x, y);
		
		for(int cnt = 0; cnt < 5; cnt++){
			if(max_multiplier >= four_three_multipliers[cnt]){
				if(four_three_multipliers[cnt]*4 == f_s_x && four_three_multipliers[cnt]*3 == f_s_y){
					sixteen_nine.add("" + (four_three_multipliers[cnt]*4) + "x" + (four_three_multipliers[cnt]*3) + " (full screen)");
				}
				else{
					four_three.add("" + (four_three_multipliers[cnt]*4) + "x" + (four_three_multipliers[cnt]*3));
				}
			}
		}
		
		// 16:9 calculations
		x = f_s_x/16;
		y = f_s_y/9;
		max_multiplier = Math.max(x, y);
		
		for(int cnt = 0; cnt < 2; cnt++){
			if(max_multiplier >= sixteen_nine_multipliers[cnt]){
				if(sixteen_nine_multipliers[cnt]*16 == f_s_x && sixteen_nine_multipliers[cnt]*9 == f_s_y){
					sixteen_nine.add("" + (sixteen_nine_multipliers[cnt]*16) + "x" + (sixteen_nine_multipliers[cnt]*9) + " (full screen)");
				}
				else{
					sixteen_nine.add("" + (sixteen_nine_multipliers[cnt]*16) + "x" + (sixteen_nine_multipliers[cnt]*9));
				}
			}
		}
		
		// 16:10 calculations
		x = f_s_x/8;
		y = f_s_y/5;
		max_multiplier = Math.max(x, y);
		
		for(int cnt = 0; cnt < 1; cnt++){
			if(max_multiplier >= eight_five_multipliers[cnt]){
				if(eight_five_multipliers[cnt]*8 == f_s_x && eight_five_multipliers[cnt]*5 == f_s_y){
					eight_five.add("" + (eight_five_multipliers[cnt]*8) + "x" + (eight_five_multipliers[cnt]*5) + " (full screen)");
				}
				else{
					eight_five.add("" + (eight_five_multipliers[cnt]*8) + "x" + (eight_five_multipliers[cnt]*5));
				}
			}
		}
		
		// odd ratio calculations
		for(int cnt = 0; cnt < 3; cnt++){
			if(f_s_x >= odd_resolutions_x[cnt] && f_s_y >= odd_resolutions_y[cnt]){
				if(odd_resolutions_x[cnt] == f_s_x && odd_resolutions_y[cnt] == f_s_y){
					odd_ratios.add("" + odd_resolutions_x[cnt] + "x" + odd_resolutions_y[cnt] + " (full screen)");
				}
				else{
					odd_ratios.add("" + odd_resolutions_x[cnt] + "x" + odd_resolutions_y[cnt]);
				}
			}
		}
		
		
	}
	
	// Volume listeners
	private boolean change_flag = false;
	
	public void add_audio_observer(Observer o){
		observers.add(o);
	}
	
	@Override
	public void notifyObservers(Object arg0){
		if(hasChanged()){
			for(int x = 0; x < observers.size(); x++){
				System.out.println("Observer Notified");
				observers.get(x).update(this, arg0);	
			}
		}
	}
	
	@Override
	public void setChanged(){
		change_flag = true;
	}
	
	@Override
	public boolean hasChanged(){
		return change_flag;
		
	}
	
	public void set_default_values(){
		if(f_s_x > MAX_WIDTH){
			x_multiplier = 1;
			resolution_x = 1920;
		}
		else{
			x_multiplier = f_s_x / MAX_WIDTH;
			resolution_x = f_s_x;
		}
		if(f_s_y > MAX_HEIGHT){
			y_multiplier = 1;
			resolution_y = 1080;
		}
		else{
			y_multiplier = f_s_y / MAX_HEIGHT;
			resolution_y = f_s_y;
		}
		
		if(f_s_x > MAX_WIDTH && f_s_y > MAX_HEIGHT){
			current_aspect_ratio = "8:5";
			full_screen = false;
		}
		else{
			current_aspect_ratio = screen_aspect_ratio;
			full_screen = true;
		}
		master_volume = 100;
		music_volume = 100;
		sound_effects_volume = 100;
	}
}
