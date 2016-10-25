package com.xinding.travel.util;

import java.text.DecimalFormat;

public class NumberFormat {

	public static Double numberFormat(String str) {
		Double a = Double.valueOf(str) / 100.00;
		// 这样为保持2位
		DecimalFormat df = new DecimalFormat("##0.00");
		String s = df.format(a);
		return Double.valueOf(s);
	}

}
