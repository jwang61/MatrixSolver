
public class Matrix {
	public double[][] matrix;
	public int size;

	public Matrix(int size) {
		this.size = size;
		matrix = new double[size][size];
	}

	public double cof(int row, int col) {
		if (size == 2) {
			return (row == col) ? matrix[1 - row][1 - col] : -1.0 * matrix[1 - row][1 - col];
		} else {
			Matrix newMat = new Matrix(size - 1);
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++) {
					if (i < row) {
						if (j < col)
							newMat.matrix[i][j] = this.matrix[i][j];
						else if (j > col)
							newMat.matrix[i][j - 1] = this.matrix[i][j];
					} else if (i > row) {
						if (j < col)
							newMat.matrix[i-1][j] = this.matrix[i][j];
						else if (j > col)
							newMat.matrix[i-1][j - 1] = this.matrix[i][j];
					}
				}
			return newMat.det();
		}
	}

	public double det() {
		if (size == 2) {
			return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
		} else {
			double det = 0;
			for (int i = 0; i < size; i++) {
				if (i % 2 == 0)
					det += matrix[0][i] * cof(0, i);
				else
					det -= matrix[0][i] * cof(0, i);
			}
			return det;
		}
	}
}
