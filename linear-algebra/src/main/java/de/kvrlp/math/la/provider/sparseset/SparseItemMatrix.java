package de.kvrlp.math.la.provider.sparseset;

import java.util.TreeMap;

import de.kvrlp.math.la.LAProvider;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class SparseItemMatrix implements Matrix {

	private TreeMap<SparseItem, Double> val = new TreeMap<SparseItem, Double>();
	private int n,m;
	
	@Override
	public void initialize(int n, int m) {
		this.n = n;
		this.m = m;
	}

	@Override
	public void set(int i, int j, double value) {
		SparseItem si = new SparseItem();
		si.setI(i);
		si.setJ(j);
		val.put(si, value);
	}

	@Override
	public double get(int i, int j) {
		SparseItem si = new SparseItem();
		si.setI(i);
		si.setJ(j);
		Double d = val.get(si);
		return d==null ? 0. : d.doubleValue();
	}

	@Override
	public Vector multiply(Vector x) {
		SparseItemVector siv = new SparseItemVector();
		siv.initialize(n);
		for(SparseItem si : val.keySet()) {
			double s = siv.get(si.getI());
			siv.set(si.getI(), s+x.get(si.getJ())*val.get(si));
		}
		return siv;
	}

	@Override
	public Matrix multiply(Matrix m) {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public Matrix transpose() {
		throw new RuntimeException("Not implemented yet!");
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
		for(SparseItem si : val.keySet()) {
			double d = Math.abs(val.get(si));
			if (max<d) max = d;
		}
		return max;
	}

	@Override
	public double norm(double p) {
		double sum = 0.;
		for(SparseItem si : val.keySet()) {
			double d = Math.abs(Math.pow(val.get(si),p));
			sum += d;
		}
		return Math.pow(sum, 1./p);
	}

	@Override
	public LAProvider getProvider() {
		return LAProvider.SPARSE_ITEM;
	}

	public static void main(String[] args) {
		SparseItemMatrix sim = new SparseItemMatrix();
		
		sim.set(0,0, 1.);
		System.out.println("0,0 : "+sim.get(0,0));
				
		sim.set(1,0, 1.);
		System.out.println("1,0 : "+sim.get(1,0));
				
		TreeMap<SparseItem, Double> val = new TreeMap<SparseItem, Double>();
		SparseItem key = new SparseItem();
		key.setI(1);
		key.setJ(0);
		
		val.put(key, 1.);
		
		System.out.println("1,0 : "+val.get(key));
		key = new SparseItem();
		key.setI(1);
		key.setJ(0);
		System.out.println("1,0 : "+val.get(key));
	}
}
