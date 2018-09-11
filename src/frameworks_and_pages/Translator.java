package frameworks_and_pages;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class Translator {
	private String file_path;
	private BufferedReader br;
	private Page page;
	
	public Translator(String fp, Page page) throws FileNotFoundException{
		file_path = fp;
		br = new BufferedReader(new FileReader(fp));
		this.page = page;
	}
	
	public String get_file_path(){
		return file_path;
	}
	
	public BufferedReader get_buffered_reader(){
		return br;
	}
	
	public abstract void process_file();
}
