package com.tolstovenator.brewcalc.calc;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class BeanUtils {
	public static String getProperty(Object o, String property) {
		String getter = "get" + property.substring(0, 1).toUpperCase() + property.substring(1, property.length() - 4);
		try {
			Class clazz = o.getClass();
			Method method = clazz.getMethod(getter);
			Object result = method.invoke(o);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print("Cannot find method " + getter + " for " + o.getClass().toString());
			return null;
		}
	}
	
	public static void setProperty(Object o, String property, Double value) {
		String setter = "set" + property.substring(0, 1).toUpperCase() + property.substring(1, property.length() - 4);
		try {
			Class clazz = o.getClass();
			Method method = clazz.getMethod(setter, double.class);
			Object result = method.invoke(o, value);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print("Cannot find method " + setter + " for " + o.getClass().toString());
		}
	}
}
