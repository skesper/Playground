package de.kvrlp.math.la.provider.sparsehash;

import de.kvrlp.math.la.LAProvider;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class HashMapVectorMatrix implements Matrix {
	
	private HashMapVector[] rows;
	private int m,n;
	
	public HashMapVectorMatrix() {
	}
	
	@Override
	public void initialize(int n, int m) {
		this.n = n;
		this.m = m;
		rows = new HashMapVector[n];
		for(int i=0;i<n;++i) {
			rows[i] = new HashMapVector();
			rows[i].initialize(m);
		}
	}

	@Override
	public void set(int i, int j, double value) {
		rows[i].set(j, value);
	}

	@Override
	public double get(int i, int j) {
		return rows[i].get(j);
	}

	@Override
	public Vector multiply(Vector x) {
		HashMapVector v = new HashMapVector();
		v.initialize(n);
		for(int i=0;i<n;++i) {
			v.set(i, rows[i].scalarProduct(x));
		}
		return v;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double normMaximum() {
		double max = 0.;
		for(int i=0;i<n;++i) {
			double d = rows[i].normMaximum();
			if (d>max) max = d;
		}
		return max;
	}

	@Override
	public double norm(double p) {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public LAProvider getProvider() {
		return LAProvider.SPARSE;
	}

	@Override
	public Matrix multiply(Matrix m) {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public Matrix transpose() {
		throw new RuntimeException("Not implemented yet!");
	}

}
