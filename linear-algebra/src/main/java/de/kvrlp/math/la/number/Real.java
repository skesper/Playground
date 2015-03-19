package de.kvrlp.math.la.number;

public class Real implements Operatable<Double> {

	private final Double val;
	
	public Real(Double val) {
		this.val = val;
	}

	@Override
	public Double add(Double other) {
		return val+other;
	}

	@Override
	public Double subtract(Double other) {
		return val-other;
	}

	@Override
	public Double multiply(Double other) {
		return val*other;
	}

	@Override
	public Double divide(Double other) {
		return val/other;
	}
}
