package de.kvrlp.math.la.solvers.gen;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import de.kvrlp.math.la.LAFactory;
import de.kvrlp.math.la.LAProvider;
import de.kvrlp.math.la.Matrix;
import de.kvrlp.math.la.Vector;

public class GeneticSolver {

	private SecureRandom rand = new SecureRandom();
	
	public Vector solve1(Matrix A, Vector b) {
		int numProc = Runtime.getRuntime().availableProcessors();
		int n = 4;

		ArrayList<ParallelMutator> mutators = new ArrayList<GeneticSolver.ParallelMutator>();
		for(int i=0;i<numProc;++i) {
			ParallelMutator pm = new ParallelMutator(A, b, n);
			mutators.add(pm);
		}
		
		Vector x = b.multiply(1.);
		double eps = residuum(A, b, x);
		int cnt = 0;
		while(true) {
			if (eps<0.01) break;
			cnt++;
			// single thread
			Vector candidate = mutate(x, n, eps);
			double epsCand = residuum(A, b, candidate);
			if (epsCand<eps) {
//				System.out.println(cnt+". Mutation fitter: "+epsCand);
				eps = epsCand;
				x = candidate;
			}
			
			// parallel
			
//			readyCounter = 0;
//			for(ParallelMutator pm : mutators) {
//				pm.update(x, eps);
//			}
//			
//			while(readyCounter<numProc) {
//				try {
//					Thread.sleep(1);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					break;
//				}
//			}
//			
//			Vector candidate = null;
//			
//			double epsCand = Double.MAX_VALUE;
//			for(ParallelMutator pm : mutators) {
//				if (pm.getEps()<epsCand) {
//					candidate = pm.getCandidate();
//					epsCand = pm.getEps();
//				}
//			}
//			if (epsCand<eps) {
//				System.out.println("Mutation fitter: "+epsCand);
//				x = candidate;
//				eps = epsCand;
//			}
		}
		System.out.println(cnt+" generations later.");
		return x;
	}
	
	public static Integer readyCounter = 0;

	public class ParallelMutator implements Runnable, Comparable<ParallelMutator> {
		
		private Matrix A;
		private Vector b;
		private Vector x;
		private Vector candidate;
		private double eps;
		private int n;
		private double div;
		private Thread t;
		
		public ParallelMutator(Matrix A, Vector b, int n) {
			this.A = A;
			this.b = b;
			this.n = n;
			t = new Thread(this);
		}
		
		public void update(Vector x, double div) {
			this.x = x;
			this.div = div;
			this.x = x;
			candidate = null;
			eps = -1.;
			t.start();
		}
		
		@Override
		public void run() {
			candidate = mutate(x, n, div);
			eps = residuum(A, b, candidate);
			synchronized(readyCounter) {
				readyCounter++;
				System.out.println("update ready counter: "+Thread.currentThread().getName()+"  ->  "+readyCounter);
			}
		}

		public Vector getCandidate() {
			return candidate;
		}

		public double getEps() {
			return eps;
		}

		@Override
		public int compareTo(ParallelMutator o) {
			if (eps==o.eps) return 0;
			return eps<o.eps ? 1 : -1;
		}
		
	}

	public double residuum(Matrix A, Vector b, Vector x) {
		return b.subtract(A.multiply(x)).norm(2.);
	}

	public Vector solve2(Matrix A, Vector b) {
		
		Vector x = b.multiply(1.);
		double eps = residuum(A, b, x);
		int n = 50;
		int nChildren = 20;
		double maxEps = 1.e-2;
		
		long start = System.currentTimeMillis();
		int cnt = 0;
		while(true) {
			if (eps<maxEps) {
				break;
			}
			cnt++;
			List<Vector> generation = createGeneration(x, nChildren, n, eps);
			List<Vector> mates = mate(generation);
			VectorResiduumValue candidate = fittest(mates, A, b);
			double epsCand = candidate.eps;
			if (epsCand<eps) {
				System.out.println(cnt+". Mutation fitter: "+epsCand);
				eps = epsCand;
				x = candidate.v;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(cnt+" generations and "+(end-start)+"ms later.");
		
		return x;
	}
	
	public class VectorResiduumValue {
		Vector v;
		double eps;
	}
	
	private VectorResiduumValue fittest(List<Vector> mates, Matrix A, Vector b) {
		Vector candidate = mates.get(0);
		double eps = residuum(A, b, candidate);
		
		for(int i=1;i<mates.size();++i) {
			double mateEps = residuum(A, b, mates.get(i));
			if (mateEps<eps) {
				eps = mateEps;
				candidate = mates.get(i);
			}
		}
		VectorResiduumValue vrv = new VectorResiduumValue();
		vrv.eps = eps;
		vrv.v = candidate;
		return vrv;
	}

	private List<Vector> mate(List<Vector> generation) {
		int len = generation.size();
		ArrayList<Vector> children = new ArrayList<Vector>();
		for(int i=0;i<len;++i) {
			Vector a = generation.get(i);
			for(int j=1;j<len;++j) {
				Vector b = generation.get(j);
				Vector child = mate(a,b);
				children.add(child);
			}
		}
		return children;
	}

	private Vector mate(Vector a, Vector b) {
		Vector child = LAFactory.newInstance().newVector(a.getNumberOfEntries(), a.getProvider());
		int idx = rand.nextInt(a.getNumberOfEntries());
		for(int i=0;i<idx;++i) {
			child.set(i, a.get(i));
		}
		for(int i=idx;i<a.getNumberOfEntries();++i) {
			child.set(i, b.get(i));
		}
		return child;
	}

	private List<Vector> createGeneration(Vector x, int nChildren, int n, double div) {
		ArrayList<Vector> result = new ArrayList<Vector>();
		for(int i=0;i<nChildren;++i) {
			result.add(mutate(x, n, div));
		}
		return result;
	}

	public Vector mutate(Vector x, int num, double div) {
		Vector v = x.duplicate();
		int len = v.getNumberOfEntries();
		for(int i=0;i<num;++i) {
			int idx = rand.nextInt(len);
			v.set(idx, mutate(v.get(idx), div));
		}
		return v;
	}
	
	public double mutate(double val, double div) {
		double r = (rand.nextDouble()-.5)*div;
		return val+r;
	}
	
	public static void main(String[] args) {
		int size = 100;
		LAProvider provider = LAProvider.ARRAY;
		Matrix m = LAFactory.newInstance().newMatrix(size, size, provider);
		Vector b = LAFactory.newInstance().newVector(size, provider);
		for(int i=0;i<size;++i) {
			b.set(i, 1./((double)(1.+i)));
			m.set(i,i, 10./(i+1.));
			for(int j=0;j<size;++j) {
				if (i!=j) {
					m.set(i,j, 1./((double)((1.+i)*(1.+j))));
				}
			}
		}

		GeneticSolver gs = new GeneticSolver();
		
		Vector x = gs.solve2(m, b);
		
		Vector y = m.multiply(x);
		
		for(int i=0;i<10;++i) {
			System.out.println(y.get(i)+"  =!=  "+b.get(i));
		}
	}
}
