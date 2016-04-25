package cihw2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Gene {
	private int neuronNumber = 3;
	private double[] weight;
	private double[] fi;
	private double[] sigma;
	private ArrayList<double[]> mean;
	private ArrayList<double[]> infoArray;
	private int fi0 = 1;

	public Gene() {
		
		this.mean = new ArrayList<double[]>();
		this.weight = new double[neuronNumber+1];
		this.fi = new double[neuronNumber+1];
		this.sigma = new double[neuronNumber+1];

		for (int i = 0; i < neuronNumber; i++) {
			weight[i] = Math.random();
			double[] tempM = null;
			for (int j = 0; j < 3; j++) {
				tempM[j] = Math.random() * 30;
			}
			mean.add(tempM);
			sigma[i] = Math.random() * 10;
		}

	}

	public double calOutput(double[] distanceInput, double desire) {
		
		for (int i = 0; i < neuronNumber; i++) {
			if(i == 0){ // fi0 = 1
				fi[i] = 1;
			}
			else{
				fi[i] = calGaussianBasis(distanceInput, this.mean.get(i), this.sigma[i]);
			}
		}
		double output = 0;
		for(int i=0;i<fi.length;i++){
			output += fi[i]*weight[i];
		}
		
		return output;
	}
	
	public double calGaussianBasis(double[] x, double[] m2, double sigma2) {
		double returnValue = 0;
		double temp = 0;

		for (int i = 0; i < x.length; i++) {
			temp += Math.pow((x[i] - m2[i]), 2);
		}
		returnValue = Math.exp((-1 * temp) / (2 * sigma2 * sigma2));
		return returnValue;
	}

}
