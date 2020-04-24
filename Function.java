public class Function{


	public static final int SIGMOID_MIN_DOMAIN = -5;
	public static final int SIGMOID_MAX_DOMAIN = 5;
	
	public static final double SIGMOID(double i){
		return (double) (1/(1 + Math.pow(Math.E, -i)));
	}


}