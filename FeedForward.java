public class FeedForward{



	int inputNodes; //only one layer
    Matrix inputToHiddenWeight;

	int hiddenLayers; //number of layers
	int hiddenNodes; //nodes per layer (shared)
    Matrix[] hiddenToHiddenWeights;
    Matrix[] hiddenBiases;


    int outputNodes; //only one layer
	Matrix hiddenToOutput;
	Matrix outputBias;


	
	

	


	public FeedForward(int inputNodes, int hiddenLayers, int hiddenNodes, int outputNodes){
		
		this.inputNodes = inputNodes;
		inputToHiddenWeight = new Matrix(hiddenNodes,inputNodes);
		inputToHiddenWeight.randomize(0,1);



		this.hiddenLayers = hiddenLayers;
		this.hiddenNodes = hiddenNodes;
		hiddenToHiddenWeights = new Matrix[hiddenLayers];
        hiddenBiases = new Matrix[hiddenLayers];

		for(int index = 0; index < hiddenToHiddenWeights.length; index++){
			
			hiddenToHiddenWeights[index] = new Matrix(hiddenNodes,hiddenNodes);
			hiddenToHiddenWeights[index].randomize(0,1);

			hiddenBiases[index] = new Matrix(hiddenNodes,1);
			hiddenBiases[index].randomize(0,1);

		}


		this.outputNodes = outputNodes;
		hiddenToOutput = new Matrix(outputNodes,hiddenNodes);
		hiddenToOutput.randomize(0,1);

		outputBias = new Matrix(hiddenNodes,1);
		outputBias.randomize(0,1);

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
		hiddenToOutput = hiddenToOutput.multiBy(hidden);
		hiddenToOutput = hiddenToOutput.addBy(outputBias);
		hiddenToOutput = hiddenToOutput.sigmoidActivation();

		return hiddenToOutput.toStreamArray();

	}


}