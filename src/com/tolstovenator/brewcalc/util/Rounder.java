package com.tolstovenator.brewcalc.util;

public class Rounder {
	
	private final static int powers[] = {
		1,
		10,
		100,
		1000,
		10000,
		100000,
		1000000,
		10000000,
		100000000,
		1000000000
	};
	
	public static double round(double v, int p) {
		if (p < 0 || p >= powers.length) throw new IllegalArgumentException("incorrect p value: " + p);
		return (double)Math.round(v * powers[p]) / powers[p];
	}
}
