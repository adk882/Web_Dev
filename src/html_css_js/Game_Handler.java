package html_css_js;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class Game_Handler {
	private int type;
	private int chapter;
	
	public Game_Handler(int type, int chapter){
		this.type = type;
		this.chapter = chapter;
	}
	
	public int get_type(){
		return type;
	}
	
	public int get_chapter(){
		return chapter;
	}
	
	public String process_file(BufferedReader file) throws IOException{
		String hold = "";
		String temp = file.readLine();
		while(temp != null){
			hold += temp + "\n";
			temp = file.readLine();
		}
		return hold;
	}
}
