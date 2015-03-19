package de.kvrlp.math.la.provider.sparsehash;

import java.util.HashMap;
import java.util.HashSet;

import de.kvrlp.math.la.LAProvider;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class HashMapVector implements Vector {

	private HashMap<Integer, Double> vec = new HashMap<Integer, Double>();
	
	private int n = 0;
	private int smallestIndex = Integer.MAX_VALUE;
	private int largestIndex = 0;
	
	
	@Override
	public void initialize(int n) {
		this.n = n;
	}

	@Override
	public void set(int i, double value) {
		if (value==0.) return; // do not clutter the vector with 0. entries.
		vec.put(i, value);
		if (i<smallestIndex) smallestIndex = i;
		if (i>largestIndex) largestIndex = i;
	}

	@Override
	public int getNumberOfEntries() {
		return n;
	}

	@Override
	public double get(int i) {
		Double d = vec.get(i);
		return d==null ? 0. : d.doubleValue();
	}

	@Override
	public Vector crossProduct(Vector v) {
		return null;
	}

	@Override
	public double scalarProduct(Vector v) {
		double sum = 0.;
		for(Integer key : vec.keySet()) {
			sum += vec.get(key)*v.get(key);
		}
		return sum;
	}

	@Override
	public Matrix dyadicProduct(Vector v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector multiply(double d) {
		HashMapVector v = new HashMapVector();
		v.initialize(n);
		for(Integer key : vec.keySet()) {
			v.set(key, d*vec.get(key));
		}
		return v;
	}

	@Override
	public Vector add(Vector v) {
		HashMapVector res = new HashMapVector();
		res.initialize(n);
		HashSet<Integer> keys = new HashSet<Integer>();
		keys.addAll(vec.keySet());
		keys.addAll(((HashMapVector)v).vec.keySet());
		for(Integer key : keys) {
			res.set(key, this.get(key)+v.get(key));
		}
		return res;
	}

	@Override
	public Vector subtract(Vector v) {
		HashMapVector res = new HashMapVector();
		res.initialize(n);
		HashSet<Integer> keys = new HashSet<Integer>();
		keys.addAll(vec.keySet());
		keys.addAll(((HashMapVector)v).vec.keySet());
		for(Integer key : keys) {
			res.set(key, this.get(key)-v.get(key));
		}
		return res;
	}

	@Override
	public double normMaximum() {
		double max = 0.;
		for(Double d : vec.values()) {
			double dd = Math.abs(d);
			if (dd>max) max = dd;
		}
		return max;
	}

	@Override
	public double norm(double p) {
		double sum = 0.;
		for(Double d : vec.values()) {
			double dd = Math.abs(d);
			sum += Math.pow(dd, p);
		}
		return Math.pow(sum, 1./p);
	}

	@Override
	public double normp(double p) {
		double sum = 0.;
		for(Double d : vec.values()) {
			double dd = Math.abs(d);
			sum += Math.pow(dd, p);
		}
		return sum;
	}

	@Override
	public LAProvider getProvider() {
		return LAProvider.SPARSE;
	}

	@Override
	public Vector duplicate() {
		HashMapVector clone = new HashMapVector();
		clone.initialize(n);
		clone.vec.putAll(vec);
		return clone;
	}

}
