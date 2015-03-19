package de.kvrlp.math.la.solvers;

import java.util.ArrayList;

import de.kvrlp.math.la.LAFactory;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Solver;
import de.kvrlp.math.la.Vector;

public class TFQMRSolver implements Solver {

	private double eps = 1.e-10;
	
	@Override
	public Vector solve(Matrix A, Vector b) {
		Vector x = LAFactory.newInstance().newVector(A.getNumberOfColumns(), A.getProvider());
		 
		Vector r = b.subtract(A.multiply(x));
		Vector y = r.duplicate();
		double tau = r.norm(2.);
		Vector r0 = r.duplicate();
		
		Vector v = A.multiply(y);
		Vector d = LAFactory.newInstance().newVector(A.getNumberOfRows(), A.getProvider());
		double eta = 0.;
		double nu = 0.;
		
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public Matrix solve(Matrix A, Matrix b) {
		throw new UnsupportedOperationException("Iterative solvers do not support matrix values as right hand sides.");
	}

}
