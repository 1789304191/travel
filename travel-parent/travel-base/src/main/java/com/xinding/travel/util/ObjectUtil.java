package com.xinding.travel.util;

public class ObjectUtil {

	public static String stringFormat(Object arg) {
		if (arg == null || "".equals(arg)) {
			return null;
		}
		return String.valueOf(arg);

	}

	public static Long longFormat(Object arg) {
		if (arg == null || "".equals(arg)) {
			return null;
		}
		return Long.valueOf(String.valueOf(arg));

	}

	public static Integer integerFormat(Object arg) {
		if (arg == null || "".equals(arg)) {
			return null;
		}
		return Integer.valueOf(String.valueOf(arg));

	}

	public static Double doubleFormat(Object arg) {
		if (arg == null || "".equals(arg)) {
			return null;
		}
		return Double.valueOf(String.valueOf(arg));

	}

}
