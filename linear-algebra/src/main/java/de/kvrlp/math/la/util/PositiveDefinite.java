package de.kvrlp.math.la.util;

import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.provider.array.ArrayMatrix;
import de.kvrlp.math.la.provider.sparsehash.HashMapVectorMatrix;

public class PositiveDefinite {

	public boolean gershGorinCircleTest(Matrix A) {
		switch(A.getProvider()) {
		case ARRAY: {
			return gershGorinCircleTest((ArrayMatrix)A);
		}
		
		case SPARSE: {
			return gershGorinCircleTest((HashMapVectorMatrix)A);
		} 
		}
		return false;
	}
	
	private boolean gershGorinCircleTest(ArrayMatrix A) {
		
		return false;
	}

	private boolean gershGorinCircleTest(HashMapVectorMatrix A) {
		return false;
	}
}
