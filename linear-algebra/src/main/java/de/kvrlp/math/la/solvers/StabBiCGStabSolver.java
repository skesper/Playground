package de.kvrlp.math.la.solvers;

import de.kvrlp.math.la.LAFactory;
import de.kvrlp.math.la.Solver;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class StabBiCGStabSolver implements Solver {

	private double eps1 = 1.e-20, eps2 = 1.e-20;
	private int maxRestarts = 100;
	
	@Override
	public Vector solve(Matrix A, Vector b) {
		Vector x = LAFactory.newInstance().newVector(A.getNumberOfRows(), A.getProvider());
		
		exit: {
			
			for(int restarts=0; restarts<maxRestarts; ++restarts) restartLoop: {
				Vector r = b.subtract(A.multiply(x));
				Vector r0 = r;
				Vector p = r;
	
				double normr = r.norm(2.);
				double normr0 = normr;
				double rho = r.scalarProduct(r);
		
				while(normr>eps1) {
					
					if (normr<eps1) {
						System.out.println("BREAK TO EXIT!");
						break exit;
					}
					
					Vector vj = A.multiply(p);
	
					double sigmaj = vj.scalarProduct(p);
					double normvj = vj.norm(2.);
					
					if (sigmaj > eps2*normvj*normr0) {
						
						double alphaj = rho/sigmaj;
						
						Vector sj = r.subtract(vj.multiply(alphaj));
						
						double normsj = sj.norm(2.);
						
						if (normsj>eps1) {
							Vector tj = A.multiply(sj);
							double omegaj = tj.scalarProduct(sj) / tj.scalarProduct(tj);
							
							x = x.add(p.multiply(alphaj)).add(sj.multiply(omegaj));
							r = sj.subtract(tj.multiply(omegaj));
							double rhojp1 = r.scalarProduct(r0);
							double betaj = alphaj*rhojp1/(omegaj*rho);
							p = r.add(p.subtract(vj.multiply(omegaj)).multiply(betaj));
							
							rho = rhojp1;
						} else {
							x = x.add(p.multiply(alphaj));
							r = sj;
						}
						normr = r.norm(2.);
					} else {
						System.out.println("RESTART!");
						break restartLoop;
					}
					
				}
			}
		}
		
		return x;
	}

	@Override
	public Matrix solve(Matrix A, Matrix b) {
		throw new UnsupportedOperationException("Iterative solvers do not support matrix values as right hand sides.");
	}

	public double getEps1() {
		return eps1;
	}

	public void setEps1(double eps1) {
		this.eps1 = eps1;
	}

	public double getEps2() {
		return eps2;
	}

	public void setEps2(double eps2) {
		this.eps2 = eps2;
	}

	public int getMaxRestarts() {
		return maxRestarts;
	}

	public void setMaxRestarts(int maxRestarts) {
		this.maxRestarts = maxRestarts;
	}

}
