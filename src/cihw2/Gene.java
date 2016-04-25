package cihw2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Gene {
	private int neuronNumber = 9;
	private ArrayList<double[]> inputArray;
	
	private double[] weight;
	private ArrayList<double[]> m;
	private double[] sigma;
	private ArrayList<double[]> infoArray;
	private int fi0 = 1;
	
	public Gene(ArrayList<double []> input){
		this.inputArray = new ArrayList<double[]>();
		this.inputArray = input;
		this.infoArray = new ArrayList<double[]>();
				
		this.weight = new double[neuronNumber];
		
		this.m = new ArrayList<double[]>();
		
		this.sigma = new double[neuronNumber];
		
		for(int i=0;i<neuronNumber;i++){
			weight[i] = Math.random();
			double[] tempM = null;
			for(int j=0;j<3;j++){
				tempM[j] = Math.random()*30;
			}
			m.add(tempM);
			sigma[i] = Math.random()*10;
		}	
		
	}
	
	public ArrayList<double[]> getGeneInfo(){
		return this.infoArray;
	}
	
}
