package movies.p1;

public class Matrix {
	
	private double[][] matrix;
	private int numOfRows;
	private int numOfColumns;
	
	public Matrix (int x, int y) {
		matrix = new double[x][y];	
		numOfRows = x;
		numOfColumns = y;
	}
	
	public int getRows() {
		return numOfRows;
	}
	
	public int getColumns() {
		return numOfColumns;
	}
	
	public Matrix (double[][] matrix) {
		this.matrix = matrix;
		assert matrix != null;
		numOfRows = matrix.length;
		numOfColumns = matrix[0].length;
	}

	public void computeSigmoid() {
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				matrix[i][j] = 1 / (1 + Math.exp(matrix[i][j]));
			}
		}		
	}
	

	public void transpose() {
		double[][] result = new double[numOfColumns][numOfRows];
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				result[j][i] = matrix[i][j];
			}
		}			
		matrix = result;
	}
	
	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				result += matrix[i][j] + " ";
			}
			result += "\n";			
		}
		return result;
	}
}
