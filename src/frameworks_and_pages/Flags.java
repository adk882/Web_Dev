package frameworks_and_pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.StringTokenizer;

public class Flags {
	private boolean first_run;
	private int html_unlock_level;
	private int css_unlock_level;
	private int js_unlock_level;
	
	public Flags() throws IOException{
		File flags_file = new File("flags.txt");
		BufferedReader reader = null;
		try{
		reader = new BufferedReader(new FileReader(flags_file));
		}
		catch (FileNotFoundException e) {
			flags_file.createNewFile();
			System.out.println("File Created.");
			// repair file and exit
			File_Repair.repair_flags_file();
		}
		
		// check flags
		StringTokenizer st = null;
		String line = reader.readLine();
		if(line.equals("")){
			// repair file and exit
			File_Repair.repair_flags_file();
			
		}
		st = new StringTokenizer(line, "=");
		if(st.nextToken().equals("first_run")){
			if(st.nextToken().equals("false")){
				first_run = false;
			}
			else{
				first_run = true;
			}
		}
		else{
			// repair file and exit
			File_Repair.repair_flags_file();
		}
		
		line = reader.readLine();
		if(line.equals("")){
			// repair file and exit
			File_Repair.repair_flags_file();
		}
		st = new StringTokenizer(line, "=");
		if(st.nextToken().equals("html_unlock_level")){
			html_unlock_level = Integer.parseInt(st.nextToken());
		}
		else{
			// repair file and exit
			File_Repair.repair_flags_file();
		}
		
		line = reader.readLine();
		if(line.equals("")){
			// repair file and exit
			File_Repair.repair_flags_file();
		}
		st = new StringTokenizer(line, "=");
		if(st.nextToken().equals("css_unlock_level")){
			css_unlock_level = Integer.parseInt(st.nextToken());
		}
		else{
			// repair file and exit
			File_Repair.repair_flags_file();
		}
		
		line = reader.readLine();
		if(line.equals("")){
			// repair file and exit
			File_Repair.repair_flags_file();
		}
		st = new StringTokenizer(line, "=");
		if(st.nextToken().equals("js_unlock_level")){
			js_unlock_level = Integer.parseInt(st.nextToken());
		}
		else{
			// repair file and exit
			File_Repair.repair_flags_file();
		}
			
		// close flags
		reader.close();
	}
	
	public boolean first_run(){
		return first_run;
	}
	
	public int get_html_level(){
		return html_unlock_level;
	}
	
	public int get_css_level(){
		return css_unlock_level;
	}
	
	public int get_js_level(){
		return js_unlock_level;
	}
	
	public void save(){
		// placeholder
	}

}
