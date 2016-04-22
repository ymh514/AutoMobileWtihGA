package cihw2;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Parameter extends VBox{
	public Button loadFile = new Button("Load File");
	public Button start = new Button("Start");
	public Button restart = new Button("Restart");
	public Slider slider = new Slider();
	public Label initialAngle = new Label("Angle value : 90º");
	public Label initialAngleSign = new Label("Please slide to start angle :");
	public Label line1Dist = new Label("Red");
	public Label line2Dist = new Label("Blue");
	public Label line3Dist = new Label("Green");
	public Label angleInfo = new Label("");

	public Parameter() {
		this.setSpacing(10);
		this.setPadding(new Insets(15, 50, 15, 15));
		slider.setPrefSize(180, 30);
		slider.setMin(-270);
		slider.setMax(90);
		slider.setValue(90);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(10);
		line1Dist.setTextFill(Color.DARKRED);
		line2Dist.setTextFill(Color.DARKBLUE);
		line3Dist.setTextFill(Color.DARKGREEN);
		this.getChildren().addAll(loadFile,start,restart, line3Dist, line1Dist, line2Dist, angleInfo,initialAngleSign,slider,initialAngle);

	}

}
