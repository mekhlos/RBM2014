package rbm;

import picture_processing.Picture;

public class RBM {

	private final double LEARNING_RATE;
	private double[] visibleBias;
	private double[] hiddenBias;
	private double[] visible;
	private double[] vCopy;
	private double[] hidden;
	private double[] hCopy;
	private double[][] weights;
	private double[][] wCopy;

	public RBM(int numVisible, int numHidden, double rate) {

		this.LEARNING_RATE = rate;
		visibleBias = new double[numVisible];
		visible = new double[numVisible];
		hiddenBias = new double[numHidden];
		hidden = new double[numHidden];
		weights = new double[numVisible][numHidden];

		RBMUtils.initializeWeights(weights);

	}

	public void train(double[][] trainingSet, int numEpochs) {

		double[] visibleB = new double[trainingSet.length];

		for (int i = 0; i < trainingSet.length; i++) {
			double p = RBMUtils.getP(trainingSet[i]);
			double x = p / (1 - p);
			visibleB[i] = Math.log(x);
		}

		for (int index = 0; index < numEpochs; index++) {

			for (int count = 0; count < trainingSet.length; count++) {

				RBMUtils.setValues(visible, trainingSet[count]);
				RBMUtils.setVBias(visibleBias, visibleB, count);

				RBMUtils.update0(visible, hidden, visibleBias, hiddenBias, weights);

				double[][] data = RBMUtils.computeData(visible, hidden, visibleBias, hiddenBias);

				RBMUtils.reconstruct(visible, hidden, visibleBias, hiddenBias, weights);

				RBMUtils.update1(visible, hidden, visibleBias, hiddenBias, weights);

				double[][] recon = RBMUtils.computeRecon(visible, hidden, visibleBias, hiddenBias);

				RBMUtils.updateWeights(weights, data, recon, LEARNING_RATE);

			}
		}

	}

	public void printVisible() {
		RBMUtils.printVisible(visible);

	}

	public Picture getVisible() {
		return RBMUtils.getVisible(visible);

	}

	public void randomHidden() {
		RBMUtils.randomHidden(hidden);
		RBMUtils.reconstruct(visible, hidden, visibleBias, hiddenBias, weights);
		printVisible();
	}

	public double getEnergy() {
		double e = RBMUtils.getEnergy(visible, visibleBias, hidden, hiddenBias, weights);
		return e;
	}

	public void printMovies() {
		RBMUtils.printMovies(weights);
	}

	public Picture featuresToPicture() {
		return RBMUtils.featuresToPicture(weights, visible, hidden);
	}

	public void saveValues() {
		vCopy = new double[visible.length];
		hCopy = new double[hidden.length];
		wCopy = new double[visible.length][hidden.length];
		
		RBMUtils.setValues(vCopy, visible);
		RBMUtils.setValues(hCopy, hidden);
		RBMUtils.setValues(wCopy, weights);
	}

	public void restoreValues() {
		if (vCopy != null && hCopy != null && wCopy != null) {
			visible = vCopy;
			hidden = hCopy;
			weights = wCopy;
		}
	}

}


