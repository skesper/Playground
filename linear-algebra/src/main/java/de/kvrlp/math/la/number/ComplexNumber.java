package de.kvrlp.math.la.number;

import java.io.Serializable;

public class ComplexNumber implements Serializable {

	private static final long serialVersionUID = -3254336492636374618L;
	
	private final double re;
	private final double im;
	
	public ComplexNumber(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	public ComplexNumber multiply(ComplexNumber a) {
		return new ComplexNumber(re*a.re-im*a.im, re*a.im+im*a.re);
	}
	
	public ComplexNumber multiply(double a) {
		return new ComplexNumber(re*a, im*a);
	}
	
	public ComplexNumber divide(ComplexNumber a) {
		double n = (a.re*a.re+a.im*a.im);
		return new ComplexNumber((re*a.re+im*a.im)/n,(im*a.re-re*a.im)/n);
	}

	public ComplexNumber add(ComplexNumber a) {
		return new ComplexNumber(re+a.re, im+a.im);
	}

	public ComplexNumber subtract(ComplexNumber a) {
		return new ComplexNumber(re-a.re, im-a.im);
	}
	
	public ComplexNumber square() {
		return new ComplexNumber(re*re-im*im, 2.*re*im);
	}
	
	public double abs() {
		return Math.sqrt(re*re+im*im);
	}
	
	public ComplexNumber conjugate() {
		return new ComplexNumber(re, -im);
	}
	
	public double real() {
		return re;
	}
	
	public double imaginary() {
		return im;
	}
	
	public String toString() {
		return "("+re+" + i"+im+")";
	}
}
