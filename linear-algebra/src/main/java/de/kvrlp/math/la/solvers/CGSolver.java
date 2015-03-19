package de.kvrlp.math.la.solvers;

import de.kvrlp.math.la.LAFactory;
import de.kvrlp.math.la.Solver;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class CGSolver implements Solver {

	private int maxIterations = 100000;
	private double maxResiduum = 1.e-10;
	
	
	@Override
	public Vector solve(Matrix A, Vector b) {
		Vector p = b;
		Vector r = p;

		int n = A.getNumberOfRows();
		Vector x = LAFactory.newInstance().newVector(n, A.getProvider());
		
		double alpha = r.normp(2.);
		
		int iterationLength = Math.min(n, maxIterations);
		int neededIt = -1;
		
		for(int m=0; m<iterationLength; ++m) {
			if (alpha<maxResiduum) {
				neededIt = m;
				break;
			}
			Vector v = A.multiply(p);
			double vp = v.scalarProduct(p);
			double lambda = alpha/vp;
			x = x.add(p.multiply(lambda));
			r = r.subtract(v.multiply(lambda));
			double alpha1 = r.normp(2.);
			p = r.add(p.multiply(alpha1/alpha));
			alpha = alpha1;
		}
		if (neededIt<0) neededIt = iterationLength;
		
		return x;
	}

	@Override
	public Matrix solve(Matrix A, Matrix b) {
		throw new UnsupportedOperationException("Iterative solvers do not support matrix values as right hand sides.");
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	public double getMaxResiduum() {
		return maxResiduum;
	}

	public void setMaxResiduum(double maxResiduum) {
		this.maxResiduum = maxResiduum;
	}

}
