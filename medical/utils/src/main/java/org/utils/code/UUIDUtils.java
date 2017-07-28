package org.utils.code;

import java.util.UUID;

public class UUIDUtils {
	private static UUIDUtils instance = new UUIDUtils();

	private UUIDUtils() {
	}

	public static UUIDUtils getInstance() {
		if (null == instance) {
			instance = new UUIDUtils();
		}
		return instance;
	}

	private String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Numbers.toString(hi | (val & (hi - 1)), Numbers.MAX_RADIX).substring(1);
	}

	public String getUUID19() {
		UUID uuid = UUID.randomUUID();
		StringBuilder sb = new StringBuilder();
		sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
		sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
		sb.append(digits(uuid.getMostSignificantBits(), 4));
		sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
		sb.append(digits(uuid.getLeastSignificantBits(), 12));
		return sb.toString().toUpperCase();
	}
	public static void main(String[] args) {
		System.out.println(UUIDUtils.getInstance().getUUID19());
	}
}
