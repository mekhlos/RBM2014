package movies.p1;

public class RBM {

	private int numVisible;
	private int numHidden;
	private double LEARNING_RATE;
	private double[][] weights;

	public RBM(int numVisible, int numHidden, double learningRate) {
		this.numVisible = numVisible;
		this.numHidden = numHidden;
		this.LEARNING_RATE = learningRate;
		weights = new double[numVisible][numHidden];

		// LEARNINING_RATE * random
		weights = Utils.initializeWeights(weights, LEARNING_RATE);
		weights = Utils.insert(weights, 0, 0, axes.ROW);
		weights = Utils.insert(weights, 0, 0, axes.COLUMN);

	}

	public void train(double[][] data, int epoch) {

		int numExamples = data.length;

		data = Utils.insert(data, 1, 0, axes.COLUMN);

		for (int count = 0; count < epoch; count++) {

			double[][] posHiddenActivations = Utils.dotProduct(data, weights);	
			double[][] posHiddenProbs = Utils.computeSigmoid(posHiddenActivations);	
			double[][] posHiddenStates = Utils.setHiddenStates(posHiddenProbs, numExamples, numHidden + 1);
			double[][] posAssociations = Utils.dotProduct(Utils.transpose(data), posHiddenProbs);
			
			double[][] negVisibleActivations = Utils.dotProduct(posHiddenStates, Utils.transpose(weights));	
			double[][] negVisibleProbs = Utils.computeSigmoid(negVisibleActivations);	

			negVisibleProbs = Utils.fixBias(negVisibleProbs);	

			double[][] negHiddenActivations = Utils.dotProduct(negVisibleProbs, weights);
			double[][] negHiddenProbs = Utils.computeSigmoid(negHiddenActivations);			
			double[][] negAssociations = Utils.dotProduct(Utils.transpose(negVisibleProbs), negHiddenProbs);

			weights = Utils.updateWeights(weights, LEARNING_RATE, posAssociations, negAssociations, numExamples);
		}

		String[] movies = {"Bias        ", "Harry Potter", "Avatar      ",
				"LOTR 3      ", "Gladiator   ", "Titanic      ", "Glitter     "};

		StringBuilder kaki = new StringBuilder();
		for (int i = 0; i < weights.length; i++) {

			kaki.append(movies[i] + "\t\t");
			for (int j = 0; j < weights[i].length; j++) {
				kaki.append(weights[i][j] + "\t");
			}
			kaki.append("\n");
		}

		System.out.println(kaki.toString());

		for (int i = 0; i < weights.length; i++) {
			System.out.print(movies[i] + "\t\t");
			for (int j = 0; j < weights[i].length; j++) {
				System.out.print((weights[i][j] > 0) + "\t");
			}
			System.out.println();
		}
	}

	public double[][] getResult() {

		return weights;
	}
}
