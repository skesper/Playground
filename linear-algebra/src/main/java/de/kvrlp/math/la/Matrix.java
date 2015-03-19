package de.kvrlp.math.la;


public interface Matrix {

	void initialize(int n, int m);
	
	void set(int i, int j, double value);
	
	double get(int i, int j);
	
	Vector multiply(Vector x);

	Matrix multiply(Matrix m);
	
	Matrix transpose();

	int getNumberOfRows();
	
	int getNumberOfColumns();
	
	double det();
	
	double normMaximum();
	
	double norm(double p);
	
	LAProvider getProvider();
}
