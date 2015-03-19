package de.kvrlp.math.la.solvers;

import de.kvrlp.math.la.LAFactory;
import de.kvrlp.math.la.Solver;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class BiCGStabSolver implements Solver {

	private double eps = 1.e-12;
	
	
	@Override
	public Vector solve(Matrix A, Vector b) {
		Vector x = LAFactory.newInstance().newVector(A.getNumberOfColumns(), A.getProvider());
	
		Vector r = b.subtract(A.multiply(x));
		Vector r0 = r;
		Vector p = r;
		
		double rho = r.scalarProduct(r);
		
		double normr = r.norm(2.);
		
		while(normr>eps) {
			Vector v = A.multiply(p);
			double alpha = rho / v.scalarProduct(r0);
			
			Vector s = r.subtract(v.multiply(alpha));
			Vector t = A.multiply(s);
			
			double omega = t.scalarProduct(s) / t.scalarProduct(t);
			
			x = x.add(p.multiply(alpha)).add(s.multiply(omega));
			
			r = s.subtract(t.multiply(omega));
			
			double rhop1 = r.scalarProduct(r0);
			
			double beta = alpha*rhop1/(omega*rho);
			
			p = r.add(p.subtract(v.multiply(omega)).multiply(beta));
			
			rho = rhop1;
			
			normr = r.norm(2.);
		}
		
		return x;
	}

	@Override
	public Matrix solve(Matrix A, Matrix b) {
		throw new UnsupportedOperationException("Iterative solvers do not support matrix values as right hand sides.");
	}

	public double getEps() {
		return eps;
	}

	public void setEps(double eps) {
		this.eps = eps;
	}

}
