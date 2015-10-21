package movies.p1;

import java.util.Random;

public class Utils {
	
	public static Random rand = new Random();

	
	public static double[][] dotProduct(double[][] m, double[][] n) {
		
		double[][] result = new double[m.length][n[0].length];
		//System.out.println(m.length + " " + n[0].length);
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < n[0].length; j++) {
				for (int k = 0; k < m[0].length; k++) {
					result[i][j] += m[i][k] * n[k][j];
				}
			}
		}
		return result;
	}
	
	public static double[][] computeSigmoid(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = 1 / (1 + Math.exp(-matrix[i][j]));
			}
		}
		return matrix;		
	}
	
	public static double[][] transpose(double[][] matrix) {
		double[][] result = new double[matrix[0].length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				result[j][i] = matrix[i][j];
			}
		}			
		return result;
	}
	
	public static String print(double[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				sb.append(matrix[i][j] + " ");
			}
			sb.append("\n");			
		}
		return sb.toString();
	}

	public static double[][] initializeWeights(double[][] weights, 
			double rate) {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[0].length; j++) {
				weights[i][j] = rand.nextDouble() * rate;
			}
		}
		return weights;
	}

	public static double[][] insert(double[][] weights, int value, int n, 
			axes axes) {
		
		double[][] result;
			

		switch (axes) {
		case ROW: 
			result = new double[weights.length + 1][weights[0].length];
			for (int i = 0; i < result.length; i++) {

				for (int j = 0; j < weights[0].length; j++) {
					if (i < n) {
						result[i][j] = weights[i][j];
					} else if (i == n) {
						result[i][j] = value;
					} else {
						result[i][j] = weights[i - 1][j]; 
					}					
				}
			}
			return result;
		case COLUMN:
			result = new double[weights.length][weights[0].length + 1];
			for (int j = 0; j < result[0].length; j++) {
				for (int i = 0; i < weights.length; i++) {
					if (j < n) {
						result[i][j] = weights[i][j];
					} else if (j == n) {
						result[i][j] = value;
					} else {
						result[i][j] = weights[i][j - 1]; 
					}					
				}
			}
			return result;
		default: return null;
		}
	
	}

	public static double[][] setHiddenStates(double[][] probs, int rows, int columns) {
		double[][] result = new double[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (probs[i][j] > Math.random()) {
					result[i][j] = 1;
				} else {
					result[i][j] = 0;
				}
			}
		}
		return result;
	}

	public static double[][] fixBias(double[][] probs) {
		for (int i = 0; i < probs.length; i++) {
			probs[i][0] = 1;
		}
		return probs;
	}

	public static double[][] updateWeights(double[][] weights,
			double rate, double[][] pos, double[][] neg, int num) {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[0].length; j++) {
				weights[i][j] += rate * ((pos[i][j] - neg[i][j]) / num);
			}
		}
		return weights;
	}

}
