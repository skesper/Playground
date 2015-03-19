package de.kvrlp.math.la;

import de.kvrlp.math.la.provider.array.ArrayMatrix;
import de.kvrlp.math.la.provider.array.ArrayVector;
import de.kvrlp.math.la.provider.sparsehash.HashMapVector;
import de.kvrlp.math.la.provider.sparsehash.HashMapVectorMatrix;
import de.kvrlp.math.la.provider.sparseset.SparseItemMatrix;
import de.kvrlp.math.la.provider.sparseset.SparseItemVector;

public class LAFactory {
	private LAFactory() {}
	
	public static LAFactory newInstance() {
		return new LAFactory();
	}
	
	public Matrix newMatrix(int n, int m, LAProvider provider) {
		Matrix matrix = null;
		
		switch(provider) {
		case ARRAY: {
			matrix = new ArrayMatrix();
		} break;
		
		case SPARSE: {
			matrix = new HashMapVectorMatrix();
		} break;
		
		case SPARSE_ITEM: {
			matrix = new SparseItemMatrix();
		} break;
		
		default: {
			throw new RuntimeException("Not yet implemented!"); 
		}
		}
		matrix.initialize(n, m);
		return matrix;
	}
	
	public Vector newVector(int n, LAProvider provider) {
		Vector vector = null;
		switch(provider) {
		
		case ARRAY: {
			vector = new ArrayVector();
		} break;
		
		case SPARSE: {
			vector = new HashMapVector();
		} break;
		
		case SPARSE_ITEM: {
			vector = new SparseItemVector();
		} break;
		
		default: {
			throw new RuntimeException("Not yet implemented!"); 
		}
		}
		vector.initialize(n);
		return vector;
	}
}
