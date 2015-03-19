package de.kvrlp.math.la.provider.array;

import de.kvrlp.math.la.LAProvider;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class ArrayVector implements Vector {

	private double[] v = null;
	
	@Override
	public void initialize(int n) {
		v = new double[n];
	}

	@Override
	public void set(int i, double value) {
		v[i] = value;
	}

	@Override
	public int getNumberOfEntries() {
		return v.length;
	}

	@Override
	public double get(int i) {
		return v[i];
	}

	@Override
	public Vector crossProduct(Vector v) {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public double scalarProduct(Vector ve) {
		if (ve.getNumberOfEntries()!=v.length) {
			throw new IllegalArgumentException("Vectors do not match in size!");
		}
		double sum = 0.;
		for(int i=0;i<v.length;++i) {
			sum += v[i]*ve.get(i);
		}
		return sum;
	}

	@Override
	public Matrix dyadicProduct(Vector v) {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public Vector multiply(double d) {
		ArrayVector av = new ArrayVector();
		av.initialize(v.length);
		for(int i=0;i<v.length;++i) {
			av.v[i] = v[i]*d;
		}
		return av;
	}

	@Override
	public Vector add(Vector ve) {
		ArrayVector av = new ArrayVector();
		av.initialize(v.length);
		for(int i=0;i<v.length;++i) {
			av.v[i] = v[i]+ve.get(i);
		}
		return av;
	}

	@Override
	public Vector subtract(Vector ve) {
		ArrayVector av = new ArrayVector();
		av.initialize(v.length);
		for(int i=0;i<v.length;++i) {
			av.v[i] = v[i]-ve.get(i);
		}
		return av;
	}

	@Override
	public double normMaximum() {
		double max = 0.;
		for(int i=0;i<v.length;++i) {
			double d = v[i]<0. ? -v[i] : v[i];
			if (d>max) max = d;
		}
		return max;
	}

	@Override
	public double norm(double p) {
		double sum = 0.;
		for(int i=0;i<v.length;++i) {
			double d = Math.abs(v[i]);
			sum += Math.pow(d, p);
		}
		return Math.pow(sum, 1./p);
	}

	@Override
	public double normp(double p) {
		double sum = 0.;
		for(int i=0;i<v.length;++i) {
			double d = Math.abs(v[i]);
			sum += Math.pow(d, p);
		}
		return sum;
	}

	@Override
	public LAProvider getProvider() {
		return LAProvider.ARRAY;
	}

	@Override
	public Vector duplicate() {
		ArrayVector clone = new ArrayVector();
		clone.initialize(v.length);
		System.arraycopy(v, 0, clone.v, 0, v.length);
		return clone;
	}

}
