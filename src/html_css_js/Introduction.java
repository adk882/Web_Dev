package html_css_js;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Introduction extends Game_Handler {
	String intro_title;
	String intro_text;

	public Introduction(int type) {
		super(type, 0);
		BufferedReader br;
		switch(type){
			case 0:
				intro_title = "Introduction to HTML";
				try {
					br = new BufferedReader(new FileReader("html_intro_text.txt"));
					intro_text = process_file(br);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			case 1:
				intro_title = "Introduction to CSS";
				try {
					br = new BufferedReader(new FileReader("css_intro_text.txt"));
					intro_text = process_file(br);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				intro_title = "Introduction to JavaScript";
				try {
					br = new BufferedReader(new FileReader("javascript_intro_text.txt"));
					intro_text = process_file(br);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
	}
	
	public String get_intro_text(){
		return intro_text;
	}
	
	public String get_intro_title(){
		return intro_title;
	}

}
