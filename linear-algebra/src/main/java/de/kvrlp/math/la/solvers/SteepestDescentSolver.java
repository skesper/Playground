package de.kvrlp.math.la.solvers;

import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Solver;
import de.kvrlp.math.la.Vector;

public class SteepestDescentSolver implements Solver {

	private double eps = 1.e-2;
	
	public double getEps() {
		return eps;
	}

	public void setEps(double eps) {
		this.eps = eps;
	}

	@Override
	public Vector solve(Matrix A, Vector b) {
		Vector x = b.duplicate();
		Vector r = b.subtract(A.multiply(x));
		int n = 2*x.getNumberOfEntries();
		int i=0;
		double rn;
		
		do {
			rn = r.norm(2.);

			Vector ar = A.multiply(r);
			
			double lambda = rn*rn/ar.scalarProduct(r);
			
			if (rn<1.e-10) break;

			x = x.add(r.multiply(lambda));
			
			r = r.subtract(ar.multiply(lambda));
			
			++i;
			
		} while(rn>eps && i<n);
		
		return x;
	}

	@Override
	public Matrix solve(Matrix A, Matrix b) {
		// TODO Auto-generated method stub
		return null;
	}

}
