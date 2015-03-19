package de.kvrlp.math.la;

public interface Solver {
	
	/**
	 * Calculates the solution to the problem 
	 * Ax=b
	 * 
	 * @param A The matrix
	 * @param b The right hand side
	 * @return The solution to Ax=b
	 */
	Vector solve(Matrix A, Vector b);

	/**
	 * Simultaneously solves several problems of the type Ax=b, which means
	 * each column of matrix b is a problem of that form. Hence each colum 
	 * of the result matrix x is the solution to the Ax=b problem of the corresponding
	 * column in b. This interface method will only be supported by direct solvers. 
	 * For iterative solvers need to restart the iteration for each vector.
	 * @param A the matrix
	 * @param b the right hand sides
	 * @return the solution to all right hand sides
	 */
	Matrix solve(Matrix A, Matrix b);
}
