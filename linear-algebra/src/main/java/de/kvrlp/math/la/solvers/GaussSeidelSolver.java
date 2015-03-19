package de.kvrlp.math.la.solvers;

import de.kvrlp.math.la.LAFactory;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Solver;
import de.kvrlp.math.la.Vector;

public class GaussSeidelSolver implements Solver {

	private int maxit=10000;
	private double eps = 1.e-5;
	
	@Override
	public Vector solve(Matrix A, Vector b) {
		
		Vector x = LAFactory.newInstance().newVector(b.getNumberOfEntries(), b.getProvider());
		int n = x.getNumberOfEntries();
		int cnt = 0;
		while(cnt<maxit) {
			
			for(int i=0;i<n;++i) {
				double s = b.get(i);
				for(int j=0;j<n;++j) {
					if (j==i) continue;
					s -= A.get(i, j)*x.get(j);
				}
				
				s = s/A.get(i,i);
				
				x.set(i, s);
			}
			cnt++;
			
			Vector r = A.multiply(x).subtract(b);
			double res = r.norm(2.);
//			System.out.println("res: "+res);
			if (res<eps) break;
		}
		return x;
	}

	@Override
	public Matrix solve(Matrix A, Matrix b) {
		throw new UnsupportedOperationException("Iterative solvers do not support matrix values as right hand sides.");
	}

}
