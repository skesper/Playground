package de.kvrlp.math.la.solvers;

import de.kvrlp.math.la.LAFactory;
import de.kvrlp.math.la.Solver;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class NaiveSolver implements Solver {

	private int maxit = 5;
	private double eps = 0.01;
	
	@Override
	public Vector solve(Matrix A, Vector b) {
		
		int n = A.getNumberOfRows();
		Vector x = LAFactory.newInstance().newVector(n, A.getProvider());
		
		double lambda = 0.01;
		
		int it = 0;
		while(it < maxit) {
			for(int i=0;i<n;++i) {
				Vector r =b.subtract(A.multiply(x));
				double normr = r.norm(2);
				if (normr<eps) break;
				double aii = A.get(i,i);
				if (aii==0.) throw new RuntimeException("A(i,i)=0., i="+i);
				lambda = r.get(i)/aii;
				x.set(i, x.get(i)+lambda);
			}
			it++;
		}
		
		return x;
	}

	@Override
	public Matrix solve(Matrix A, Matrix b) {
		throw new UnsupportedOperationException("Iterative solvers do not support matrix values as right hand sides.");
	}

}
