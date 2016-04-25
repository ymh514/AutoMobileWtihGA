package cihw2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import cihw2.Canvas;
import cihw2.Gene;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class cihw2 extends Application {

	private Canvas canvasPane;
	private Car car;
	private Line sensorLine1;
	private Line sensorLine2;
	private Line sensorLine3;
	private double startPointX = 30;
	private double startPointY = 52;
	private int ratio = 10;
	public ArrayList<double[]> inputArray;
	private ArrayList<Gene> geneArray;
	private int looptimes;
	private int groupSize;
	private double crossoverProb;
	private double mutationProb;
	private ArrayList<ArrayList<double[]>> geneInfoArray;
	private ArrayList<ArrayList<double[]>> pool;
	private int iteration;
	private double avgError;
	private int bstErrorNo;
	private double bstErrorValue;
	private double errorLimit = 0.03;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("A battle with Computational Intelligence.");

		/*
		 * Initial setting
		 */
		inputArray = new ArrayList<double[]>();
		canvasPane = new Canvas();
		car = new Car(this.canvasPane);

		BorderPane ciPane = new BorderPane();
		VBox infoBox = new VBox(10);
		Button loadFile = new Button("Load File");
		Button reset = new Button("Reset");
		Button start = new Button("Start");
		Button go = new Button("Go");
		Label looptimesLabel = new Label("Looptimes :");
		Label groupSizeLabel = new Label("Group size :");
		Label crossoverProbLabel = new Label("Crossover Probability");
		Label mutationProbLabel = new Label("Mutation Probability");
		TextField looptimesText = new TextField("50000");
		TextField groupSizeText = new TextField("100");
		TextField crossoverProbText = new TextField("0.5");
		TextField mutationProbText = new TextField("0.2");

		infoBox.setPadding(new Insets(15, 50, 15, 15));
		infoBox.getChildren().addAll(loadFile, reset, looptimesLabel, looptimesText, groupSizeLabel, groupSizeText,
				crossoverProbLabel, crossoverProbText, mutationProbLabel, mutationProbText, start,go);
		canvasPane.getChildren().add(car);
		ciPane.setRight(canvasPane);
		ciPane.setLeft(infoBox);

		/*
		 * Set sensor lines
		 */

		sensorLinesSetting();

		loadFile.setOnMouseClicked(event -> {
			inputArray.clear();
			try {
				inputFileChoose(null, loadFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Cancel");
				e.printStackTrace();

			}
			inputDataNormalize();
			// drawPath(inputArray);
		});

		start.setOnMouseClicked(event -> {
			iteration = 0;
			avgError = Double.MAX_VALUE;

			geneInfoArray = new ArrayList<ArrayList<double[]>>();
			pool = new ArrayList<ArrayList<double[]>>();

			this.looptimes = Integer.parseInt(looptimesText.getText());
			this.groupSize = Integer.parseInt(groupSizeText.getText());
			this.crossoverProb = Double.parseDouble(crossoverProbText.getText());
			this.mutationProb = Double.parseDouble(mutationProbText.getText());

			// generate gene
			geneArray = new ArrayList<Gene>();

			for (int i = 0; i < groupSize; i++) {
				Gene tempGene = new Gene();
				geneArray.add(tempGene);
			}

			while (true) {

				double[] errorArray = new double[geneArray.size()];
				double[] bstError = new double[geneArray.size()];
				ArrayList<double[]> avgErrorArray = new ArrayList<double[]>();

				bstErrorNo = 0;
				bstErrorValue = Double.MAX_VALUE;
				
				geneInfoArray = new ArrayList<ArrayList<double[]>>();
				avgError = 0;

				for (int i = 0; i < geneArray.size(); i++) {
					double bstv = 0;

					for (int j = 0; j < inputArray.size(); j++) {
						double[] distance = new double[3];
						double desire = inputArray.get(j)[inputArray.get(j).length - 1];

						distance[0] = inputArray.get(j)[0];
						distance[1] = inputArray.get(j)[1];
						distance[2] = inputArray.get(j)[2];

						double output = geneArray.get(i).calOutput(distance);
						double errorTemp = Math.pow((desire - output), 2);
						double avgETemp = Math.abs(desire - output);
						bstv += avgETemp;
						errorArray[i] += errorTemp;
					}
					bstError[i] = bstv;

					errorArray[i] /= 2;
					geneInfoArray.add(geneArray.get(i).getGeneInfo());
				}

				for(int i=0;i<bstError.length;i++){
					if(bstError[i] < bstErrorValue){
						bstErrorValue = bstError[i];
						bstErrorNo = i;
					}
				}
				double tt = bstError[bstErrorNo];
//				for(int i=0;i<bstError[bstErrorNo];i++){
//					tt += avgErrorArray.get(bstErrorNo)[i];
//				}
				tt /= bstError.length;
				System.out.println(" avg : "+tt);

//				for (int i = 0; i < avgErrorTempArray.length; i++) {
//					avgError += avgErrorTempArray[i];
//				}
//				
//				avgError = avgError / avgErrorTempArray.length;
//				System.out.println(iteration+" avg : "+avgError);
				
				for (int i = 0; i < errorArray.length; i++) {
					avgError += errorArray[i];
				}
				avgError = tt;
				System.out.println(iteration+" rmse : "+avgError + "best no :"+bstErrorNo+" value : "+bstErrorValue);

				double errorTotal = 0;
				for (int i = 0; i < errorArray.length; i++) {
//					System.out.println("error : " + error[i]);
					errorTotal += errorArray[i];
				}
				
				System.out.println("------------------------------");
				
				double newTotal = 0;
				for (int i = 0; i < errorArray.length; i++) {
					errorArray[i] = Math.abs(errorArray[i] - errorTotal);
					newTotal += errorArray[i];
					// System.out.println("new error : " + error[i]);
				}
				for (int i = 0; i < errorArray.length; i++) {
					errorArray[i] /= newTotal;
					// System.out.println("normal error : " + error[i]);

				}

				reproduction(errorArray);

				crossover();

				mutation();

				for(int i=0;i<geneArray.size();i++){
//					System.out.println("-----------------");
//					System.out.println(geneArray.size());
//					System.out.println(geneInfoArray.size());
//					System.out.println("-----------------");

					geneArray.get(i).updateGeneInfo(geneInfoArray.get(i));
				}
				
				if (iteration > looptimes) {
					System.out.println("looptimes break loop");
					break;
				}
				if(avgError < errorLimit){
					System.out.println("good error break");
					break;
				}

				iteration++;
			}
		});

		go.setOnMouseClicked(event ->{
			new Thread() {
				public void run() {
					while (true) {
						try {
							// Main thread sleep
							Thread.sleep(100);

							Platform.runLater(new Runnable() {
								// GUI update by javafx thread
								@Override
								public void run() {
									// The function for the final round

										// Tune car's position and angle
										car.tuneCar(canvasPane,geneArray,bstErrorNo);
										initialSetSensorsLine();

								}
							});

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}.start();


		});
		reset.setOnMouseClicked(event -> {
			canvasPane.rePaint();
			loadFile.setText("Load File");
			inputArray.clear();
		});

		canvasPane.setOnMouseClicked(event -> {
			car.initialSetCar(event.getX(), event.getY());
			initialSetSensorsLine();
		});

		Scene primaryScene = new Scene(ciPane);
		primaryStage.setScene(primaryScene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public void reproduction(double[] normalError) {
		
		pool = new ArrayList<ArrayList<double[]>>();
		
		double[] boundary = new double[normalError.length + 1];
		boundary[0] = 0;
		for (int i = 0; i < normalError.length; i++) {
			int boundaryCount = i + 1;
			if (i == normalError.length - 1) {
				boundary[boundaryCount] = 1;
			} else {
				boundary[boundaryCount] = boundary[i] + normalError[i];
			}
		}
//		 for (int i = 0; i < boundary.length; i++) {
//			 System.out.println("boundary" + i + " : " + boundary[i]);
//		 }
//		System.out.println(iteration + " geneInfoArray "+geneInfoArray.size());

		for (int i = 0; i < geneInfoArray.size(); i++) {
			double rand = Math.random();
			int reproductionNo = 0;
//			 System.out.println("rand:" + rand);
			for (int j = 0; j < boundary.length - 1; j++) {
				if (rand >= boundary[j] && rand < boundary[j + 1]) {
					reproductionNo = j;
//					 System.out.println("region :" + j);
				}
			}

			pool.add(geneInfoArray.get(reproductionNo));
		}

		for (int i = 0; i < pool.size(); i++) {
			for (int j = 0; j < pool.get(i).size(); j++) {
				for (int k = 0; k < pool.get(i).get(j).length; k++) {
					if (j == 0 || j == 1) {
						Random rand = new Random();
						double temp = 0;

						if (Math.random() > 0.5) {
							temp = (rand.nextFloat() + 0f) / 10;
						} else {
							temp = (rand.nextFloat() - 1f) / 10;
						}
//						temp = (temp - iteration) / looptimes;

						double judge = pool.get(i).get(j)[k] + temp;
						if (judge > 1 || judge < -1) {
							// complete reproduction
						} else {
							// add some noise
							pool.get(i).get(j)[k] = judge;
						}
					} else if (j == 1) {
						double judge = pool.get(i).get(j)[k] + Math.random();
						if (judge > 30 || judge < 0) {
							// complete reproduction
						} else {
							// add some noise
							pool.get(i).get(j)[k] = judge;
						}

					} else {
						double judge = pool.get(i).get(j)[k] + Math.random();
						if (judge > 10 || judge < 0) {
							// complete reproduction
						} else {
							// add some noise
							pool.get(i).get(j)[k] = judge;
						}

					}
				}
			}
		}
	}

	public void crossover() {
		// crossover
//		int crossNo1 = (int)Math.random()*(pool.size() - 1);
		for (int i = 0; i < pool.size(); i++) {
			int crossNo1 = i;
			Random rand = new Random();
			int crossNo2 = rand.nextInt(pool.size() - 1);
			while (crossNo2 == crossNo1) {
				crossNo2 = rand.nextInt(pool.size() - 1);
			}

			double distanceDef = Math.random();
			double crossSigma = Math.random() / 1000;
			double doCrossoverProb = Math.random();

			if (doCrossoverProb < crossoverProb) {
				// do crossover
				if (distanceDef > 0.5) {
					for (int j = 0; j < pool.get(crossNo1).size(); j++) {
						for (int k = 0; k < pool.get(crossNo1).get(j).length; k++) {
							double c1 = pool.get(crossNo1).get(j)[k];
							double c2 = pool.get(crossNo2).get(j)[k];
							double judge1 = c1 + crossSigma * (c1 - c2);
							double judge2 = c2 - crossSigma * (c1 - c2);

							if (j == 0 || j == 1) {
								if (judge1 > 1 || judge1 < 0) {
									// complete reproduction
								} else {
									// add some noise
									pool.get(crossNo1).get(j)[k] = judge1;
								}
								if (judge2 > 1 || judge2 < 0) {
								} else {
									pool.get(crossNo2).get(j)[k] = judge2;
								}
							} else if (j == 2) {
								if (judge1 > 30 || judge1 < 0) {
								} else {
									pool.get(crossNo1).get(j)[k] = judge1;
								}
								if (judge2 > 30 || judge2 < 0) {
								} else {
									pool.get(crossNo2).get(j)[k] = judge2;
								}
							} else {
								if (judge1 > 10 || judge1 < 0) {
								} else {
									pool.get(crossNo1).get(j)[k] = judge1;
								}
								if (judge2 > 10 || judge2 < 0) {
								} else {
									pool.get(crossNo2).get(j)[k] = judge2;
								}
							}
						}
					}
				} else {
					for (int j = 0; j < pool.get(crossNo1).size(); j++) {
						for (int k = 0; k < pool.get(crossNo1).get(j).length; k++) {
							double c1 = pool.get(crossNo1).get(j)[k];
							double c2 = pool.get(crossNo2).get(j)[k];
							double judge1 = c1 + crossSigma * (c2 - c1);
							double judge2 = c2 - crossSigma * (c2 - c1);

							if (j == 0 || j == 1) {
								if (judge1 > 1 || judge1 < 0) {
								} else {
									pool.get(crossNo1).get(j)[k] = judge1;
								}
								if (judge2 > 1 || judge2 < 0) {
								} else {
									pool.get(crossNo2).get(j)[k] = judge2;
								}
							} else if (j == 2) {
								if (judge1 > 30 || judge1 < 0) {
								} else {
									pool.get(crossNo1).get(j)[k] = judge1;
								}
								if (judge2 > 30 || judge2 < 0) {
								} else {
									pool.get(crossNo2).get(j)[k] = judge2;
								}
							} else {
								if (judge1 > 10 || judge1 < 0) {
								} else {
									pool.get(crossNo1).get(j)[k] = judge1;
								}
								if (judge2 > 10 || judge2 < 0) {
								} else {
									pool.get(crossNo2).get(j)[k] = judge2;
								}

							}
						}
					}
				}
			} else {
				// donothing
			}
		}
		
		
//		System.out.println("-----------------");
//		System.out.println("pool:"+pool.size());
//		System.out.println("-----------------");

		geneInfoArray = pool;
	}

	public void mutation() {
		// mutation
		double doMutationProb = Math.random();
		double s = Math.random() / 100;
		double randomNois = Math.random() / 100;

		if (doMutationProb < mutationProb) {

			int mutationNo = (int)Math.random()*(geneInfoArray.size() - 1);
			
			for (int i = 0; i < geneInfoArray.get(mutationNo).size(); i++) {
				for (int j = 0; j < geneInfoArray.get(mutationNo).get(i).length; j++) {
					if (i == 0 || i == 1) {
						double judge = geneInfoArray.get(mutationNo).get(i)[j] + s * randomNois;
						if (judge > 1 || judge < 0) {
						} else {
							geneInfoArray.get(mutationNo).get(i)[j] = judge;
						}
					} else if (i == 2) {
						double judge = geneInfoArray.get(mutationNo).get(i)[j] + s * randomNois;
						if (judge > 30 || judge < 0) {
						} else {
							geneInfoArray.get(mutationNo).get(i)[j] = judge;
						}
					} else {
						double judge = geneInfoArray.get(mutationNo).get(i)[j] + s * randomNois;
						if (judge > 10 || judge < 0) {
						} else {
							geneInfoArray.get(mutationNo).get(i)[j] = judge;
						}
					}
				}
			}
		} else {

		}
		
	}

	public void drawPath(ArrayList<float[]> showArray) {
		for (int i = 0; i < showArray.size(); i++) {
			Circle path = new Circle();
			path.setCenterX(startPointX * ratio + showArray.get(i)[0] * ratio);
			path.setCenterY(startPointY * ratio + showArray.get(i)[1] * -ratio);
			path.setRadius(3);
			path.setStroke(Color.DARKGRAY);
			path.setFill(Color.DARKGRAY);
			canvasPane.getChildren().add(path);

		}
	}

	public void inputDataNormalize() {
		int desireNumer = inputArray.get(0).length - 1;
		for (int i = 0; i < inputArray.size(); i++) {
			double cal = inputArray.get(i)[desireNumer];
			cal = (cal + 40) / 80;
			inputArray.get(i)[desireNumer] = cal;
		}
	}

	public void initialSetSensorsLine() {
		sensorLine1.setEndX(car.sensor1.getX());
		sensorLine1.setEndY(car.sensor1.getY());
		sensorLine2.setEndX(car.sensor2.getX());
		sensorLine2.setEndY(car.sensor2.getY());
		sensorLine3.setEndX(car.sensor3.getX());
		sensorLine3.setEndY(car.sensor3.getY());

		// Calculate the distance with walls
		car.sensor1.calDistance(canvasPane);
		car.sensor2.calDistance(canvasPane);
		car.sensor3.calDistance(canvasPane);

		// Set showing information
		// line1Dist.setText("Red Line :" + car.sensor1.getDist());
		// line2Dist.setText("Blue Line :" + car.sensor2.getDist());
		// line3Dist.setText("Green Line :" + car.sensor3.getDist());
		// angleInfo.setText("Angle with x-axis : " + Math.round(car.angle *
		// 1000.0) / 1000.0 + "º");

		// Set sensor lines
		int sensor1ClosetId = car.sensor1.getClosestLineId();
		int sensor2ClosetId = car.sensor2.getClosestLineId();
		int sensor3ClosetId = car.sensor3.getClosestLineId();

		sensorLine1.setEndX(car.sensor1.getIntersectionPointX(sensor1ClosetId));
		sensorLine1.setEndY(car.sensor1.getIntersectionPointY(sensor1ClosetId));
		sensorLine2.setEndX(car.sensor2.getIntersectionPointX(sensor2ClosetId));
		sensorLine2.setEndY(car.sensor2.getIntersectionPointY(sensor2ClosetId));
		sensorLine3.setEndX(car.sensor3.getIntersectionPointX(sensor3ClosetId));
		sensorLine3.setEndY(car.sensor3.getIntersectionPointY(sensor3ClosetId));

	}

	public void printCurrentThread() {
		System.out.println("************************");
		System.out.println(Thread.currentThread());
		System.out.println("************************");

	}

	public void sensorLinesSetting() {
		sensorLine1 = new Line();
		sensorLine1.setStartX(car.getCenterX());
		sensorLine1.setStartY(car.getCenterY());
		sensorLine1.setEndX(car.sensor1.getX());
		sensorLine1.setEndY(car.sensor1.getY());
		sensorLine1.startXProperty().bind(car.centerXProperty());
		sensorLine1.startYProperty().bind(car.centerYProperty());
		sensorLine1.setStroke(Color.DARKRED);

		sensorLine2 = new Line();
		sensorLine2.setStartX(car.getCenterX());
		sensorLine2.setStartY(car.getCenterY());
		sensorLine2.setEndX(car.sensor2.getX());
		sensorLine2.setEndY(car.sensor2.getY());
		sensorLine2.startXProperty().bind(car.centerXProperty());
		sensorLine2.startYProperty().bind(car.centerYProperty());
		sensorLine2.setStroke(Color.DARKBLUE);

		sensorLine3 = new Line();
		sensorLine3.setStartX(car.getCenterX());
		sensorLine3.setStartY(car.getCenterY());
		sensorLine3.setEndX(car.sensor3.getX());
		sensorLine3.setEndY(car.sensor3.getY());
		sensorLine3.startXProperty().bind(car.centerXProperty());
		sensorLine3.startYProperty().bind(car.centerYProperty());
		sensorLine3.setStroke(Color.DARKGREEN);

		sensorLine1.setVisible(true);
		sensorLine2.setVisible(true);
		sensorLine3.setVisible(true);

		canvasPane.getChildren().addAll(sensorLine1, sensorLine2, sensorLine3);

	}

	public void inputFileChoose(String[] args, Button loadFile) throws IOException {
		/*
		 * show a file stage for choose file
		 */

		Stage fileStage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(new File("src/datasetWithoutPosition"));

		File file = fileChooser.showOpenDialog(fileStage);
		// System.out.println(file);

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);// 在br.ready反查輸入串流的狀況是否有資料

		loadFile.setText(file.getName());

		String txt;
		while ((txt = br.readLine()) != null) {
			/*
			 * If there is space before split(), it will cause the error So, we
			 * could to use trim() to remove the space at the beginning and the
			 * end. Then split the result, which doesn't include the space at
			 * the beginning and the end. "\\s+" would match any of space, as
			 * you don't have to consider the number of space in the string
			 */
			String[] token = txt.trim().split("\\s+");// <-----背起來
			// String[] token = txt.split(" ");//<-----original split
			double[] token2 = new double[token.length];// 宣告float[]

			try {
				for (int i = 0; i < token.length; i++) {
					token2[i] = Float.parseFloat(token[i]);
				} // 把token(string)轉乘token2(float)
				inputArray.add(token2);// 把txt裡面內容先切割過在都讀進array內
			} catch (NumberFormatException ex) {
				System.out.println("Sorry Error...");
			}
		}
		fr.close();// 關閉檔案
	}

	public void printArrayData(ArrayList<double[]> showArray) {

		for (int i = 0; i < showArray.size(); i++) {
			for (int j = 0; j < showArray.get(i).length; j++) {
				System.out.print(showArray.get(i)[j] + "\t");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	public static void main(String[] args) {
		launch(args);
	}

}
