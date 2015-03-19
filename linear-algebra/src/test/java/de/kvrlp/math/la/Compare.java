package de.kvrlp.math.la;

import de.kvrlp.math.la.solvers.BiCGStabSolver;
import de.kvrlp.math.la.solvers.CGSolver;
import de.kvrlp.math.la.solvers.GaussSeidelSolver;
import de.kvrlp.math.la.solvers.GivensSolver;
import de.kvrlp.math.la.solvers.NaiveSolver;
import de.kvrlp.math.la.solvers.SteepestDescentSolver;

public class Compare {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int size = 200;
		
		
//		LAProvider provider = LAProvider.SPARSE_ITEM;
//		Matrix m = LAFactory.newInstance().newMatrix(size, size, provider);
//		Vector b = LAFactory.newInstance().newVector(size, provider);
//		for(int i=0;i<size;++i) {
//			m.set(i,i, -4.);
//			if (i>0) {
//				m.set(i-1, i, 1.);
//				m.set(i, i-1, 1.);
//			}
//			if (i>3) {
//				m.set(i-4, i, 1.);
//				m.set(i, i-4, 1.);
//			}
//		}
//		b.set(0,10.);
		
		LAProvider provider = LAProvider.SPARSE_ITEM;
		Matrix m = LAFactory.newInstance().newMatrix(size, size, provider);
		Vector b = LAFactory.newInstance().newVector(size, provider);
		for(int i=0;i<size;++i) {
			b.set(i, 1./((double)(1.+i)));
			m.set(i,i, 10./(i+1.));
			for(int j=0;j<size;++j) {
				if (i!=j) {
					if (i<j) {
						m.set(i,j, 1./((double)((1.+i)*(1.+j))));
					} else {
						m.set(i,j, -2./((double)((1.+i)*(1.+j))));
					}
				}
			}
		}
		
//		LAProvider provider = LAProvider.SPARSE_ITEM;
//		Matrix m = LAFactory.newInstance().newMatrix(size, size, provider);
//		Vector b = LAFactory.newInstance().newVector(size, provider);
//		for(int i=0;i<size;++i) {
//			b.set(i, 1./((double)(1.+i)));
//			m.set(i,i, 10./(i+1.));
//			for(int j=0;j<size;++j) {
//				if (i!=j) {
//					m.set(i,j, 1./((double)((1.+i)*(1.+j))));
//				}
//			}
//		}
		
		
//		Solver[] solvers = new Solver[]{new CGSolver(), new BiCGStabSolver(), new SteepestDescentSolver(), new GaussSeidelSolver(), new NaiveSolver()};
		Solver[] solvers = new Solver[]{new CGSolver(), new BiCGStabSolver(), new SteepestDescentSolver(), new NaiveSolver()};

//		Matrix At = m.transpose();
//		Matrix AtA = At.multiply(m);
//		Vector Atb = At.multiply(b);
		
		for(Solver s : solvers) {
			long start = System.currentTimeMillis();
			Vector x = s.solve(m, b);
			long end = System.currentTimeMillis();
			
			Vector res = m.multiply(x).subtract(b);
			double r = res.norm(2.);
			double xn = x.norm(2.);
			
			double rrel = r/xn;
			
			System.out.printf("Ax=b     : %-25s : %10d ms, res: %20.18f, rel: %20.18f\n", s.getClass().getSimpleName(), (end-start), r, rrel);

//			start = System.currentTimeMillis();
//			x = s.solve(AtA, Atb);
//			end = System.currentTimeMillis();
//			
//			res = m.multiply(x).subtract(b);
//			r = res.norm(2.);
//			xn = x.norm(2.);
//			
//			rrel = r/xn;
//			
//			System.out.printf("AtAx=Atb : %-25s : %10d ms, res: %20.18f, rel: %20.18f\n", s.getClass().getSimpleName(), (end-start), r, rrel);
		}
		
	}

}
