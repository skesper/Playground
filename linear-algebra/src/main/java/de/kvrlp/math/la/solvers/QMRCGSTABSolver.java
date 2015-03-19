package de.kvrlp.math.la.solvers;

import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Solver;
import de.kvrlp.math.la.Vector;

public class QMRCGSTABSolver implements Solver {

	@Override
	public Vector solve(Matrix A, Vector b) {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public Matrix solve(Matrix A, Matrix b) {
		throw new UnsupportedOperationException("Iterative solvers do not support matrix values as right hand sides.");
	}

}
