package cihw2;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Canvas extends Pane{

	protected int ratio = 10;
	protected Line line1;
	protected Line line2;
	protected Line line3;
	protected Line line4;
	protected Line line5;
	protected Line line6;
	protected Line line7;
	protected Line line8;

	public Canvas(){
		
		this.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, null, null)));
		this.setPrefSize(800, 800);

		line1 = new Line();
		line1.setStartX(24 * ratio);
		line1.setStartY(52 * ratio);
		line1.setEndX(36 * ratio);
		line1.setEndY(52 * ratio);
		line1.setStroke(Color.BLACK);
		line1.setStrokeWidth(3);
		this.getChildren().add(line1);

		line2 = new Line();
		line2.setStartX(24 * ratio);
		line2.setStartY(52 * ratio);
		line2.setEndX(24 * ratio);
		line2.setEndY(30 * ratio);
		line2.setStroke(Color.GRAY);
		line2.setStrokeWidth(3);
		this.getChildren().add(line2);

		line3 = new Line();
		line3.setStartX(36 * ratio);
		line3.setStartY(52 * ratio);
		line3.setEndX(36 * ratio);
		line3.setEndY(42 * ratio);
		line3.setStroke(Color.GRAY);
		line3.setStrokeWidth(3);

		this.getChildren().add(line3);

		line4 = new Line();
		line4.setStartX(24 * ratio);
		line4.setStartY(30 * ratio);
		line4.setEndX(48 * ratio);
		line4.setEndY(30 * ratio);
		line4.setStroke(Color.GRAY);
		line4.setStrokeWidth(3);

		this.getChildren().add(line4);

		line5 = new Line();
		line5.setStartX(36 * ratio);
		line5.setStartY(42 * ratio);
		line5.setEndX(60 * ratio);
		line5.setEndY(42 * ratio);
		line5.setStroke(Color.GRAY);
		line5.setStrokeWidth(3);

		this.getChildren().add(line5);

		line6 = new Line();
		line6.setStartX(48 * ratio);
		line6.setStartY(30 * ratio);
		line6.setEndX(48 * ratio);
		line6.setEndY(15 * ratio);
		line6.setStroke(Color.GRAY);
		line6.setStrokeWidth(3);

		this.getChildren().add(line6);
		
		line7 = new Line();
		line7.setStartX(60 * ratio);
		line7.setStartY(42 * ratio);
		line7.setEndX(60 * ratio);
		line7.setEndY(15 * ratio);
		line7.setStroke(Color.GRAY);
		line7.setStrokeWidth(3);
		this.getChildren().add(line7);
		
		line8 = new Line();
		line8.setStartX(48 * ratio);
		line8.setStartY(15 * ratio);
		line8.setEndX(60 * ratio);
		line8.setEndY(15 * ratio);
		line8.setStroke(Color.GRAY);
		line8.setStrokeWidth(3);
		this.getChildren().add(line8);
	}
	public void rePaint(){
		
		this.getChildren().clear();
		
		line1 = new Line();
		line1.setStartX(24 * ratio);
		line1.setStartY(52 * ratio);
		line1.setEndX(36 * ratio);
		line1.setEndY(52 * ratio);
		line1.setStroke(Color.BLACK);
		line1.setStrokeWidth(3);
		this.getChildren().add(line1);

		line2 = new Line();
		line2.setStartX(24 * ratio);
		line2.setStartY(52 * ratio);
		line2.setEndX(24 * ratio);
		line2.setEndY(30 * ratio);
		line2.setStroke(Color.GRAY);
		line2.setStrokeWidth(3);
		this.getChildren().add(line2);

		line3 = new Line();
		line3.setStartX(36 * ratio);
		line3.setStartY(52 * ratio);
		line3.setEndX(36 * ratio);
		line3.setEndY(42 * ratio);
		line3.setStroke(Color.GRAY);
		line3.setStrokeWidth(3);

		this.getChildren().add(line3);

		line4 = new Line();
		line4.setStartX(24 * ratio);
		line4.setStartY(30 * ratio);
		line4.setEndX(48 * ratio);
		line4.setEndY(30 * ratio);
		line4.setStroke(Color.GRAY);
		line4.setStrokeWidth(3);

		this.getChildren().add(line4);

		line5 = new Line();
		line5.setStartX(36 * ratio);
		line5.setStartY(42 * ratio);
		line5.setEndX(60 * ratio);
		line5.setEndY(42 * ratio);
		line5.setStroke(Color.GRAY);
		line5.setStrokeWidth(3);

		this.getChildren().add(line5);

		line6 = new Line();
		line6.setStartX(48 * ratio);
		line6.setStartY(30 * ratio);
		line6.setEndX(48 * ratio);
		line6.setEndY(15 * ratio);
		line6.setStroke(Color.GRAY);
		line6.setStrokeWidth(3);

		this.getChildren().add(line6);
		
		line7 = new Line();
		line7.setStartX(60 * ratio);
		line7.setStartY(42 * ratio);
		line7.setEndX(60 * ratio);
		line7.setEndY(15 * ratio);
		line7.setStroke(Color.GRAY);
		line7.setStrokeWidth(3);
		this.getChildren().add(line7);
		
		line8 = new Line();
		line8.setStartX(48 * ratio);
		line8.setStartY(15 * ratio);
		line8.setEndX(60 * ratio);
		line8.setEndY(15 * ratio);
		line8.setStroke(Color.GRAY);
		line8.setStrokeWidth(3);
		this.getChildren().add(line8);
	}
	
}
