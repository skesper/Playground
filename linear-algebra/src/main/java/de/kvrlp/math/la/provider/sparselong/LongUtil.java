package de.kvrlp.math.la.provider.sparselong;

public class LongUtil {

	public static final long lmask = 0x00000000FFFFFFFFL;
	public static final long hmask = 0xFFFFFFFF00000000L;
	
	public static int getHigh(Long L) {
		long l = L.longValue();
		return (int)((hmask&l) >> 32);
	}
	
	public static int getLow(Long l) {
		return (int)(l & lmask);
	}
	
	public static Long compose(int high, int low) {
		return new Long((hmask&((long)high)<<32) | (((long)low)&lmask));
	}
}
