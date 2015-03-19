package de.kvrlp.math.la.solvers;

import de.kvrlp.math.la.LAFactory;
import de.kvrlp.math.la.Solver;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class GivensSolver implements Solver {
	private Matrix a = null;
	
	@Override
	public Vector solve(Matrix A, Vector b) {
		int n = A.getNumberOfRows();
		int nm1 = n-1;
		int np1 = n+1;
		
		Matrix a = LAFactory.newInstance().newMatrix(n, np1, A.getProvider());
		
		for(int i=0;i<n;++i) {
			for(int j=0;j<n;++j) {
				a.set(i, j, A.get(i,j));
			}
			a.set(i, n, b.get(i));
		}
		
		for(int i=0;i<nm1;++i) {
			for(int j=i+1;j<n;++j) {
				double aii = a.get(i,i);
				double aii2 = aii*aii;
				double aij = a.get(i,j);
				if (aij!=0.) {
					double t = 1./Math.sqrt(aii2+aij*aij);
					double s = t*aij;
					double c = t*aii;
					for(int k=i;k<np1;++k) {
						t = c*a.get(i,k)+s*a.get(j,k);
						if (k==i) {
							a.set(i, k, t);
						} else {
							a.set(j, k, -s*a.get(i,k)+c*a.get(j,k));
						}
						a.set(j, i, 0.);
					}
				}
			}
		}
		
		Vector x = LAFactory.newInstance().newVector(n, A.getProvider());
		for(int i=n-1;i>=0;--i) {
			for(int j=i+1;j<n;++j) {
				a.set(i, n, a.get(i, n)-a.get(i, i)*x.get(j));
			}
			x.set(i, a.get(i, n)/a.get(i,i));
		}
		
		return x;
	}

	@Override
	public Matrix solve(Matrix A, Matrix b) {
		throw new RuntimeException("Not implemented yet!");
	}

}
