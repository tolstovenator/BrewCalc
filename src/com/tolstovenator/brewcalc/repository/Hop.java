package com.tolstovenator.brewcalc.repository;

public class Hop {

	private String name;
	
	private double alpha;
	private double beta;
	private HopForm hopForm;
	private HopUsage hopUsage;
	private int storageFactor;
	private String origin;
	private double humulene;
	private double cohumulone;
	private double caryophyllene;
	private double myrcene;
	
	public enum HopUsage {
		BITTER,
		AROMA,
		DUAL
	}
	
	public enum HopForm {
		WHOLE,
		PLUG,
		PELLET
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	

	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public HopForm getHopForm() {
		return hopForm;
	}

	public void setHopForm(HopForm hopForm) {
		this.hopForm = hopForm;
	}

	public HopUsage getHopUsage() {
		return hopUsage;
	}

	public void setHopUsage(HopUsage hopUsage) {
		this.hopUsage = hopUsage;
	}

	public int getStorageFactor() {
		return storageFactor;
	}

	public void setStorageFactor(int storageFactor) {
		this.storageFactor = storageFactor;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public double getHumulene() {
		return humulene;
	}

	public void setHumulene(double humulene) {
		this.humulene = humulene;
	}

	public double getCohumulone() {
		return cohumulone;
	}

	public void setCohumulone(double cohumulone) {
		this.cohumulone = cohumulone;
	}

	public double getCaryophyllene() {
		return caryophyllene;
	}

	public void setCaryophyllene(double caryophyllene) {
		this.caryophyllene = caryophyllene;
	}

	public double getMyrcene() {
		return myrcene;
	}

	public void setMyrcene(double myrcene) {
		this.myrcene = myrcene;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
