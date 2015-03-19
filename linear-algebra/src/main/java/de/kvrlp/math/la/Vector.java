package de.kvrlp.math.la;

public interface Vector {

	void initialize(int n);
	
	void set(int i, double value);
	
	int getNumberOfEntries();
	
	double get(int i);
	
	Vector crossProduct(Vector v);
	
	double scalarProduct(Vector v);
	
	Matrix dyadicProduct(Vector v);
	
	Vector multiply(double d);
	
	Vector add(Vector v);
	
	Vector subtract(Vector v);
	
	double normMaximum();
	
	double norm(double p);
	
	/**
	 * = norm(p)^p
	 * @param p
	 * @return
	 */
	double normp(double p);

	LAProvider getProvider();

	Vector duplicate();
}
