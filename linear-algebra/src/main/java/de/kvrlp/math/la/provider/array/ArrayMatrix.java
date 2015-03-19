package de.kvrlp.math.la.provider.array;

import de.kvrlp.math.la.LAFactory;
import de.kvrlp.math.la.LAProvider;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class ArrayMatrix implements Matrix {

	private double[] v;
	private int n,m;
	
	@Override
	public void initialize(int n, int m) {
		v = new double[n*m];
		this.n = n;
		this.m = m;
	}

	@Override
	public void set(int i, int j, double value) {
		v[i*m+j] = value;
	}

	@Override
	public double get(int i, int j) {
		return v[i*m+j];
	}

	@Override
	public Vector multiply(Vector x) {
		if (x.getNumberOfEntries()!=m) {
			throw new IllegalArgumentException("Number of Entries in Vector must match number of columns in matrix!");
		}
		ArrayVector av = new ArrayVector();
		av.initialize(n);
		for(int i=0;i<n;++i) {
			int I = i*m;
			double sum = 0.;
			for(int j=0;j<m;++j) {
				sum += v[I+j]*x.get(j);
			}
			av.set(i, sum);
		}
		return av;
	}

	@Override
	public int getNumberOfRows() {
		return n;
	}

	@Override
	public int getNumberOfColumns() {
		return m;
	}

	@Override
	public double det() {
		throw new RuntimeException("Not implemented yet!");
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
	public LAProvider getProvider() {
		return LAProvider.ARRAY;
	}

	@Override
	public Matrix multiply(Matrix M) {
		Matrix a = LAFactory.newInstance().newMatrix(n, M.getNumberOfColumns(), M.getProvider());
		
		for(int i=0;i<n;++i) {
			for(int j=0;j<n;++j) {
				double s = 0.;
				for(int k=0;k<n;++k) {
					s += this.get(i,k)*M.get(k,j);
				}
				a.set(i, j, s);
			}
		}
		
		return a;
	}

	@Override
	public Matrix transpose() {
		ArrayMatrix a = new ArrayMatrix();
		a.initialize(n, m);
		
		for(int i=0;i<n;++i) {
			for(int j=0;j<m;++j) {
				a.set(j, i, this.get(i,j));
			}
		}
		
		return a;
	}

}
