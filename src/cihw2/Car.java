package cihw2;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Car extends Circle {
	
	private int ratio = 10;
	private Canvas canvasPane;
	protected Sensor sensor1;
	protected Sensor sensor2;
	protected Sensor sensor3;
	protected double startPointX = 30;
	protected double startPointY = 52;
	protected double angle = 90;
	protected double turnAngle = 40;
	protected Fuzzy fuzzy;
	public ArrayList<double[]> tempArray;

	public Car(Canvas canvasPane){
		this.canvasPane = canvasPane;
		
		// initial
		this.setCenterX(startPointX * ratio);
		this.setCenterY(startPointY * ratio);
		this.setRadius(3 * ratio);
		this.setStroke(Color.INDIANRED);
		this.setFill(Color.TRANSPARENT);
		this.setStrokeWidth(2);
				
		// Set sensors
		sensor1 = new Sensor((this.getCenterX())+3*ratio*Math.cos(Math.toRadians(angle)), this.getCenterY()-3*ratio*Math.sin(Math.toRadians(angle)),this.getCenterX(),this.getCenterY());
		sensor2 = new Sensor(this.getCenterX()+(3*ratio*Math.cos(Math.toRadians(angle+45))), this.getCenterY()-(3*ratio*Math.sin(Math.toRadians(angle+45))),this.getCenterX(),this.getCenterY());
		sensor3 = new Sensor(this.getCenterX()+(3*ratio*Math.cos(Math.toRadians(angle-45))), this.getCenterY()-(3*ratio*Math.sin(Math.toRadians(angle-45))),this.getCenterX(),this.getCenterY());
	
		// Set fuzzy 
		fuzzy = new Fuzzy(sensor1, sensor2, sensor3, angle);
		tempArray = new ArrayList<double[]>();
	}

	public void tuneCar(Canvas canvasPane,ArrayList<Gene> geneArray,int bstNo){
		
		double x = this.getCenterX()/ratio-startPointX;
		double y = -1*(this.getCenterY()/ratio-startPointY);
		double[] temp = {0,0};
		temp[0] = x;
		temp[1] = y;
//		System.out.println("car x :"+x+" y :"+y);
		tempArray.add(temp);
		
//		for(int i=0;i<geneArray.get(bstNo)){
//			
//		}
		
		// Calculate turn angle 
		double[] distance = {Double.parseDouble(this.sensor1.getDist())/ratio,Double.parseDouble(this.sensor2.getDist())/ratio,Double.parseDouble(this.sensor3.getDist())/ratio};
		turnAngle = geneArray.get(bstNo).calOutput(distance);
		turnAngle = turnAngle*80 - 40;
//		System.out.println(turnAngle);
		//turnAngle = fuzzy.getTurnAngle();
		
		// Tune car's coordinate
		this.setCenterX(ratio*(this.getCenterX()/ratio+Math.cos(Math.toRadians(angle + turnAngle))+Math.sin(Math.toRadians(turnAngle))*Math.sin(Math.toRadians(angle))));
		this.setCenterY(ratio*(this.getCenterY()/ratio-Math.sin(Math.toRadians(angle + turnAngle))+Math.sin(Math.toRadians(turnAngle))*Math.cos(Math.toRadians(angle))));

		// Let sensors know car's coordinate
		setSensorsCarCoordinate(this.getCenterX(), this.getCenterY());
		
		// Calculate angle with x-axis
		angle = angle - (180/Math.PI)*Math.asin((2*Math.sin(Math.toRadians(turnAngle))/(6)));
		if(angle < 0){
			angle += 360;
		}
		if(angle > 360){
			angle %= 360;
		}
		
		// Set sensors X and Y
		this.sensor1.setX((this.getCenterX())+3*ratio*Math.cos(Math.toRadians(angle)));
		this.sensor1.setY(this.getCenterY()-3*ratio*Math.sin(Math.toRadians(angle)));
		double angleForS2 = angle-45;
		if(angleForS2 <0){
			angleForS2 += 360;
		}
		this.sensor2.setX(this.getCenterX()+(3*ratio*Math.cos(Math.toRadians(angleForS2))));
		this.sensor2.setY(this.getCenterY()-(3*ratio*Math.sin(Math.toRadians(angleForS2))));
		double angleForS3 = angle+45;
		if(angleForS3 > 360){
			angleForS3 %= 360;
		}
		this.sensor3.setX(this.getCenterX()+(3*ratio*Math.cos(Math.toRadians(angleForS3))));
		this.sensor3.setY(this.getCenterY()-(3*ratio*Math.sin(Math.toRadians(angleForS3))));
		
		// Add path on canvas
		addPathOnCanvas(canvasPane);
		
	}
	public void initialSetCar(double coordinateX,double coordinateY){
		
		// Tune car's coordinate
		this.setCenterX(coordinateX);
		this.setCenterY(coordinateY);

		// Let sensors know car's coordinate
		setSensorsCarCoordinate(this.getCenterX(), this.getCenterY());
				
		// Set sensors X and Y
		this.sensor1.setX((this.getCenterX())+3*ratio*Math.cos(Math.toRadians(angle)));
		this.sensor1.setY(this.getCenterY()-3*ratio*Math.sin(Math.toRadians(angle)));
		double angleForS2 = angle-45;
		if(angleForS2 <0){
			angleForS2 += 360;
		}
		this.sensor2.setX(this.getCenterX()+(3*ratio*Math.cos(Math.toRadians(angleForS2))));
		this.sensor2.setY(this.getCenterY()-(3*ratio*Math.sin(Math.toRadians(angleForS2))));
		double angleForS3 = angle+45;
		if(angleForS3 > 360){
			angleForS3 %= 360;
		}
		this.sensor3.setX(this.getCenterX()+(3*ratio*Math.cos(Math.toRadians(angleForS3))));
		this.sensor3.setY(this.getCenterY()-(3*ratio*Math.sin(Math.toRadians(angleForS3))));
		
		// Add path on canvas
	}
	public void setSensorsCarCoordinate(double x,double y){
		this.sensor1.setCarX(x);
		this.sensor2.setCarX(x);
		this.sensor3.setCarX(x);
		this.sensor1.setCarY(y);
		this.sensor2.setCarY(y);
		this.sensor3.setCarY(y);
	}
	public void addPathOnCanvas(Canvas canvasPane){
		
		Circle path = new Circle();
		path.setCenterX(this.getCenterX());
		path.setCenterY(this.getCenterY());
		path.setRadius(3);
		path.setStroke(Color.DARKGRAY);
		path.setFill(Color.DARKGRAY);
		canvasPane.getChildren().add(path);

	}

	
	public void finalTune(){

		// Just move 
		this.setCenterY(this.getCenterX()-ratio*3);
		addPathOnCanvas(canvasPane);
	}
}
