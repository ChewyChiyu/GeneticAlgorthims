public class Matrix{
	private double[][] m;

	public Matrix(int row, int col){
		m = new double[row][col];
	}

	public Matrix(double[][] matrix){
		this.m = matrix;
	}

	public Matrix(int row, int col, double[] data){
		m = new double[row][col];
		int p = 0;
		for(int r = 0; r < m.length; r++){
			for(int c = 0; c < m[0].length; c++){
				m[r][c] = data[p++];
			}
		}
	}

	public double[] toStreamArray(){
		double[] data = new double[m.length*m[0].length];
		int p = 0;
		for(int r = 0; r < m.length; r++){
			for(int c = 0; c < m[0].length; c++){
				data[p++] = m[r][c];
			}
		}
		return data;
	}

	public void randomize(double min, double max){
		for(int r = 0; r < m.length; r++){
			for(int c = 0; c < m[0].length; c++){
				m[r][c] = min + Math.random()*(max-min);
			}
		}
	}

	public static Matrix clone(Matrix matrix){
		Matrix clone = new Matrix(matrix.m.length,matrix.m[0].length);
		for(int r = 0; r < clone.m.length; r++){
			for(int c = 0; c < clone.m[0].length; c++){
				clone.m[r][c] = matrix.m[r][c];
			}
		}
		return clone;
	}

	public Matrix addBy(Matrix m2){
		Matrix add = clone(this);
		for(int r = 0; r < add.m.length; r++){
			for(int c = 0; c < add.m[0].length; c++){
				add.m[r][c]+=m2.m[r][c];
			}
		}
		return add;
	}

	public Matrix multiBy(Matrix m2){
		double[][] mTrans = new double[m.length][m2.m[0].length];
		for(int r = 0; r < mTrans.length; r++){
			for(int c = 0; c < mTrans[0].length; c++){
				double product = dotProduct(getCol(c,m2),getRow(r,this));
				mTrans[r][c] = product;
			}
		}
		return new Matrix(mTrans);
	}

	private double dotProduct(double[] a, double[] b){
		double product = 0;
		for(int index = 0; index < a.length; index++){
			product+= (a[index]*b[index]);
		}
		return product;
	}
	public static double[] getCol(int col, Matrix matrix){
		double[] c = new double[matrix.m.length];
		for(int r = 0; r < matrix.m.length; r++){
			c[r] = matrix.m[r][col];
		}
		return c;
	}
	public static double[] getRow(int row, Matrix matrix){
		double[] r = new double[matrix.m[0].length];
		for(int c = 0; c < matrix.m[0].length; c++){
			r[c] = matrix.m[row][c];
		}
		return r;
	}

	public Matrix sigmoidActivation(){
		Matrix act = clone(this);
		for(int r = 0; r < act.m.length; r++){
			for(int c = 0; c < act.m[0].length; c++){
				System.out.println(act.m[r][c]);
				act.m[r][c] = sigmoid(act.m[r][c]);
				System.out.println(act.m[r][c]);
			}
		}
		return act;
	}


	private double sigmoid(double i){
		return (double) (1/(1 + Math.pow(Math.E, -i)));
	}



	public String toString(){
		StringBuilder sb = new StringBuilder();
		double[] data = toStreamArray();
		sb.append("[");
		for(double d : data)
			sb.append(d + " ");
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}

}