package de.kvrlp.math.la.iteration;

public class IterationResult<T> {

	private T result;
	private double error;
	private int iterationsNeeded;
	private Type resultType;
	
	public enum Type {
		CONVERGENCE,
		NO_CONVERGENCE,
		ERROR
	}

	
	public IterationResult(T result, double error, int iterationsNeeded, Type type) {
		this.result = result;
		this.error = error;
		this.iterationsNeeded = iterationsNeeded;
		this.resultType = type;
	}

	public T getResult() {
		return result;
	}


	public void setResult(T result) {
		this.result = result;
	}


	public double getError() {
		return error;
	}


	public void setError(double error) {
		this.error = error;
	}


	public int getIterationsNeeded() {
		return iterationsNeeded;
	}


	public void setIterationsNeeded(int iterationsNeeded) {
		this.iterationsNeeded = iterationsNeeded;
	}


	public Type getResultType() {
		return resultType;
	}


	public void setResultType(Type resultType) {
		this.resultType = resultType;
	}
	
}
