package de.kvrlp.math.la.provider.sparseset;

public class SparseItem implements Comparable<SparseItem> {

	private int i;
	private int j;

	@Override
	public int compareTo(SparseItem o) {
		int ii = i-o.i;
		if (ii==0) {
			return j-o.j;
		}
		return ii;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
}
