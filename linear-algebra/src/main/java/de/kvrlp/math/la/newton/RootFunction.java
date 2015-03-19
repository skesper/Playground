package de.kvrlp.math.la.newton;

import de.kvrlp.math.la.Function;

public class RootFunction implements Function {

	private final double pth;
	private final double rootOf;
	
	public RootFunction(double pth, double rootOf) {
		this.pth = pth;
		this.rootOf = rootOf;
	}
	
	@Override
	public double value(double x) {
		return Math.pow(x, pth)-rootOf;
	}

	@Override
	public double valueFirstDerivative(double x) {
		if (pth==1.) {
			return pth;
		} else if (pth==2.) {
			return pth*x;
		}
		return pth*Math.pow(x, pth-1);
	}

}
