package de.kvrlp.math.la;

import de.kvrlp.math.la.number.ComplexNumber;
import de.kvrlp.math.la.solvers.BiCGStabSolver;
import de.kvrlp.math.la.solvers.CGSolver;
import de.kvrlp.math.la.solvers.GaussSeidelSolver;
import de.kvrlp.math.la.solvers.GivensSolver;
import de.kvrlp.math.la.solvers.SteepestDescentSolver;
import de.kvrlp.math.la.solvers.NaiveSolver;
import de.kvrlp.math.la.solvers.StabBiCGStabSolver;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int size = 1000;
		
		
		LAProvider provider = LAProvider.SPARSE;
//		for(LAProvider provider : LAProvider.values()) {
		
		Matrix m = LAFactory.newInstance().newMatrix(size, size, provider);
		Vector b = LAFactory.newInstance().newVector(size, provider);
		
//		for(int i=0;i<size;++i) {
//			b.set(i, 1./((double)(1.+i)));
//			for(int j=0;j<size;++j) {
//				if (i<j) {
//					m.set(i,j, 1./((double)(1.+i*j)));
//				} else if (i>j) {
//					m.set(i,j, 1./((double)(1.+j)));
//				} else {
//					m.set(i,i, 10./(i+1.));
//				}
//			}
//		}
		
		for(int i=0;i<size;++i) {
			m.set(i,i, -4.);
			if (i>0) {
				m.set(i-1, i, 1.);
				m.set(i, i-1, 1.);
			}
			
//			if (i>1) {
//				m.set(i-2, i, 1.);
//				m.set(i, i-2, 1.);
//			}
		}
		b.set(0,10.);
		
//		System.out.println("Starting decomposition...");
//		long start = System.currentTimeMillis();
//		Vector x = m.solve(b, LinearAlgebraSolver.LU_CHOLESKY_DECOMPOSITOR);
//		long end = System.currentTimeMillis();
//		System.out.println("Decomposition needed: "+(end-start)+" ms");
//		
//		
//		Vector b2 = m.multiply(x);
//		
//		for(int i=0;i<10;++i) {
//			System.out.println(b.get(i)+" =?= "+b2.get(i));
//		}
//		
//		
//		Vector dif = b2.subtract(b);
//		System.out.println("Distance: b-b2 = "+dif.norm(2));
		
//		CGSolver solver = new CGSolver();
//		solver.setMaxResiduum(1.e-10);
		
//		BiCGStabSolver solver = new BiCGStabSolver();
//		solver.setEps(1.e-2);

//		GivensSolver solver = new GivensSolver();
		
//		NaiveSolver solver = new NaiveSolver();
		
//		GaussSeidelSolver solver = new GaussSeidelSolver();
		
		SteepestDescentSolver solver = new SteepestDescentSolver();
		
		long start = System.currentTimeMillis();
		Vector x = solver.solve(m,b);
		long end = System.currentTimeMillis();
		System.out.printf("%-15s needed %5d ms.\n", provider.name(), (end-start));
		Vector b2 = m.multiply(x);
		for(int i=0;i<10;++i) {
			System.out.println("  "+x.get(i)+" -> "+b.get(i)+" = "+b2.get(i));
		}
//		}
	}

}
