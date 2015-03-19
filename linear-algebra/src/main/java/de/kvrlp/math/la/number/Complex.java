package de.kvrlp.math.la.number;

public class Complex implements Operatable<ComplexValue> {

	private final ComplexValue cv;
	
	public Complex(ComplexValue cv) {
		this.cv = cv;
	}

	public Complex(double re, double im) {
		this.cv = new ComplexValue(re, im);
	}
	
	@Override
	public ComplexValue add(ComplexValue a) {
		return new ComplexValue(cv.re+a.re, cv.im+a.im);
	}

	@Override
	public ComplexValue subtract(ComplexValue a) {
		return new ComplexValue(cv.re-a.re, cv.im-a.im);
	}

	@Override
	public ComplexValue multiply(ComplexValue a) {
		return new ComplexValue(cv.re*a.re-cv.im*a.im, cv.re*a.im+cv.im*a.re);
	}

	@Override
	public ComplexValue divide(ComplexValue a) {
		double n = (a.re*a.re+a.im*a.im);
		return new ComplexValue((cv.re*a.re+cv.im*a.im)/n,(cv.im*a.re-cv.re*a.im)/n);
	}

}
