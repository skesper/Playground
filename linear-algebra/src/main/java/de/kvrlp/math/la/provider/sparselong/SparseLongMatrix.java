package de.kvrlp.math.la.provider.sparselong;

import java.util.HashMap;

import de.kvrlp.math.la.LAProvider;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class SparseLongMatrix implements Matrix {

	private HashMap<Long, Double> vals = new HashMap<Long, Double>();
	
	@Override
	public void initialize(int n, int m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(int i, int j, double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double get(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector multiply(Vector x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix multiply(Matrix m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix transpose() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfColumns() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double det() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double normMaximum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double norm(double p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LAProvider getProvider() {
		// TODO Auto-generated method stub
		return null;
	}

}
