package de.kvrlp.math.la.newton;

import de.kvrlp.math.la.Function;
import de.kvrlp.math.la.iteration.IterationResult;

public class Newton {
	
	private double eps = 1.e-10;
	
	public Newton() {
	}
	
	public Newton(double errorBound) {
		eps = errorBound;
	}

	public double findZero(Function f, double start, int maxit) {
		
		double x = start;
		double x0 = x;
		
		for(int i=0;i<maxit;++i) {
			x = x - f.value(x)/f.valueFirstDerivative(x);
			if (Math.abs(x-x0)<eps) break;
			x0 = x;
		}
		
		return x;
	}

	public void findZero(Function f, double start, int maxit, IterationResult<Double> result) {
		
		double x = start;
		double x0 = x;
		
		for(int i=0;i<maxit;++i) {
			x = x - f.value(x)/f.valueFirstDerivative(x);
			if (Math.abs(x-x0)<1.e-10) break;
			x0 = x;
		}
	}
	
	public static void main(String[] args) {
		Newton n = new Newton();
		
		for(double p=2.;p<10.;p=p+1.) {
			double zero = n.findZero(new RootFunction(p, 2.), 2., 100);
			System.out.println("DEBUG: erg = "+zero+" --> "+Math.pow(zero, p));
		}
		
	}
}
