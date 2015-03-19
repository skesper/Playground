package de.kvrlp.math.la.number;

public interface Operatable<T> {

	T add(T other);
	
	T subtract(T other);
	
	T multiply(T other);
	
	T divide(T other);
}
