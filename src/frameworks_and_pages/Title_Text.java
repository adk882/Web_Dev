package frameworks_and_pages;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Title_Text {
	private GraphicsContext gc;
	private String text;
	private Page page;
	private double x_pos;
	private double y_pos;
	private Font title_font;
	
	public Title_Text(String text, Page page, int size, double x_pos, double y_pos){
		this.text = text;
		this.page = page;
		gc = page.get_canvas().getGraphicsContext2D();
		
		gc.setFill(Color.RED);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		Font title_font = Font.font("Arial", FontWeight.BOLD, size);
		gc.setFont(title_font);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(text, x_pos, y_pos);
		gc.strokeText(text, x_pos, y_pos);
	}
	
	public void set_size(int size){
		title_font = Font.font("Arial", FontWeight.BOLD, size);
		gc.setFont(title_font);
	}
	
	public void update(){
		// placeholder
		
		// update text
		/*
		gc.setFill(Color.RED);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		Font main_menu_title_font = Font.font("Arial", FontWeight.BOLD, 48);
		gc.setFont(main_menu_title_font);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText("Settings", width/2, 150);
		gc.strokeText("Settings", width/2, 150);
		*/
	}
}
