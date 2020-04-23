public class FeedForward{


	final static double DELTA_MUTATION_SHIFT = 0.1; 

	public int inputNodes; //only one layer
    public Matrix inputToHiddenWeight;

	public int hiddenLayers; //number of layers
	public int hiddenNodes; //nodes per layer (shared)
    public Matrix[] hiddenToHiddenWeights;
    public Matrix[] hiddenBiases;


    public int outputNodes; //only one layer
	public Matrix hiddenToOutput;
	public Matrix outputBias;

	public FeedForward(int inputNodes, int hiddenLayers, int hiddenNodes, int outputNodes){

		this.inputNodes = inputNodes;
		inputToHiddenWeight = new Matrix(hiddenNodes,inputNodes);
		inputToHiddenWeight.randomize(-1,1);

		this.hiddenLayers = hiddenLayers;
		this.hiddenNodes = hiddenNodes;
		hiddenToHiddenWeights = new Matrix[hiddenLayers];
        hiddenBiases = new Matrix[hiddenLayers];

		for(int index = 0; index < hiddenToHiddenWeights.length; index++){
			
			hiddenToHiddenWeights[index] = new Matrix(hiddenNodes,hiddenNodes);
			hiddenToHiddenWeights[index].randomize(-1,1);

			hiddenBiases[index] = new Matrix(hiddenNodes,1);
			hiddenBiases[index].randomize(-1,1);

		}


		this.outputNodes = outputNodes;
		hiddenToOutput = new Matrix(outputNodes,hiddenNodes);
		hiddenToOutput.randomize(-1,1);

		outputBias = new Matrix(hiddenNodes,1);
		outputBias.randomize(-1,1);
	}

	public double[] predict(double[] inputs){


		//input to first hidden layer
		Matrix input = new Matrix(inputs.length,1,inputs);

		Matrix hidden = inputToHiddenWeight.multiBy(input);
		hidden = hidden.addBy(hiddenBiases[0]);
		hidden = hidden.sigmoidActivation();

		//hidden layer to hidden layers, if more than one
		for(int index = 0; index < hiddenLayers && hiddenLayers > 1; index++){
			Matrix nextHidden = hiddenToHiddenWeights[index].multiBy(hidden);
			nextHidden = nextHidden.addBy(hiddenBiases[index]);
			nextHidden = nextHidden.sigmoidActivation();
			hidden = nextHidden;
		}

		//hidden layer to output layer
		Matrix output = hiddenToOutput.multiBy(hidden);
		output = output.addBy(outputBias);
		output = output.sigmoidActivation();

		return output.toStreamArray();

	}

	public FeedForward(FeedForward clone, double mutationRate){

		inputNodes = clone.inputNodes; 
		hiddenLayers = clone.hiddenLayers; 
		hiddenNodes = clone.hiddenLayers;
		hiddenToHiddenWeights = new Matrix[hiddenLayers];
		hiddenBiases = new Matrix[hiddenLayers];
		outputNodes = clone.outputNodes; 

		// mutated = Math.random()<mutationRate;

		// if(mutated){
			inputToHiddenWeight = mutate(clone.inputToHiddenWeight,mutationRate);

			for(int index = 0; index < clone.hiddenLayers; index++){
				hiddenToHiddenWeights[index] = mutate(clone.hiddenToHiddenWeights[index],mutationRate);
				hiddenBiases[index] = mutate(clone.hiddenBiases[index],mutationRate);
			}
    	
			hiddenToOutput = mutate(clone.hiddenToOutput,mutationRate);
			outputBias = mutate(clone.outputBias,mutationRate);
		// }else{

		// 	inputToHiddenWeight = Matrix.clone(clone.inputToHiddenWeight);

		// 	for(int index = 0; index < clone.hiddenLayers; index++){
		// 		hiddenToHiddenWeights[index] =Matrix.clone(clone.hiddenToHiddenWeights[index]);
		// 		hiddenBiases[index] = Matrix.clone(clone.hiddenBiases[index]);
		// 	}
    	
		// 	hiddenToOutput = Matrix.clone(clone.hiddenToOutput);
		// 	outputBias = Matrix.clone(clone.outputBias);
		// }
	}

	public Matrix mutate(Matrix layer, double mutationRate){
		Matrix mut = Matrix.clone(layer);
		for(int r = 0; r < mut.m.length; r++){
			for(int c = 0; c < mut.m[0].length; c++){
				boolean mutate = (Math.random() < mutationRate);
				boolean negativeShift = (Math.random() > .5);
				if(mutate) mut.m[r][c] += (negativeShift)?-FeedForward.DELTA_MUTATION_SHIFT:FeedForward.DELTA_MUTATION_SHIFT;
			}
		}
		return mut;
	}

}