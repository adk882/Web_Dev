package frameworks_and_pages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class File_Repair {
	public static void repair_settings_file() throws IOException{
		File settings = new File("settings.txt");
		BufferedReader reader = new BufferedReader(new FileReader(settings));
		BufferedWriter writer = new BufferedWriter(new FileWriter(settings));
		
	}
	
	public static void repair_flags_file() throws IOException{
		File flags = new File("flags.txt");
		BufferedReader reader = new BufferedReader(new FileReader(flags));
		BufferedWriter writer = new BufferedWriter(new FileWriter(flags));
	}
}
